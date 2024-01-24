package utils.log;

import java.util.ArrayList;
import java.util.List;

public final class Logger {

  private static final List<String> logMessages = new ArrayList<>();

  public static void log(String message) {
    logMessages.add(message);
  }

  public static String[] getLog() {
    String[] logArray = new String[logMessages.size()];
    for (int i = 0; i < logMessages.size(); i++) {
      logArray[i] = logMessages.get(i);
    }
    return logArray;
  }

  public static void exportToLogFile() {
    // TODO (Ahmet): implement
  }
}