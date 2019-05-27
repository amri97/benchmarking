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
  private static final String TAG = "Test";
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
    new ExecutorCompletionService<Void>(executor).submit(() -> {
      MethodTimer.FILE_NAME = "sql_12_000.json";
      final MethodTimer timer = new MethodTimer("Initializing the database");
      timer.start();
      dbManager = new DatabaseManager(context);
      DatabaseManager.getWritableDatabase();
      timer.stop();
      timer.showResults();
      return null;
    });
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
      // Update 5 random customers
      final int numberOfCustomers = (int) dbManager.customerCount();
      for (int i = 1; i <= 5; i++) {
        final long randomCustomer = getRandomNumberInRange(i, numberOfCustomers);

        final Customer bernard = new Customer(randomCustomer, "Bernard Longho", "City");
        final MethodTimer timer = new MethodTimer("Update customer number " + randomCustomer);
        timer.start();
        dbManager.updateCustomer(bernard);
        timer.stop();
        timer.showResults();
      }
      return null;
    });

  }

  @Override
  public void delete() {
    new ExecutorCompletionService<Void>(executor).submit(() -> {
      final int numberOfCustomers = (int) dbManager.customerCount();
      for (int i = 1; i <= 5; i++) {
        final int randomCustomer = getRandomNumberInRange(1, numberOfCustomers);
        final MethodTimer timer = new MethodTimer("Deleting customer number " + randomCustomer);
        timer.start();
        dbManager.deleteCustomerWithId((long) randomCustomer);
        timer.stop();
        timer.showResults();
      }
      return null;
    });

  }

  @Override
  public void deleteAll() {
    new AsyncDeleteAll(dbManager).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
   * Read all the data from file
   * <p>Call this method before running Create()</p>
   */
  private void getData() {
    initCompletionServices();
    submitFileReadingRequest(productService, R.raw.products12000);
    submitFileReadingRequest(customerService, R.raw.customers12000);
    submitFileReadingRequest(orderService, R.raw.order12000);
    submitFileReadingRequest(orderProductService, R.raw.order_products12000);
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
