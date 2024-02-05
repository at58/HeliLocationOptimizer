package utils;

public class MathUtils {

  public static double round(double value, int decimals) {

    String valueString = String.valueOf(value);
    int indexOfDecimal = valueString.indexOf(".") + 1;
    String rounded = valueString.substring(0, (indexOfDecimal + decimals));
    return Double.parseDouble(rounded);
  }
}