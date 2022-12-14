package io.blongho.github.room.test;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;

import androidx.annotation.RawRes;
import io.blongho.github.room.R;
import io.blongho.github.room.database.AppDatabaseRepository;
import io.blongho.github.room.databaseOperations.AsyncAddCustomer;
import io.blongho.github.room.databaseOperations.AsyncAddOrder;
import io.blongho.github.room.databaseOperations.AsyncAddOrderProduct;
import io.blongho.github.room.databaseOperations.AsyncAddProduct;
import io.blongho.github.room.databaseOperations.AsyncDeleteAllEntries;
import io.blongho.github.room.databaseOperations.AsyncReadCustomer;
import io.blongho.github.room.databaseOperations.AsyncReadOrderProduct;
import io.blongho.github.room.databaseOperations.AsyncReadOrders;
import io.blongho.github.room.databaseOperations.AsyncReadProduct;
import io.blongho.github.room.model.Customer;
import io.blongho.github.room.model.Order;
import io.blongho.github.room.model.OrderProduct;
import io.blongho.github.room.model.Product;
import io.blongho.github.room.util.MethodTimer;
import io.blongho.github.room.util.ReadFromFile;

public class Test implements TestSuiteInterface {
  private static final String TAG = "Test";
  private static Executor executor = Executors.newCachedThreadPool();
  private final Context context;

  private ExecutorCompletionService<String> customerService;
  private ExecutorCompletionService<String> productService;
  private ExecutorCompletionService<String> orderService;
  private ExecutorCompletionService<String> orderProductService;
  private Customer[] customers;
  private Product[] products;
  private Order[] orders;
  private OrderProduct[] orderProducts;
  private AppDatabaseRepository repository;

  public Test(Context context) {
    this.context = context;
    getData();
    init();
  }


  @Override
  public void init() {
    new ExecutorCompletionService<Void>(executor).submit(() -> {
      if (repository == null) {
        MethodTimer.FILE_NAME = "roomInsert_1_000.json";
        final MethodTimer timer = new MethodTimer("Initializing the database");
        timer.start();
        repository = new AppDatabaseRepository(context);
        timer.stop();
        timer.showResults();
      } else {
        Log.e(TAG , "::init() called but db already initialized");
      }
      return null;
    });
  }

  @Override
  public void create(Customer[] customers, Product[] products, Order[] orders, OrderProduct[] orderProducts) {
    if (isTestReady()) {
      new AsyncAddCustomer(repository).execute(customers);
      new AsyncAddProduct(repository).execute(products);
      new AsyncAddOrder(repository).execute(orders);
      new AsyncAddOrderProduct(repository).execute(orderProducts);
    } else {
      Toast.makeText(context.getApplicationContext(), "Test data not ready", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void create() {
    create(customers, products, orders, orderProducts);
  }

  @Override
  public void read() {
    new AsyncReadCustomer(repository).execute();
    new AsyncReadProduct(repository).execute();
    new AsyncReadOrders(repository).execute();
    new AsyncReadOrderProduct(repository).execute();
    Executors.newSingleThreadExecutor().submit(()->{
      List<Order> orders = repository.ordersByCustomer(10);
      for (Order order : orders) {
        Log.d(TAG, "read() called " + order);
      }
      return null;
    });
  }

  @Override
  public void update() {
    new ExecutorCompletionService<Void>(executor).submit(() -> {
      final long customerCount = repository.customerCount();
      final MethodTimer timer = new MethodTimer("Updating " + customerCount + " Customers");
      timer.start();
      repository.insertCustomer(customers);
      timer.stop();
      timer.showResults();
      return null;
    });

  }

  @Override
  public void delete() {
    new ExecutorCompletionService<Void>(executor).submit(() -> {
      final int numberOfCustomers = (int) repository.customerCount();

      final MethodTimer timer = new MethodTimer("Deleting all " + numberOfCustomers);
      timer.start();
      repository.deleteAllCustomers();
      timer.stop();
      timer.showResults();

      return null;
    });

  }

  @Override
  public void deleteAll() {
    new AsyncDeleteAllEntries(repository).execute();
  }

  @Override
  public void destroy() {
    repository.clear();
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

  private boolean isTestReady() {
    return (customers.length > 0 && products.length > 0 && orders.length > 0 && orderProducts.length > 0);
  }
}
