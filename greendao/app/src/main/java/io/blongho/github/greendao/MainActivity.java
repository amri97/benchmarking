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
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;

import androidx.annotation.RawRes;
import androidx.appcompat.app.AppCompatActivity;
import io.blongho.github.greendao.asynctasks.AsyncDeleteAllFromDatabase;
import io.blongho.github.greendao.asynctasks.AsyncReadFromDatabase;
import io.blongho.github.greendao.asynctasks.AsyncWriteToDatabase;
import io.blongho.github.greendao.database.DatabaseHelper;
import io.blongho.github.greendao.model.Customer;
import io.blongho.github.greendao.model.DaoMaster;
import io.blongho.github.greendao.model.DaoSession;
import io.blongho.github.greendao.model.Order;
import io.blongho.github.greendao.model.OrderProduct;
import io.blongho.github.greendao.model.Product;
import io.blongho.github.greendao.util.MethodTimer;
import io.blongho.github.greendao.util.ReadFromFile;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";
  private final static Executor executor = Executors.newCachedThreadPool();
  private static DaoSession writableDaoSession;
  private static DaoSession readableDaoSession;
  final String dbName = "customer_order_greendao";
  private final Object writeLock = new Object();
  private final Object readLock = new Object();
  private ExecutorCompletionService<String> customerService;
  private ExecutorCompletionService<String> productService;
  private ExecutorCompletionService<String> orderService;
  private ExecutorCompletionService<String> orderProductService;
  private Gson gson;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    gson = new Gson();

  }

  private void initCompletionServices() {
    customerService = new ExecutorCompletionService<>(executor);
    productService = new ExecutorCompletionService<>(executor);
    orderService = new ExecutorCompletionService<>(executor);
    orderProductService = new ExecutorCompletionService<>(executor);
  }

  private void submitFileReadingRequest(ExecutorCompletionService<String> completionService,
                                        @RawRes int fileResourceID) {
    completionService.submit(new ReadFromFile(getApplicationContext(), fileResourceID));
  }

  /**
   * Create db.
   *
   * @param view the view
   */
  public void createDb(View view) {
    MethodTimer timer = new MethodTimer(TAG + "initializing database");
    timer.start();
    writableDaoSession = getWritableDaoSession();
    readableDaoSession = getReadableDaoSession();
    timer.stop();
    timer.showResults();
    showSnackBar(view, "createDb");

  }

  /**
   * Clear db.
   *
   * @param view the view
   */
  public void clearDb(View view) {
    getWritableDaoSession();
    new AsyncDeleteAllFromDatabase(writableDaoSession).execute();
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
    getReadableDaoSession();
    // TODO implement logic for reading some data from the database

    new AsyncReadFromDatabase<>(readableDaoSession, Customer.class).execute();
    new AsyncReadFromDatabase<>(readableDaoSession, Product.class).execute();
    new AsyncReadFromDatabase<>(readableDaoSession, Order.class).execute();
    new AsyncReadFromDatabase<>(readableDaoSession, OrderProduct.class).execute();

    showSnackBar(view, "readData");
  }

  private <T> void showItems(List<T> customers) {
    for (Object customer : customers) {
      Log.i(TAG, "showItems: " + customer);
    }
  }

  private <T> void showItem(T item) {
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
    initCompletionServices();
    submitFileReadingRequest(productService, R.raw.products10000);
    submitFileReadingRequest(customerService, R.raw.customers10000);
    submitFileReadingRequest(orderService, R.raw.orders10000);
    submitFileReadingRequest(orderProductService, R.raw.order_products10000);

    getWritableDaoSession();
    try {
      Customer[] customers = gson.fromJson(customerService.take().get(), Customer[].class);
      new AsyncWriteToDatabase<Customer>(writableDaoSession).execute(customers);

      Product[] products = gson.fromJson(productService.take().get(), Product[].class);
      new AsyncWriteToDatabase<Product>(writableDaoSession).execute(products);

      Order[] orders = gson.fromJson(orderService.take().get(), Order[].class);
      new AsyncWriteToDatabase<Order>(writableDaoSession).execute(orders);

      OrderProduct[] orderProducts = gson.fromJson(orderProductService.take().get(), OrderProduct[].class);
      new AsyncWriteToDatabase<OrderProduct>(writableDaoSession).execute(orderProducts);

    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    showSnackBar(view, "loadData");
  }

  private void showSnackBar(View view, final String method) {
    Snackbar.make(view, method + " ==> Implement this method", Snackbar.LENGTH_SHORT).show();
  }

  private DaoSession getWritableDaoSession() {
    synchronized (writeLock) {
      if (writableDaoSession == null) {
        writableDaoSession =
            new DaoMaster(new DatabaseHelper(MainActivity.this, dbName).getWritableDb()).newSession();
      }
    }
    return writableDaoSession;
  }

  private DaoSession getReadableDaoSession() {
    synchronized (readLock) {
      if (readableDaoSession == null) {
        readableDaoSession =
            new DaoMaster(new DatabaseHelper(MainActivity.this, dbName).getWritableDb()).newSession();
      }
    }
    return readableDaoSession;
  }
}
