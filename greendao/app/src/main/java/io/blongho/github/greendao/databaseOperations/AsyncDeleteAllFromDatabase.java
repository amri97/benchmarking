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

package io.blongho.github.greendao.databaseOperations;

import android.os.AsyncTask;

import io.blongho.github.greendao.model.DaoSession;
import io.blongho.github.greendao.util.MethodTimer;

public class AsyncDeleteAllFromDatabase extends AsyncTask<Void, Void, Void> {
  private static final String TAG = "AsyncDeleteAllFromDatabase";
  private final DaoSession daoSession;
  private MethodTimer timer = new MethodTimer(TAG);

  public AsyncDeleteAllFromDatabase(DaoSession daoSession) {
    this.daoSession = daoSession;
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
  protected Void doInBackground(Void... voids) {
    timer.setTag("Dropping all customers");
    timer.start();
    daoSession.getCustomerDao().deleteAll();
    timer.stop();
    timer.setTag("Dropping all products");
    timer.start();
    daoSession.getProductDao().deleteAll();
    timer.stop();
    timer.setTag("Dropping all orders");
    timer.start();
    daoSession.getOrderDao().deleteAll();
    timer.stop();
    timer.setTag("Dropping all order products");
    timer.start();
    daoSession.getOrderProductDao().deleteAll();
    timer.stop();
    timer.showResults();
    return null;
  }
}
