package io.blongho.github.room.util;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Method timer.
 */
final public class MethodTimer {
  public static String FILE_NAME = "test.json";
  private String method;
  private long start;
  private long stop;
  private Map<String, Long> resultsMap;
  private ResultsFileWriter fileWriter;

  /**
   * Instantiates a new Method timer.
   *
   * @param method the method
   */
  public MethodTimer(final String method) {
    this.method = method;
    start = stop = 0;
    resultsMap = new HashMap<>();
    fileWriter = new ResultsFileWriter(FILE_NAME);
  }

  /**
   * Start.
   * <p>Start the timing</p>
   */
  public final synchronized void start() {
    start = System.nanoTime();
  }

  /**
   * Stop.
   * <p>Stop the timing</p>
   */
  public final synchronized void stop() {
    stop = System.nanoTime();
    final long results = stop - start;
    resultsMap.put(method, results);
  }

  /**
   * Show results.
   * <p>Displays the results in the logcat in nanoseconds, milliseconds and seconds</p>
   */
  public final synchronized void showResults() {
    long results;
    long milli;
    long sec;
    for (String key : resultsMap.keySet()) {
      {
        results = resultsMap.get(key);
        milli = results / 1_000_000;
        sec = milli / 1_000;
        Log.i(key, "" + results + " ns (" + milli + " ms, " + sec + " s)");
      }
      fileWriter.writeToFile(new ResultsFileWriter(key, results, milli, sec));
    }
    resultsMap = new HashMap<>();
  }

  /**
   * Add tag.
   * <p>Add the tag that specifies the method to measure</p>
   *
   * @param tag the tag
   */
  public final synchronized void setTag(String tag) {
    method = tag;
  }
}
