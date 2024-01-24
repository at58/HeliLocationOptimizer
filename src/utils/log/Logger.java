package utils.log;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public final class Logger {

  private static final List<String> logMessages = new ArrayList<>();
  private static Writer writer;

  public static void log(String message) {
    logMessages.add("- " + message);
    exportToLogFile();
  }

  public static String[] getLog() {
    String[] logArray = new String[logMessages.size()];
    for (int i = 0; i < logMessages.size(); i++) {
      logArray[i] = logMessages.get(i);
    }
    return logArray;
  }

  private static void exportToLogFile() {
    try {
      writer = new FileWriter("C:/Users/toy/IdeaProjects/HeliLocationOptimizer/src/utils/log/log.log");
      for (String message : logMessages) {
        writer.write(message + System.lineSeparator());
      }
      writer.close();
    } catch (IOException ignored) {}
  }
}