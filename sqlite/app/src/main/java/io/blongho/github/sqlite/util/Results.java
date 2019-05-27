package io.blongho.github.sqlite.util;

import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

class Results {
  private static final String TAG = "Results";
  private final String method;
  private final long nanoTime;
  private final long milliTime;
  private final long secTime;
  private String fileName;

  Results(String method, long nanoTime, long milliTime, long secTime) {
    this.method = method;
    this.nanoTime = nanoTime;
    this.milliTime = milliTime;
    this.secTime = secTime;
  }

  Results(final String fileName) {
    this(null, 0, 0, 0);
    this.fileName = fileName;
    createFile();
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

  synchronized void writeStringAsFile(Object src) {
    final File file = createFile();
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

  private File createFile() {
    File file = new File(Environment.getExternalStorageDirectory() + File.separator + fileName);
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