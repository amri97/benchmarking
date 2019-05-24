package io.blongho.github.room.util;

import android.content.Context;

import java.util.concurrent.Callable;

import androidx.annotation.RawRes;

public class ReadFromFile implements Callable<String> {
  private final Context context;
  @RawRes
  private final int filename;

  public ReadFromFile(Context context, int filename) {
    this.context = context;
    this.filename = filename;
  }

  /**
   * Computes a result, or throws an exception if unable to do so.
   *
   * @return computed result
   * @throws Exception if unable to compute a result
   */
  @Override
  public String call() throws Exception {
    return AssetsReader.readFromAssets(context, filename);
  }
}
