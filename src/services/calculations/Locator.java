package services.calculations;

import domain.Coordinate;
import domain.Helicopter;
import domain.Location;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import services.mapper.LocationMapper;
import utils.CalculationUtils;
import utils.exceptions.NoLocationDataException;
import utils.log.Logger;

/**
 * This class defines some operations to determine the optimum solution for the
 * positioning of helicopters.
 */
public class Locator {

	/**
	 * This method calculates the optimum positions of the available helicopters by
	 * assigning the locations to those helicopter bases that are closest to the
	 * current location. After that, the pseudo focus is calculated and the
	 * assignment of the closest locations to each helicopter is repeated. The
	 * repetition is performed as long as the calculated pseudo focuses of the
	 * helicopter bases change. Once the pseudo focuses are no longer change, the
	 * real optimum positions for helicopter bases are found.
	 *
	 * @param numberOfHeli the number of helicopters that need to be placed.
	 * @param speed        the speed of the helicopters.
	 * @param data         the parsed accident data.
	 * @return list of helicopter.
	 * @throws NumberFormatException   caused by invalid input for number fields
	 * @throws NoLocationDataException caused by empty data.
	 */
	public static List<Helicopter> findOptimalPositions(String numberOfHeli, String speed, List<String[]> data)
			throws NumberFormatException, NoLocationDataException {

		// -- Exception handling
		if (numberOfHeli.isBlank() || speed.isBlank()) {
			throw new IllegalArgumentException("Missing inputs for number and/or speed of helicopter.");
		}
		if (data.isEmpty()) {
			throw new NoLocationDataException("No location data provided.");
		}
		int numberOfHelicopter;
		int helicopterSpeed;
		try {
			numberOfHelicopter = Integer.parseInt(numberOfHeli);
			helicopterSpeed = Integer.parseInt(speed);
		} catch (NumberFormatException e) {
			Logger.log("Non-numeric values were entered in the text filed for helicopter number or speed.");
			throw e;
		}
		if (numberOfHelicopter <= 0) {
			throw new IllegalArgumentException("Negative value or zero was entered for number of helicopter.");
		}
		if (helicopterSpeed <= 0) {
			throw new IllegalArgumentException("Negative value or zero was entered for the helicopter speed.");
		} // -- End of Exception handling

		Stack<Helicopter> helicopterStack = new Stack<>();
		for (int i = 0; i < numberOfHelicopter; i++) {
			helicopterStack.add(new Helicopter(helicopterSpeed));
		}

		List<Location> locations = LocationMapper.mapToLocationObjects(data);

		// initial positioning of helicopters resp. initial determination of
		// coordinates.
		List<Helicopter> helicopterList = PreDistributor.determinePreDistribution(locations, helicopterStack);
		Map<UUID, Coordinate> currentHelicopterCoordinates = new HashMap<>();
		/*
		 * save the first coordinates of each helicopter to proceed a comparison between
		 * old and new coordinate of pseudo-focuses.
		 */
		memorizeLatestHelicopterCoordinates(helicopterList, currentHelicopterCoordinates);

		int loopCounter = 0;
		while (true) {

			allocateClosestLocations(locations, helicopterList);
			determinePseudoFocus(helicopterList);
			boolean locationChanged = relocationOccurred(helicopterList, currentHelicopterCoordinates);
//      System.out.println("Location changed ? -> " + locationChanged);
			if (!locationChanged) {
				break;
			} else {
				memorizeLatestHelicopterCoordinates(helicopterList, currentHelicopterCoordinates);
				loopCounter++;
			}
			if (loopCounter >= 100) {
				break;
			}
		}
		return helicopterList;
	}

	/**
	 * Checks if the helicopter coordinates changed after calculating the new
	 * pseudo-focuses of the helicopter positions. See
	 * {@link Locator#determinePseudoFocus(List)}
	 *
	 * @param helicopterList     the helicopter list with the current states.
	 * @param currentCoordinates the latest stored coordinate values of each
	 *                           helicopter.
	 * @return true, if the coordinates changed, otherwise false.
	 */
	public static boolean relocationOccurred(List<Helicopter> helicopterList,
			Map<UUID, Coordinate> currentCoordinates) {

		AtomicBoolean result = new AtomicBoolean(false);
		helicopterList.forEach(helicopter -> {
			Coordinate current = helicopter.getCoordinate();
			Coordinate latest = currentCoordinates.get(helicopter.getUuid());
			if (current.x() != latest.x() || current.y() != latest.y()) {
				result.set(true);
			}
		});
		return result.get();
	}

