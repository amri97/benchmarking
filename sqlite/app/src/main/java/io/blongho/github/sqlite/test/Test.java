package io.blongho.github.sqlite.test;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;

import androidx.annotation.RawRes;
import io.blongho.github.sqlite.R;
import io.blongho.github.sqlite.database.DatabaseManager;
import io.blongho.github.sqlite.databaseOperations.AsyncDeleteAll;
import io.blongho.github.sqlite.databaseOperations.AsyncRead;
import io.blongho.github.sqlite.databaseOperations.InsertCustomer;
import io.blongho.github.sqlite.databaseOperations.InsertOrder;
import io.blongho.github.sqlite.databaseOperations.InsertOrderProduct;
import io.blongho.github.sqlite.databaseOperations.InsertProduct;
import io.blongho.github.sqlite.model.Customer;
import io.blongho.github.sqlite.model.Order;
import io.blongho.github.sqlite.model.OrderProduct;
import io.blongho.github.sqlite.model.Product;
import io.blongho.github.sqlite.util.MethodTimer;
import io.blongho.github.sqlite.util.ReadFromFile;

public class Test implements TestSuiteInterface {
  private static Executor executor = Executors.newCachedThreadPool();
  private static DatabaseManager dbManager;
  private final Context context;
  private ExecutorCompletionService<String> customerService;
  private ExecutorCompletionService<String> productService;
  private ExecutorCompletionService<String> orderService;
  private ExecutorCompletionService<String> orderProductService;
  private Customer[] customers;
  private Product[] products;
  private Order[] orders;
  private OrderProduct[] orderProducts;

  public Test(Context context) {
    this.context = context;
    getData();
    init();
  }

  @Override
  public void init() {
    new ExecutorCompletionService<Void>(executor).submit(() -> {
      MethodTimer.FILE_NAME = "sqlInsert_1_000.json";
      final MethodTimer timer = new MethodTimer("Initializing the database");
      timer.start();
      dbManager = new DatabaseManager(context);
      DatabaseManager.getWritableDatabase();
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
      new InsertCustomer(dbManager, customers);
      new InsertProduct(dbManager, products);
      new InsertOrder(dbManager, orders);
      new InsertOrderProduct(dbManager, orderProducts);
    } else {
      Toast.makeText(context.getApplicationContext(), "Test data not ready", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void create() {
    new ExecutorCompletionService<Void>(executor).submit(() -> {
      create(customers, products, orders, orderProducts);
      return null;
    });
  }

  @Override
  public void read() {
    new AsyncRead(dbManager).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
  }

  @Override
  public void update() {
    new ExecutorCompletionService<Void>(executor).submit(() -> {
      final long customersInSystem = dbManager.customerCount();
      final MethodTimer update = new MethodTimer("Updating " + customersInSystem + " Customers");
      update.start();
      dbManager.updateCustomer(customers);
      update.stop();
      update.showResults();
      return null;
    });

  }

  // run this and then call read to see if orders and OrderProducts are null.
  // If they are null, then cascading has been enforced
  @Override
  public void delete() {
    new ExecutorCompletionService<Void>(executor).submit(() -> {
      final int numberOfCustomers = (int) dbManager.customerCount();
      final MethodTimer timer = new MethodTimer("Deleting all " + numberOfCustomers + " Customers");
      timer.start();
      dbManager.deleteAllCustomers();
      timer.stop();
      timer.showResults();
      return null;
    });

  }

  @Override
  public void deleteAll() {
    new AsyncDeleteAll(dbManager).execute();
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

  private boolean isTestReady() {
    return (customers.length > 0 && products.length > 0 && orders.length > 0 && orderProducts.length > 0);
  }
}
