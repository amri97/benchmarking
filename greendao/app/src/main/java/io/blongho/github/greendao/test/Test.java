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

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;

import androidx.annotation.RawRes;
import io.blongho.github.greendao.R;
import io.blongho.github.greendao.database.DaoSessionInstance;
import io.blongho.github.greendao.databaseOperations.AsyncDeleteAllFromDatabase;
import io.blongho.github.greendao.databaseOperations.AsyncReadFromDatabase;
import io.blongho.github.greendao.databaseOperations.WriteCustomers;
import io.blongho.github.greendao.databaseOperations.WriteOrderProducts;
import io.blongho.github.greendao.databaseOperations.WriteOrders;
import io.blongho.github.greendao.databaseOperations.WriteProducts;
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
  private static final String TAG = "Test";
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

  /**
   * Generate a random number in the range min and max
   *
   * @param min the minimum number
   * @param max the upper bound
   * @return a random integer
   */
  private static int getRandomNumberInRange(int min, int max) {
    if (min >= max) {
      throw new IllegalArgumentException("max must be greater than min");
    }
    Random r = new Random();
    return r.nextInt((max - min) + 1) + min;
  }

  @Override
  public void init() {
    MethodTimer.FILE_NAME = "1_000.json";
    final MethodTimer timer = new MethodTimer("Initializing the database");
    timer.start();
    getWritableDaoSession();
    timer.stop();
    timer.showResults();

  }

  @Override
  public void create(Customer[] customers, Product[] products, Order[] orders, OrderProduct[] orderProducts) {
    if (isTestReady()) {
      new ExecutorCompletionService<Void>(executor).submit(() -> {
        new WriteCustomers(daoSession, new MethodTimer("Create customers", context), customers);
        new WriteProducts(daoSession, new MethodTimer("Create product", context), products);
        new WriteOrders(daoSession, new MethodTimer("Create orders", context), orders);
        new WriteOrderProducts(daoSession, new MethodTimer("Create OrderProducts", context), orderProducts);
      }, null);
    }

  }

  @Override
  public void create() {
    create(customers, products, orders, orderProducts);
  }

  @Override
  public void read() {
    new AsyncReadFromDatabase<>(daoSession, Customer.class).execute();
    new AsyncReadFromDatabase<>(daoSession, Product.class).execute();
    new AsyncReadFromDatabase<>(daoSession, Order.class).execute();
    new AsyncReadFromDatabase<>(daoSession, OrderProduct.class).execute();
  }

  @Override
  public void update() {
    // Update 5 random customers
    final int numberOfCustomers = (int) daoSession.getCustomerDao().count();
    for (int i = 1; i <= 5; i++) {
      final long randomCustomer = getRandomNumberInRange(i, numberOfCustomers);

      final Customer bernard = new Customer(randomCustomer, "Bernard Longho", "City");
      final MethodTimer timer = new MethodTimer("Update customer number " + randomCustomer);
      timer.start();
      daoSession.getCustomerDao().update(bernard);
      timer.stop();
      timer.showResults();
    }
  }

  @Override
  public void delete() {
    final int numberOfCustomers = (int) daoSession.getCustomerDao().count();
    for (int i = 1; i <= 5; i++) {
      final int randomCustomer = getRandomNumberInRange(1, numberOfCustomers);
      final MethodTimer timer = new MethodTimer("Deleting customer number " + randomCustomer);
      timer.start();
      daoSession.getCustomerDao().deleteByKey((long) randomCustomer);
      timer.stop();
      timer.showResults();
    }
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

  /**
   * Read all the data from file
   * <p>Call this method before running Create()</p>
   */
  private void getData() {
    initCompletionServices();
    submitFileReadingRequest(productService, R.raw.products1000);
    submitFileReadingRequest(customerService, R.raw.customers1000);
    submitFileReadingRequest(orderService, R.raw.order1000);
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

  private boolean isTestReady() {
    return (customers.length > 0 && products.length > 0 && orders.length > 0 && orderProducts.length > 0);
  }

}
