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

package io.blongho.github.room.databaseOperations;

import android.os.AsyncTask;
import android.util.Log;

import io.blongho.github.room.database.AppDatabaseRepository;
import io.blongho.github.room.util.MethodTimer;

public class AsyncReadCustomer extends AsyncTask<Void, Void, Void> {
  private static final String TAG = "AsyncReadCustomer";
  private final AppDatabaseRepository repository;

  public AsyncReadCustomer(final AppDatabaseRepository repository) {
    this.repository = repository;
  }

  @Override
  protected Void doInBackground(final Void... voids) {
    final long customers = repository.customerCount();
    if (customers == 0) {
      Log.e(TAG, "doInBackground() called with: Customers = [" + customers + "]");
      return null;
    }
    final MethodTimer timer = new MethodTimer("Reading " + customers + " Customers");
    timer.start();
    repository.getAllCustomers();
    timer.stop();
    timer.showResults();
    return null;
  }
}
