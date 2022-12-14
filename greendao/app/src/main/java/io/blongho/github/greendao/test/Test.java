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

package io.blongho.github.greendao.test;

import android.content.Context;

import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;

import androidx.annotation.RawRes;
import io.blongho.github.greendao.R;
import io.blongho.github.greendao.database.DaoSessionInstance;
import io.blongho.github.greendao.databaseOperations.AsyncDeleteAllFromDatabase;
import io.blongho.github.greendao.databaseOperations.InsertCustomers;
import io.blongho.github.greendao.databaseOperations.InsertOrderProducts;
import io.blongho.github.greendao.databaseOperations.InsertOrders;
import io.blongho.github.greendao.databaseOperations.InsertProducts;
import io.blongho.github.greendao.databaseOperations.ReadCustomer;
import io.blongho.github.greendao.databaseOperations.ReadOrder;
import io.blongho.github.greendao.databaseOperations.ReadOrderProducts;
import io.blongho.github.greendao.databaseOperations.ReadProducts;
import io.blongho.github.greendao.model.Customer;
import io.blongho.github.greendao.model.DaoSession;
import io.blongho.github.greendao.model.Order;
import io.blongho.github.greendao.model.OrderProduct;
import io.blongho.github.greendao.model.Product;
import io.blongho.github.greendao.util.MethodTimer;
import io.blongho.github.greendao.util.ReadFromFile;

/**
 * The type Test.
 */
public class Test implements TestSuiteInterface {
  private static Executor executor = Executors.newCachedThreadPool();
  private static DaoSession daoSession;
  private final Context context;

  private ExecutorCompletionService<String> customerService;
  private ExecutorCompletionService<String> productService;
  private ExecutorCompletionService<String> orderService;
  private ExecutorCompletionService<String> orderProductService;
  private Customer[] customers;
  private Product[] products;
  private Order[] orders;
  private OrderProduct[] orderProducts;

  /**
   * Instantiates a new Test.
   *
   * @param context the context
   */
  public Test(Context context) {
    this.context = context;
    getData();
    init();
  }

  @Override
  public void init() {
    Executors.newCachedThreadPool().submit(() -> {
      MethodTimer.FILE_NAME = "prof-gd_1_000.json";
      final MethodTimer timer = new MethodTimer("Initializing the database");
      timer.start();
      getWritableDaoSession();
      timer.stop();
      timer.showResults();
      return null;
    });
  }

  /**
   * Read all the data from file
   * <p>Call this method before running Create()</p>
   */
  private void getData() {
    initCompletionServices();
    submitFileReadingRequest(productService, R.raw.products1000);
    submitFileReadingRequest(customerService, R.raw.customers1000);
    submitFileReadingRequest(orderService, R.raw.orders1000);
    submitFileReadingRequest(orderProductService, R.raw.order_products1000);
    final Gson gson = new Gson();
    try {
      customers = gson.fromJson(customerService.take().get(), Customer[].class);
      products = gson.fromJson(productService.take().get(), Product[].class);
      orders = gson.fromJson(orderService.take().get(), Order[].class);
      orderProducts = gson.fromJson(orderProductService.take().get(), OrderProduct[].class);
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void create(Customer[] customers, Product[] products, Order[] orders, OrderProduct[] orderProducts) {
    if (isTestReady()) {
      new ExecutorCompletionService<Void>(executor).submit(() -> {
        new InsertCustomers(daoSession, new MethodTimer("Create customers"), customers);
        new InsertProducts(daoSession, new MethodTimer("Create product"), products);
        new InsertOrders(daoSession, new MethodTimer("Create orders"), orders);
        new InsertOrderProducts(daoSession, new MethodTimer("Create OrderProducts"), orderProducts);
      }, null);
    }

  }

  @Override
  public void create() {
    create(customers, products, orders, orderProducts);
  }

  @Override
  public void read() {
    new ExecutorCompletionService<Void>(executor).submit(() -> {
      new ReadCustomer(daoSession, new MethodTimer(""), null);
      new ReadProducts(daoSession, new MethodTimer(""), null);
      new ReadOrder(daoSession, new MethodTimer(""), null);
      new ReadOrderProducts(daoSession, new MethodTimer(""), null);
      return null;
    });
  }

  @Override
  public void update() {
    Executors.newCachedThreadPool().submit(() -> {
      final long customerCount = daoSession.getCustomerDao().count();
      final MethodTimer timer = new MethodTimer("Updating " + customerCount + " Customers");
      timer.start();
      daoSession.getCustomerDao().updateInTx(customers);
      timer.stop();
      timer.showResults();
      return null;
    });
  }

  //**** greenDAO does not enforce cascading ****

  /**
   * Run potentially long-running actions on background thread
   */
  @Override
  public void delete() {
    Executors.newCachedThreadPool().submit(() -> {
      final int numberOfCustomers = (int) daoSession.getCustomerDao().count();
      final MethodTimer timer = new MethodTimer("Deleting " + numberOfCustomers + " customers");
      timer.start();
      daoSession.getCustomerDao().deleteAll();
      timer.stop();
      timer.showResults();
      return null;
    });
  }

  @Override
  public void deleteAll() {
    new AsyncDeleteAllFromDatabase(daoSession).execute();
  }

  private void initCompletionServices() {
    customerService = new ExecutorCompletionService<>(executor);
    productService = new ExecutorCompletionService<>(executor);
    orderService = new ExecutorCompletionService<>(executor);
    orderProductService = new ExecutorCompletionService<>(executor);

  }

  private void submitFileReadingRequest(ExecutorCompletionService<String> completionService,
                                        @RawRes int fileResourceID) {
    completionService.submit(new ReadFromFile(context.getApplicationContext(), fileResourceID));
  }

  /**
   * Get a single instance of the DaoSession
   */
  private void getWritableDaoSession() {
    daoSession = DaoSessionInstance.getInstance(context);
  }

  private boolean isTestReady() {
    return (customers.length > 0 && products.length > 0 && orders.length > 0 && orderProducts.length > 0);
  }

  @Override
  public void destroy() {
    daoSession.clear();
  }
}
