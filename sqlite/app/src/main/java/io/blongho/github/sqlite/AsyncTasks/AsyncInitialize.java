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

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import io.blongho.github.sqlite.database.DatabaseHelper;
import io.blongho.github.sqlite.database.DatabaseManager;
import io.blongho.github.sqlite.util.MethodTimer;

public class AsyncInitialize extends AsyncTask<Void, Void, Void> {
  private static final String TAG = "AsyncInitialize";
  private final DatabaseManager dbManager;

  public AsyncInitialize(final DatabaseManager manager) {
    dbManager = manager;
  }

  /**
   * Override this method to perform a computation on a background thread. The
   * specified parameters are the parameters passed to {@link #execute}
   * by the caller of this task.
   * <p>
   * This method can call {@link #publishProgress} to publish updates
   * on the UI thread.
   *
   * @param voids The parameters of the task.
   * @return A result, defined by the subclass of this task.
   * @see #onPreExecute()
   * @see #onPostExecute
   * @see #publishProgress
   */
  @Override
  protected Void doInBackground(final Void... voids) {
    final MethodTimer timer = new MethodTimer(TAG + "Initializing database");
    timer.start();
    dbManager.getWritableDatabase().setForeignKeyConstraintsEnabled(true);
    timer.stop();
    timer.showResults();
    return null;
  }

  /**
   * <p>Runs on the UI thread after {@link #doInBackground}. The
   * specified result is the value returned by {@link #doInBackground}.</p>
   *
   * <p>This method won't be invoked if the task was cancelled.</p>
   *
   * @param aVoid The result of the operation computed by {@link #doInBackground}.
   * @see #onPreExecute
   * @see #doInBackground
   * @see #onCancelled(Object)
   */
  @Override
  protected void onPostExecute(final Void aVoid) {
    super.onPostExecute(aVoid);
    Log.d(TAG, "onPostExecute: " + dbManager.getWritableDatabase().getPath() + " created");

  }
}
