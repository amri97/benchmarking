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
import android.util.Log;

import io.blongho.github.greendao.model.Customer;
import io.blongho.github.greendao.model.CustomerDao;
import io.blongho.github.greendao.model.DaoSession;
import io.blongho.github.greendao.model.Order;
import io.blongho.github.greendao.model.OrderDao;
import io.blongho.github.greendao.model.OrderProduct;
import io.blongho.github.greendao.model.OrderProductDao;
import io.blongho.github.greendao.model.Product;
import io.blongho.github.greendao.model.ProductDao;
import io.blongho.github.greendao.util.MethodTimer;

/**
 * The type Async read from database.
 *
 * @param <T> the type parameter
 */
public class AsyncReadFromDatabase<T> extends AsyncTask<Void, Void, Void> {
  private static final String TAG = "AsyncReadFromDatabase";
  private final DaoSession daoSession;
  private final Class<T> objectClass;

  /**
   * Instantiates a new Async read from database.
   *
   * @param daoSession  the dao session
   * @param objectClass the object class
   */
  public AsyncReadFromDatabase(DaoSession daoSession, Class<T> objectClass) {
    this.daoSession = daoSession;
    this.objectClass = objectClass;
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
  // If there are no entries in the database, do not call entityDao.loadAll()
  @Override
  protected Void doInBackground(Void... voids) {
    if (objectClass.isInstance(new Customer())) {
      final MethodTimer timer = new MethodTimer(TAG);
      final CustomerDao customerDao = daoSession.getCustomerDao();
      long customersInSystem = customerDao.count();
      if (customersInSystem == 0) {
        Log.e(TAG, "doInBackground() called with: Customers = [" + customersInSystem + "]");
        return null;
      }
      timer.setTag("Reading " + customersInSystem + " Customers from database");
      timer.start();
      daoSession.getCustomerDao().loadAll();
      timer.stop();
      timer.showResults();
    } else if (objectClass.isInstance(new Product())) {
      final MethodTimer timer = new MethodTimer(TAG);
      final ProductDao productDao = daoSession.getProductDao();
      long products = productDao.count();
      if (products == 0) {
        Log.e(TAG, "doInBackground() called with: products = [" + products + "]");
        return null;
      }
      timer.setTag("Reading " + products + " Products form the database");
      timer.start();
      productDao.loadAll();
      timer.stop();
      timer.showResults();
    } else if (objectClass.isInstance(new Order())) {
      final MethodTimer timer = new MethodTimer(TAG);
      final OrderDao orderDao = daoSession.getOrderDao();
      long orders = orderDao.count();
      if (orders == 0) {
        Log.e(TAG, "doInBackground() called with: Orders = [" + orders + "]");
        return null;
      }
      timer.setTag("Reading " + orders + " Orders from the database");
      timer.start();
      daoSession.getOrderDao().loadAll();
      timer.stop();
      timer.showResults();
    } else if (objectClass.isInstance(new OrderProduct())) {
      final MethodTimer timer = new MethodTimer(TAG);
      final OrderProductDao opdao = daoSession.getOrderProductDao();
      long odp = opdao.count();
      if (odp == 0) {
        Log.e(TAG, "doInBackground() called with: OrderProducts = [" + odp + "]");
        return null;
      }
      timer.setTag("Reading " + odp + " OrderProducts from database");
      timer.start();
      daoSession.getOrderProductDao().loadAll();
      timer.stop();
      timer.showResults();
    }

    return null;
  }
}
