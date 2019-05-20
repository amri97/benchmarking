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

import androidx.appcompat.app.AppCompatActivity;
import io.blongho.github.sqlite.AsyncTasks.AsyncDelete;
import io.blongho.github.sqlite.AsyncTasks.AsyncInitialize;
import io.blongho.github.sqlite.AsyncTasks.AsyncInsert;
import io.blongho.github.sqlite.database.DatabaseManager;
import io.blongho.github.sqlite.model.Customer;
import io.blongho.github.sqlite.model.Order;
import io.blongho.github.sqlite.model.OrderProduct;
import io.blongho.github.sqlite.model.Product;
import io.blongho.github.sqlite.util.AssetsReader;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  /**
   * Clear db.
   *
   * @param view the view
   */
  public void clearDb(final View view) {
    // TODO implement code for clearing the database
    try {
      new AsyncDelete<Customer>(getApplication()).execute().get();
    } catch (final ExecutionException e) {
      e.printStackTrace();
    } catch (final InterruptedException e) {
      e.printStackTrace();
    }
    showSnackBar(view, "clearDb");
  }

  private void showSnackBar(final View view, final String method) {
    Snackbar.make(view, method + " ==> Implement this method", Snackbar.LENGTH_SHORT).show();
  }

  /**
   * Create db.
   *
   * @param view the view
   */
  public void createDb(final View view) {
    new AsyncInitialize(this.getApplication()).execute();
    // TODO implement code for creating the database here
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
    final Gson gson = new Gson();
    final String cust = AssetsReader.readFromAssets(this.getApplicationContext(), R.raw.customer);
    final Customer[] customers = gson.fromJson(cust, Customer[].class);
    final Product[] products = gson.fromJson(AssetsReader.readFromAssets(getApplicationContext(), R.raw.products),
        Product[].class);
    final Order[] orders = gson.fromJson(AssetsReader.readFromAssets(getApplicationContext(), R.raw.orders),
        Order[].class);
    final OrderProduct[] orderProducts = gson.fromJson(AssetsReader.readFromAssets(getApplicationContext(),
        R.raw.order_products), OrderProduct[].class);

    new AsyncInsert<Customer>(new DatabaseManager(getApplicationContext())).execute(customers);
    new AsyncInsert<Product>(new DatabaseManager(getApplicationContext())).execute(products);
    new AsyncInsert<Order>(new DatabaseManager(getApplicationContext())).execute(orders);
    new AsyncInsert<OrderProduct>(new DatabaseManager(getApplicationContext())).execute(orderProducts);
    showSnackBar(view, "loadData");
  }

  /**
   * Read data.
   *
   * @param view the view
   */
  public void readData(final View view) {

    final DatabaseManager manager = new DatabaseManager(getApplicationContext());

    final List<Product> products = manager.getAllProducts();
    for (final Product product : products) {
      Log.d(TAG, "run: " + product);
    }

    final List<Customer> customers = manager.getAllCustomers();
    for (final Customer customer : customers) {
      Log.d(TAG, "readData: " + customer);
    }

    final List<Order> orders = manager.getAllOrders();
    for (final Order order : orders) {
      Log.d(TAG, "readData: " + order);
    }

    final Long deleteCustomer = manager.deleteCustomer(customers.get(23));
    Log.e(TAG, "readData: " + deleteCustomer);
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
