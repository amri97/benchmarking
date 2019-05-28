package io.blongho.github.room.test;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;

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
      if (repository == null) {
        MethodTimer.FILE_NAME = "room_10_000.json";
        final MethodTimer timer = new MethodTimer("Initializing the database");
        timer.start();
        repository = new AppDatabaseRepository(context);
        timer.stop();
        timer.showResults();
      } else {
        Toast.makeText(context.getApplicationContext(), TAG + "::init() called but db already initialized",
            Toast.LENGTH_SHORT).show();
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
  }

  @Override
  public void update() {
    new ExecutorCompletionService<Void>(executor).submit(() -> {
      // Update 5 random customers
      final int numberOfCustomers = (int) repository.customerCount();
      for (int i = 1; i <= 5; i++) {
        final long randomCustomer = getRandomNumberInRange(i, numberOfCustomers);

        final Customer bernard = new Customer(randomCustomer, "Bernard Longho", "City");
        final MethodTimer timer = new MethodTimer("Update customer number " + randomCustomer);
        timer.start();
        repository.updateCustomer(bernard);
        timer.stop();
        timer.showResults();
      }
      return null;
    });

  }

  @Override
  public void delete() {
    new ExecutorCompletionService<Void>(executor).submit(() -> {
      final int numberOfCustomers = (int) repository.customerCount();
      for (int i = 1; i <= 5; i++) {
        final int randomCustomer = getRandomNumberInRange(1, numberOfCustomers);
        final MethodTimer timer = new MethodTimer("Deleting customer number " + randomCustomer);
        timer.start();
        repository.deleteCustomerWithId((long) randomCustomer);
        timer.stop();
        timer.showResults();
      }
      return null;
    });

  }

  @Override
  public void deleteAll() {
    new AsyncDeleteAllEntries(repository).execute();
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
    submitFileReadingRequest(productService, R.raw.products10000);
    submitFileReadingRequest(customerService, R.raw.customers10000);
    submitFileReadingRequest(orderService, R.raw.order10000);
    submitFileReadingRequest(orderProductService, R.raw.order_products10000);
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
