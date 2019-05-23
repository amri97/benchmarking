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

import com.github.javafaker.Faker;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;

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
import io.blongho.github.greendao.util.AssetsReader;
import io.blongho.github.greendao.util.ReadFromFile;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";
  private final static Executor executor = Executors.newCachedThreadPool();
  private static DaoSession daoSession;
  final String dbName = "customer_order_greendao";
  final ExecutorCompletionService<String> custom = new ExecutorCompletionService<>(executor);
  private final Object lock = new Object();
  private Gson gson;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    gson = new Gson();

    custom.submit(new ReadFromFile(getApplicationContext(), R.raw.customers10000));

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
    new AsyncDeleteAllFromDatabase(daoSession).execute();
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

    new AsyncReadFromDatabase<>(daoSession, Customer.class).execute();
    new AsyncReadFromDatabase<>(daoSession, Product.class).execute();
    new AsyncReadFromDatabase<>(daoSession, Order.class).execute();
    new AsyncReadFromDatabase<>(daoSession, OrderProduct.class).execute();

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
    getDaoSession();
    Customer[] customers = gson.fromJson(AssetsReader.readFromAssets(getApplicationContext(), R.raw.customers10000),
        Customer[].class);
    Product[] products = gson.fromJson(AssetsReader.readFromAssets(getApplicationContext(), R.raw.products10000),
        Product[].class);
    Order[] orders = gson.fromJson(AssetsReader.readFromAssets(getApplicationContext(), R.raw.orders10000),
        Order[].class);
    OrderProduct[] orderProducts = gson.fromJson(AssetsReader.readFromAssets(getApplicationContext(),
        R.raw.order_products10000), OrderProduct[].class);

    new AsyncWriteToDatabase<Customer>(daoSession).execute(customers);
    new AsyncWriteToDatabase<Product>(daoSession).execute(products);
    new AsyncWriteToDatabase<Order>(daoSession).execute(orders);
    new AsyncWriteToDatabase<OrderProduct>(daoSession).execute(orderProducts);
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

  private Set<Customer> generateCustomers(long number) {
    Faker faker = Faker.instance();
    Set<Customer> customers = new HashSet<>();

    for (long i = 0; i < number; i++) {
      customers.add(new Customer(i, faker.name().fullName(), faker.address().city()));

    }
    return customers;
  }
}
