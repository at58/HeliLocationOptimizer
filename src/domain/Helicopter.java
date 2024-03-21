package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

import services.calculations.Scheduler;
import utils.CalculationUtils;

/**
 * Domain class for the helicopter base.
 */
public final class Helicopter implements Item {

	private final UUID uuid;
	private int speed;
	private Coordinate coordinate;
	/*
	 * Mapping of location and the distance between this helicopter object and the
	 * assigned location.
	 */
	private final Map<Location, Double> locationHelicopterMap;

	public Helicopter(int airSpeed) {
		this.uuid = UUID.randomUUID();
		this.speed = airSpeed;
		this.locationHelicopterMap = new HashMap<>();
	}

	@Override
	public Coordinate getCoordinate() {
		return this.coordinate;
	}

	public void setCoordinates(int x, int y) {
		this.coordinate = new Coordinate(x, y);
	}

	@Override
	public UUID getUuid() {
		return this.uuid;
	}

	public void allocateLocation(Location location, double distance) {
		this.locationHelicopterMap.put(location, distance);
	}

	public void removeLocation(Location location) {
		this.locationHelicopterMap.remove(location);
	}

	public Map<Location, Double> getLocationHelicopterMapping() {
		return this.locationHelicopterMap;
	}

	public Set<Location> getAssignedLocations() {
		return this.locationHelicopterMap.keySet();
	}

	public int getSpeed() {
		return this.speed;
	}

	public void setSpeed(int velocity) {
		this.speed = velocity;
	}

	/**
	 * retrieves summarized data for a helicopter at the specified index, data
	 * includes index, x-coordinate, y-coordinate, total accidents, average
	 * accidents per location, and a comma-separated list of location names.
	 * 
	 * @param index index of the helicopter.
	 * @return List containing summarized data for the helicopter.
	 */
	public List<String> getSummarizedData(int index) {
		List<String> data = new ArrayList<>();

		data.add(Integer.toString(index + 1));
		data.add(Integer.toString(coordinate.x()));
		data.add(Integer.toString(coordinate.y()));

		Set<Location> locationSet = locationHelicopterMap.keySet();
		int accidents = CalculationUtils.accumulateTotalOfAccidents(locationSet);
		data.add(Integer.toString(accidents));
		data.add(Integer.toString(accidents / locationHelicopterMap.keySet().size()));

		List<Location> locationList = new ArrayList<>(locationSet);
		String locationStr = String.join(", ",
				locationList.stream().map(Location::getName).collect(Collectors.toList()));

		data.add(locationStr);
		return data;
	}

	/**
	 * retrieves detailed data for a helicopter at the specified index, data
	 * includes index, location name, distance to location, and travel time to
	 * location.
	 * 
	 * @param index index of the helicopter.
	 * @return List containing arrays of detailed data for the helicopter.
	 */
	public List<String[]> getDetailedData(int index) {
		List<String[]> data = new ArrayList<>();

		Set<Location> locationSet = locationHelicopterMap.keySet();
		Collection<Double> distanceCollection = locationHelicopterMap.values();
		List<Double> distanceList = new ArrayList<>(distanceCollection);
		Map<Location, Integer> travelTime = Scheduler.calcFlightTime(this);

		for (Location location : locationSet) {
			String[] entry = new String[4];
			entry[0] = Integer.toString(index + 1);
			entry[1] = location.getName();

			int locationIndex = new ArrayList<>(locationSet).indexOf(location);
			entry[2] = String.valueOf(distanceList.get(locationIndex));
			entry[3] = String.valueOf(travelTime.get(location));

			data.add(entry);
		}

		return data;
	}
}