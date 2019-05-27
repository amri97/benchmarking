package io.blongho.github.greendao.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Method timer.
 */
public class MethodTimer {
  private final Context context;
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
    this(method, null);
  }

  public MethodTimer(final String method, final Context context) {
    this.method = method + "::";
    start = stop = 0;
    resultsMap = new HashMap<>();
    this.context = null;
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
      new Results(key, results, milli, sec);
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

  private class Results {
    private static final String TAG = "Results";
    private final String method;
    private final long nanoTime;
    private final long milliTime;
    private final long secTime;

    Results(String method, long nanoTime, long milliTime, long secTime) {
      this.method = method;
      this.nanoTime = nanoTime;
      this.milliTime = milliTime;
      this.secTime = secTime;
      writeStringAsFile(this);
    }

    @Override
    public String toString() {
      final StringBuffer sb = new StringBuffer("Results{");
      sb.append("method='").append(method).append('\'');
      sb.append(", nanoTime=").append(nanoTime);
      sb.append(", milliTime=").append(milliTime);
      sb.append(", secTime=").append(secTime);
      sb.append('}');
      return sb.toString();
    }

    private synchronized void writeStringAsFile(Object src) {
      final File file = createFile("results.json");
      try (Writer writer = new FileWriter(file, true)) {
        if (file.length() == 0) {
          writer.write("[\n");
        }
        Gson gson = new GsonBuilder().create();
        gson.toJson(src, writer);
        writer.write("\n,");
      } catch (IOException e) {
        e.printStackTrace();
        Log.d(TAG, "writeStringAsFile() called with: fileContents = [" + src.toString() + "]");
      }
    }

    private File createFile(final String filename) {
      File file = new File(Environment.getExternalStorageDirectory() + File.separator + filename);
      try {
        if (!file.exists()) {
          file.createNewFile();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      file.setExecutable(true);
      return file;
    }
  }
}
