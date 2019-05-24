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

package io.blongho.github.sqlite;

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
import io.blongho.github.sqlite.AsyncTasks.AsyncDeleteAll;
import io.blongho.github.sqlite.AsyncTasks.AsyncInitialize;
import io.blongho.github.sqlite.AsyncTasks.AsyncInsert;
import io.blongho.github.sqlite.database.DatabaseManager;
import io.blongho.github.sqlite.model.Customer;
import io.blongho.github.sqlite.model.Order;
import io.blongho.github.sqlite.model.OrderProduct;
import io.blongho.github.sqlite.model.Product;
import io.blongho.github.sqlite.util.MethodTimer;
import io.blongho.github.sqlite.util.ReadFromFile;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";
  private final static Executor executor = Executors.newCachedThreadPool();
  private static DatabaseManager dbManager;
  private ExecutorCompletionService<String> customerService;
  private ExecutorCompletionService<String> productService;
  private ExecutorCompletionService<String> orderService;
  private ExecutorCompletionService<String> orderProductService;
  private Gson gson;

  /**
   * Clear db.
   *
   * @param view the view
   */
  public static void clearDb(final View view) {
    // TODO check why dropping entries does not work
    new AsyncDeleteAll(dbManager).execute();
    showSnackBar(view, "clearDb COMPLETED");

  }

  private static void showSnackBar(final View view, final String method) {
    Snackbar.make(view, method + " ==> Implement this method", Snackbar.LENGTH_SHORT).show();
  }

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    dbManager = new DatabaseManager(this.getApplicationContext());
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
  public void createDb(final View view) {
    new AsyncInitialize(dbManager).execute();
    showSnackBar(view, "createDb ");
  }

  /**
   * Delete from db.
   *
   * @param view the view
   */
  public void deleteFromDb(final View view) {
    // TODO implement code for deleting an item or items from the database

    showSnackBar(view, "deleteFromDb");
  }

  /**
   * Load data.
   *
   * @param view the view
   */
  public void loadData(final View view) {
    initCompletionServices();
    submitFileReadingRequest(productService, R.raw.products10000);
    submitFileReadingRequest(customerService, R.raw.customers10000);
    submitFileReadingRequest(orderService, R.raw.orders10000);
    submitFileReadingRequest(orderProductService, R.raw.order_products10000);

    try {
      Customer[] customers = gson.fromJson(customerService.take().get(), Customer[].class);
      new AsyncInsert<Customer>(dbManager).execute(customers);

      Product[] products = gson.fromJson(productService.take().get(), Product[].class);
      new AsyncInsert<Product>(dbManager).execute(products);

      Order[] orders = gson.fromJson(orderService.take().get(), Order[].class);
      new AsyncInsert<Order>(dbManager).execute(orders);

      OrderProduct[] orderProducts = gson.fromJson(orderProductService.take().get(), OrderProduct[].class);
      new AsyncInsert<OrderProduct>(dbManager).execute(orderProducts);

      showSnackBar(view, "loadData");
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

  /**
   * Read data.
   *
   * @param view the view
   */
  public void readData(final View view) {
    // TODO check why reading asynchronously does not work
    MethodTimer timer = new MethodTimer(TAG + "::reading from db");
    timer.start();
    List<Customer> customers =  dbManager.getAllCustomers();
    for (Customer customer : customers) {
      Log.d(TAG, "readData() called with: view = [" + customer+ "]");
    }
    timer.stop();
    timer.showResults();

    /*
    new AsyncRead<>(dbManager, Customer.class).execute();
    new AsyncRead<Product>(dbManager, Product.class).execute();
    new AsyncRead<Order>(dbManager, Order.class).execute();
    new AsyncRead<OrderProduct>(dbManager, OrderProduct.class).execute();
*/
    showSnackBar(view, "readData");
  }

  /**
   * Update entry.
   *
   * @param view the view
   */
  public void updateEntry(final View view) {
    // TODO implement code for updating an entry or entries in the database
    showSnackBar(view, "updateEntry");
  }
}
