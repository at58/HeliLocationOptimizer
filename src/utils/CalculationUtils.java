package utils;

import domain.Location;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.poi.xwpf.usermodel.LineSpacingRule;

public class CalculationUtils {

	public static List<Location> getRandomLocations(List<Location> locations, int amount) {

		Random random;
		List<Location> result;
		int locationSize = locations.size();
		if (amount >= locationSize) {
			return locations;
		} else {
			List<Integer> alreadyAssignedLocations = new ArrayList<>();
			result = new ArrayList<>();
			random = new Random();
			while (result.size() < amount) {
				int nextIndex = random.nextInt(locationSize);
				if (!alreadyAssignedLocations.contains(nextIndex)) {
					result.add(locations.get(nextIndex));
					alreadyAssignedLocations.add(nextIndex);
				}
			}
		}
		return result;
	}

	public static int accumulateTotalOfAccidents(Set<Location> locationSet) {
		return accumulateTotalOfAccidents(new ArrayList<Location>(locationSet));
	}

	public static int accumulateTotalOfAccidents(List<Location> locations) {

		int sum = 0;
		for (Location location : locations) {
			int accidents = location.getAccidents();
			sum += accidents;
		}
		return sum;
	}
	
	public static int getAccidentsMax(List<Location> locations) {
		
		int max = 0;	
		int accidents = 0;
		for (Location location : locations) {
			accidents = location.getAccidents();
			if (accidents > max)
				max = accidents;
		}

		return max;		
	}
}
