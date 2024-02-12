package utils;

import domain.Helicopter;
import domain.Location;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TestData {

  public static List<Location> getLocations_24() {

    return new ArrayList<>(List.of(
        new Location("A", 5,10,20), // min
        new Location("B", 15,10,20),
        new Location("C", 56,10,20),
        new Location("D", 60,10,20),
        new Location("F", 55,10,20),
        new Location("G", 150,10,20),
        new Location("I", 155,10,20),
        new Location("J", 152,10,20),
        new Location("K", 290,10,20),
        new Location("L", 10,10,20),
        new Location("M", 355,10,20),
        new Location("N", 20,10,20),
        new Location("O", 356,10,20), // max
        new Location("P", 299,10,20),
        new Location("Q", 145,10,20),
        new Location("R", 144,10,20),
        new Location("S", 57,10,20),
        new Location("T", 58,10,20),
        new Location("U", 349,10,20),
        new Location("V", 300,10,20),
        new Location("W", 9,10,20),
        new Location("X", 139,10,20),
        new Location("Y", 144,10,20),
        new Location("Z", 71,10,20)));
  }

  public static Stack<Helicopter> getHelicopterStack(int number) {

    Stack<Helicopter> helicopterList = new Stack<>();
    for (int i = 0; i < number; i++) {
      helicopterList.add(new Helicopter(1));
    }
    return helicopterList;
  }
}