	/**
	 * Saves the current coordinates in the provided Map.
	 *
	 * @param helicopterList     the list of helicopters.
	 * @param currentCoordinates The map that stores the UUID of each helicopter and
	 *                           the corresponding coordinates.
	 */
	private static void memorizeLatestHelicopterCoordinates(List<Helicopter> helicopterList,
			Map<UUID, Coordinate> currentCoordinates) {
		helicopterList.forEach(helicopter -> {
			UUID uuid = helicopter.getUuid();
			Coordinate coordinate = helicopter.getCoordinate();
			currentCoordinates.put(uuid, coordinate);
		});
	}

	/**
	 * Calculates the pseudo focus respectively the final focuses once the
	 * coordinates of helicopter bases no longer change. The focus is the minimum of
	 * the gradient field which originates from the cloud of coordinate points.
	 *
	 * @param helicopterList the list of helicopters
	 */
	public static void determinePseudoFocus(List<Helicopter> helicopterList) {

		double[] weightedPointSum = new double[] { 0, 0 }; // {x,y} -coordinates

		for (Helicopter helicopter : helicopterList) {
			Set<Location> assignedLocations = helicopter.getAssignedLocations();
			int totalAccidents = CalculationUtils.accumulateTotalOfAccidents(assignedLocations);
			for (Location location : assignedLocations) {
				int thisAccidents = location.getAccidents();
				double weight = getWeight(totalAccidents, thisAccidents);
				Coordinate locationCoordinate = location.getCoordinate();
				double weighted_X = locationCoordinate.x() * weight;
				double weighted_Y = locationCoordinate.y() * weight;
				weightedPointSum[0] = weightedPointSum[0] + weighted_X;
				weightedPointSum[1] = weightedPointSum[1] + weighted_Y;
			}
			helicopter.setCoordinates((int) weightedPointSum[0], (int) weightedPointSum[1]);
			// System.out.println("weighted sum: " + weightSum);
			weightedPointSum[0] = 0;
			weightedPointSum[1] = 0;
		}
	}

	/**
	 * This method assigns each helicopter the locations that are closest to the
	 * coordinates of the respective helicopter.
	 *
	 * @param locations   list of all locations
	 * @param helicopters list of all helicopters that needs to be placed.
	 */
	public static void allocateClosestLocations(List<Location> locations, List<Helicopter> helicopters) {

		/*
		 * first of all, clear all allocated locations to a helicopter to beginn a
		 * re-allocation of location objects.
		 */
		clearAssignedLocations(helicopters);

		for (Location location : locations) {
			Coordinate locationCoordinate = location.getCoordinate();
			Helicopter closestSpot = null;
			double minimalDistance = Double.MAX_VALUE;
			for (Helicopter helicopter : helicopters) {
				Coordinate helicopterCoordinate = helicopter.getCoordinate();
				double distance = Euclid.calculateDistance(locationCoordinate, helicopterCoordinate);
				if (distance < minimalDistance) {
					minimalDistance = distance;
					closestSpot = helicopter;
				}
			}
			if (Objects.nonNull(closestSpot)) {
				closestSpot.allocateLocation(location, minimalDistance);
			}
		}
	}

	/**
	 * Deletes all elements of the map that contains assigned locations to a current
	 * helicopter.
	 *
	 * @param helicopterList the list of helicopters that needs to be placed.
	 */
	private static void clearAssignedLocations(List<Helicopter> helicopterList) {

		helicopterList.forEach(helicopter -> helicopter.getLocationHelicopterMapping().clear());
	}

	/**
	 * Calculates the weighting according to the relative share of accidents at a
	 * location in relation to the total number of accidents.
	 *
	 * @param totalAccidents total number of accidents.
	 * @param accidents      current accidents in one location.
	 * @return the weighting.
	 */
	public static double getWeight(int totalAccidents, int accidents) {

		return (double) accidents / totalAccidents;
	}
}