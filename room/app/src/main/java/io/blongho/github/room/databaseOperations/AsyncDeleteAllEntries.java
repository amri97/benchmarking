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

import io.blongho.github.room.database.AppDatabaseRepository;
import io.blongho.github.room.util.MethodTimer;

public class AsyncDeleteAllEntries extends AsyncTask<Void, Void, Void> {
  private static final String TAG = "AsyncDeleteAllEntries";
  protected AppDatabaseRepository repository;

  public AsyncDeleteAllEntries(final AppDatabaseRepository repository) {
    this.repository = repository;
  }

  @Override
  protected Void doInBackground(Void... voids) {
    final MethodTimer timer = new MethodTimer("Deleting all " + repository.customerCount() + " Customers");
    timer.start();
    repository.deleteAllCustomers();
    timer.stop();
    timer.showResults();
    final MethodTimer productTimer = new MethodTimer("Deleting all " + repository.productCount() + " Products");
    productTimer.start();
    repository.deleteAllProducts();
    productTimer.stop();
    productTimer.showResults();
    final MethodTimer orderTimer = new MethodTimer("Deleting all " + repository.orderCount() + " Orders");
    orderTimer.start();
    repository.deleteAllOrders();
    orderTimer.stop();
    orderTimer.showResults();
    final MethodTimer opdtTimer = new MethodTimer("Deleting all " + repository.orderProductCount() + " OrderProducts");
    opdtTimer.start();
    repository.deleteAllOrderProducts();
    opdtTimer.stop();
    opdtTimer.showResults();
    return null;
  }

}
