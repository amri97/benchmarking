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

package io.blongho.github.room;

import android.os.Bundle;
import android.util.Log;
import android.util.TimingLogger;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AppCompatActivity;
import io.blongho.github.room.asynctasks.AsyncAdd;
import io.blongho.github.room.asynctasks.AsyncDeleteAllEntries;
import io.blongho.github.room.asynctasks.AsyncReadCustomer;
import io.blongho.github.room.asynctasks.AsyncReadOrderProduct;
import io.blongho.github.room.asynctasks.AsyncReadOrders;
import io.blongho.github.room.asynctasks.AsyncReadProduct;
import io.blongho.github.room.database.AppDatabaseRepository;
import io.blongho.github.room.model.Customer;
import io.blongho.github.room.model.Order;
import io.blongho.github.room.model.OrderProduct;
import io.blongho.github.room.model.Product;
import io.blongho.github.room.util.AssetsReader;
import io.blongho.github.room.util.MethodTimer;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";
  private final static int CUSTOMER_10K = R.raw.customers10000;
  private final static int PRODUCT_10K = R.raw.products10000;
  private final static int ORDER_10K = R.raw.orders10000;
  private final static int OP_10K = R.raw.order_products10000;
  private AppDatabaseRepository repository;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

  }

  /**
   * Create db.
   *
   * @param view the view
   */
  public void createDb(View view) {
    initializeRepository();
    showSnackBar(view, "createDb");

  }

  private void initializeRepository() {
    if (repository == null) {
      final MethodTimer timer = new MethodTimer(TAG + ", Initializing the database");
      timer.start();
      repository = new AppDatabaseRepository(getApplicationContext());
      timer.stop();
      timer.showResults();
    }
  }

  private void showSnackBar(View view, final String method) {
    Snackbar.make(view, method + " ==> Implement this method", Snackbar.LENGTH_SHORT).show();
  }

  /**
   * Clear db.
   *
   * @param view the view
   */
  public void clearDb(View view) {
    initializeRepository();
    final TimingLogger logger = new TimingLogger(TAG, "Clearing database entries");
    logger.addSplit("Drop entries");
    new AsyncDeleteAllEntries(repository).execute();
    logger.dumpToLog();
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
    List<Customer> customers = new ArrayList<>();
    List<Product> products = new ArrayList<>();
    List<Order> orders = new ArrayList<>();
    List<OrderProduct> orderProducts = new ArrayList<>();
    initializeRepository();
    try {
      final TimingLogger logger = new TimingLogger(TAG, "Read from database");
      logger.addSplit("readCustomers");
      customers = new AsyncReadCustomer(repository).execute().get();
      logger.addSplit("readProducts");
      products = new AsyncReadProduct(repository).execute().get();
      logger.addSplit("readOrders");
      orders = new AsyncReadOrders(repository).execute().get();
      logger.addSplit("readOrderProducts");
      orderProducts = new AsyncReadOrderProduct(repository).execute().get();
      logger.dumpToLog();
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    showSnackBar(view, "readData");
  }

  private <T> void showItems(List<T> items) {
    if (items.isEmpty()) {
      Log.i(TAG, "showItems: Nothting to show for " + items.getClass().getName());
    }
    for (final T item : items) {
      Log.i(TAG, "showItems: " + item);
    }
  }

  /**
   * Load data.
   *
   * @param view the view
   */
  public void loadData(View view) {
    Gson gson = new Gson();
    Customer[] customers = gson
        .fromJson(AssetsReader.readFromAssets(getApplicationContext(), CUSTOMER_10K), Customer[].class);
    Product[] products = gson
        .fromJson(AssetsReader.readFromAssets(getApplicationContext(), PRODUCT_10K), Product[].class);
    Order[] orders = gson
        .fromJson(AssetsReader.readFromAssets(getApplicationContext(), ORDER_10K), Order[].class);
    OrderProduct[] orderProducts = gson
        .fromJson(AssetsReader.readFromAssets(getApplicationContext(), OP_10K), OrderProduct[].class);

    initializeRepository();
    new AsyncAdd<Customer>(repository, Customer.class).execute(customers);
    new AsyncAdd<Product>(repository, Product.class).execute(products);
    new AsyncAdd<Order>(repository, Order.class).execute(orders);
    new AsyncAdd<OrderProduct>(repository, OrderProduct.class).execute(orderProducts);
    /*
    final TimingLogger logger = new TimingLogger(TAG, "Populating the database");
    logger.addSplit("loadCustomers");
    new AsyncAddCustomer(repository).execute(customers);
    logger.addSplit("loadProducts");
    new AsyncAddProduct(repository).execute(products);
    logger.addSplit("loadOrders");
    new AsyncAddOrder(repository).execute(orders);
    logger.addSplit("loadOrderProducts");
    new AsyncAddOrderProduct(repository).execute(orderProducts);
    logger.dumpToLog();
    */
  }
}
