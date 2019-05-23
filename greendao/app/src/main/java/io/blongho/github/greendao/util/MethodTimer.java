package io.blongho.github.greendao.util;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Method timer.
 */
public class MethodTimer {
  private String method;
  private long start;
  private long stop;

  private Map<String, Long> resultsMap;

  /**
   * Instantiates a new Method timer.
   *
   * @param method the method
   */
  public MethodTimer(String method) {
    this.method = method + "::";
    start = stop = 0;
    resultsMap = new HashMap<>();
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
      results = resultsMap.get(key);
      milli = results / 1_000_000;
      sec = milli / 1_000;
      Log.i(key, "took " + results + " ns (" + milli + " ms, " + sec + " s)");
    }
    resultsMap = new HashMap<>();
  }

  /**
   * Add tag.
   * <p>Add the tag that specifies the method to measure</p>
   *
   * @param tag the tag
   */
  public final void addTag(String tag) {
    final String current = method.split("::")[0];
    method = tag;
  }
}
