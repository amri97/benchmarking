package io.blongho.github.room.util;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Method timer.
 */
public class MethodTimer {
  public static String FILE_NAME = "test.json";
  private final Context context;
  private String method;
  private long start;
  private long stop;
  private Map<String, Long> resultsMap;
  private Results fileWriter;

  /**
   * Instantiates a new Method timer.
   *
   * @param method the method
   */
  public MethodTimer(String method) {
    this(method, null);
  }

  public MethodTimer(final String method, final Context context) {
    this.method = method;
    start = stop = 0;
    resultsMap = new HashMap<>();
    this.context = null;
    fileWriter = new Results(FILE_NAME);
  }

  /**
   * Start.
   * <p>Start the timing</p>
   */
  public final void start() {
    start = System.nanoTime();
  }

  /**
   * Stop.
   * <p>Stop the timing</p>
   */
  public final void stop() {
    stop = System.nanoTime();
    final long results = stop - start;
    resultsMap.put(method, results);
  }

  /**
   * Show results.
   * <p>Displays the results in the logcat in nanoseconds, milliseconds and seconds</p>
   */
  public final void showResults() {
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
      fileWriter.writeStringAsFile(new Results(key, results, milli, sec));
    }
    resultsMap = new HashMap<>();
  }

  /**
   * Add tag.
   * <p>Add the tag that specifies the method to measure</p>
   *
   * @param tag the tag
   */
  public final void setTag(String tag) {
    method = tag;
  }
}
