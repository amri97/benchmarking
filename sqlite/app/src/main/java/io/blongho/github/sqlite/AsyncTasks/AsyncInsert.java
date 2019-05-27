/*
 * MIT License
 *
 * Copyright (c) 2019 Bernard Che Longho
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.blongho.github.sqlite.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import io.blongho.github.sqlite.database.DatabaseManager;
import io.blongho.github.sqlite.util.MethodTimer;

public class AsyncInsert<T> extends AsyncTask<T, String, Void> {
  private static final String TAG = "AsyncInsert";
  private DatabaseManager databaseManager;
  private String className;

  public AsyncInsert(final DatabaseManager db) {
    databaseManager = db;
  }

  /**
   * Override this method to perform a computation on a background thread. The specified parameters are the
   * parameters
   * passed to {@link #execute} by the caller of this task.
   * <p>
   * This method can call {@link #publishProgress} to publish updates on the UI thread.
   *
   * @param ts The parameters of the task.
   * @return A result, defined by the subclass of this task.
   * @see #onPreExecute()
   * @see #onPostExecute
   * @see #publishProgress
   */
  @Override
  protected Void doInBackground(final T... ts) {
    if (ts.length > 0) {
      className = ts.getClass().getSimpleName();
      final MethodTimer timer = new MethodTimer(TAG + "Adding " + ts.length + " items from " + className);
      timer.start();
      for (T t : ts) {
        databaseManager.addItem(t);
      }
      timer.stop();
      timer.showResults();
      publishProgress(className);
    }

    return null;
  }
}