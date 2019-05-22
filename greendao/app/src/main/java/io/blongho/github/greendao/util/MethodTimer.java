package io.blongho.github.greendao.util;

import android.util.Log;

public class MethodTimer {
  private final String method;
  private long start;
  private long stop;

  public MethodTimer(String method) {
    this.method = method;
    start = stop = 0;
  }

  public final void start() {
    start = System.nanoTime();
  }

  public final void stop() {
    stop = System.nanoTime();
  }

  public final void results() {
    final long results = stop  - start;
    final long milli = results / 1_000_000;
    final long sec = milli / 1_000;
    Log.i(method, "took " + results + " ns (" + milli + " ms, " + sec + " s)");
  }
}
