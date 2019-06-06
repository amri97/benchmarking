/*
 *
 *  * MIT License
 *  *
 *  * Copyright (c) 2019 Bernard Che Longho
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in all
 *  * copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  * SOFTWARE.
 *
 */

package io.blongho.github.greendao.util;

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
      fileWriter.writeStringAsFile(new ResultsFileWriter(key, results, milli, sec));
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
