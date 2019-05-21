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

package io.blongho.github.greendao;

import android.os.Bundle;
import android.util.Log;
import android.util.TimingLogger;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import io.blongho.github.greendao.database.DatabaseHelper;
import io.blongho.github.greendao.model.Customer;
import io.blongho.github.greendao.model.DaoMaster;
import io.blongho.github.greendao.model.DaoSession;
import io.blongho.github.greendao.model.Order;
import io.blongho.github.greendao.model.OrderProduct;
import io.blongho.github.greendao.model.Product;
import io.blongho.github.greendao.util.AssetsReader;
import io.blongho.github.greendao.util.MethodTimer;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";
  private static DaoSession daoSession;
  final String dbName = "customer_order_greendao";
  private final Object lock = new Object();
  private Gson gson;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    gson = new Gson();

  }

  /**
   * Create db.
   *
   * @param view the view
   */
  public void createDb(View view) {
    final TimingLogger logger = new TimingLogger(TAG, "Performance of greenDao");
    logger.addSplit("Initialize database");
    daoSession = getDaoSession();
    logger.dumpToLog();
    showSnackBar(view, "createDb");

  }

  /**
   * Clear db.
   *
   * @param view the view
   */
  public void clearDb(View view) {
    getDaoSession();
    daoSession.getCustomerDao().deleteAll();
    daoSession.getProductDao().deleteAll();
    daoSession.getOrderDao().deleteAll();
    daoSession.getOrderProductDao().deleteAll();
    showSnackBar(view, "clearDb");
  }

  /**
   * Delete from db.
   *
   * @param view the view
   */
  public void deleteFromDb(View view) {
    // TODO implement code for deleting an item or items from the database
    showSnackBar(view, "deleteFromDb");
  }

  /**
   * Update entry.
   *
   * @param view the view
   */
  public void updateEntry(View view) {
    // TODO implement code for updating an entry or entries in the database
    showSnackBar(view, "updateEntry");
  }

  /**
   * Read data.
   *
   * @param view the view
   */
  public void readData(View view) {
    getDaoSession();
    // TODO implement logic for reading some data from the database
    List<Customer> customers = daoSession.getCustomerDao().loadAll();
    //showItems(customers);

    List<Order> orders = daoSession.getOrderDao()._queryProduct_OrdersWithThisProduct(12);
    Order order = orders.get(0);
    Customer customer = order.getOrderCustomer();
    showItems(customer);
    List<Product> products = daoSession.getProductDao()._queryOrder_Products(10249);
    showItems(products);

    long key = daoSession.getCustomerDao().getKey(customers.get(43));
    showItems(key);
    showSnackBar(view, "readData");
  }

  private <T> void showItems(List<T> customers) {
    for (Object customer : customers) {
      Log.i(TAG, "showItems: " + customer);
    }
  }

  private <T> void showItems(T item) {
    Log.i(TAG, "showItems: " + item);
  }

  private <T> void showItems(T[] items) {
    for (Object item : items) {
      Log.i(TAG, "showItems: " + item);
    }
  }

  /**
   * Load data.
   *
   * @param view the view
   */
  public void loadData(View view) {
    getDaoSession();
    // TODO logic for loading the database with data
    MethodTimer timer = new MethodTimer("LoadCustomers");

    Customer[] customers = gson.fromJson(AssetsReader.readFromAssets(getApplicationContext(), R.raw.customer),
        Customer[].class);

    //showItems(customers);
    Product[] products = gson.fromJson(AssetsReader.readFromAssets(getApplicationContext(), R.raw.products),
        Product[].class);
    //showItems(products);

    Order[] orders = gson.fromJson(AssetsReader.readFromAssets(getApplicationContext(), R.raw.orders), Order[].class);
    //showItems(orders);

    OrderProduct[] orderProducts = gson.fromJson(AssetsReader.readFromAssets(getApplicationContext(),
        R.raw.order_products), OrderProduct[].class);

    final TimingLogger logger = new TimingLogger(TAG, "Performance of greenDao");
    logger.addSplit("addCustomers");
    daoSession.getCustomerDao().insertOrReplaceInTx(customers);
    //showItems(orderProducts);
    logger.addSplit("addProducts");
    daoSession.getProductDao().insertOrReplaceInTx(products);

    logger.addSplit("addOrders");
    daoSession.getOrderDao().insertOrReplaceInTx(orders);

    logger.addSplit("addOrderProducts");
    daoSession.getOrderProductDao().insertOrReplaceInTx(orderProducts);
    logger.dumpToLog();
    showSnackBar(view, "loadData");
  }

  private void showSnackBar(View view, final String method) {
    Snackbar.make(view, method + " ==> Implement this method", Snackbar.LENGTH_SHORT).show();
  }

  private DaoSession getDaoSession() {
    synchronized (lock) {
      if (daoSession == null) {
        daoSession = new DaoMaster(new DatabaseHelper(getApplicationContext(), dbName).getWritableDb()).newSession();
      }
    }
    return daoSession;
  }
}
