package io.blongho.github.greendao.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;

import androidx.annotation.RawRes;
import io.blongho.github.greendao.R;
import io.blongho.github.greendao.database.DatabaseHelper;
import io.blongho.github.greendao.databaseOperations.AsyncDeleteAllFromDatabase;
import io.blongho.github.greendao.databaseOperations.AsyncReadFromDatabase;
import io.blongho.github.greendao.databaseOperations.WriteCustomers;
import io.blongho.github.greendao.databaseOperations.WriteOrderProducts;
import io.blongho.github.greendao.databaseOperations.WriteOrders;
import io.blongho.github.greendao.databaseOperations.WriteProducts;
import io.blongho.github.greendao.model.Customer;
import io.blongho.github.greendao.model.DaoMaster;
import io.blongho.github.greendao.model.DaoSession;
import io.blongho.github.greendao.model.Order;
import io.blongho.github.greendao.model.OrderProduct;
import io.blongho.github.greendao.model.Product;

public class Test implements TestSuiteInterface {
  private static final String TAG = "Test";
  private static Executor executor = Executors.newCachedThreadPool();
  private static DaoSession writableDaoSession;
  private final Context context;
  private final Object writeLock = new Object();
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
  }

  @Override
  public void init() {
    new ExecutorCompletionService<Void>(executor).submit(() -> {
      final MethodTimer timer = new MethodTimer("Initializing the database");
      timer.start();
      getWritableDaoSession();
      timer.stop();
      timer.showResults();
      return null;
    });
  }

  @Override
  public void create(Customer[] customers, Product[] products, Order[] orders, OrderProduct[] orderProducts) {
    if (isTestReady()) {
      final ExecutorCompletionService<Void> createCompletion = new ExecutorCompletionService<>(executor);
      createCompletion.submit(() -> {
        new WriteCustomers(writableDaoSession, new MethodTimer("Create customers", context), customers);
        new WriteProducts(writableDaoSession, new MethodTimer("Create product", context), products);
        new WriteOrders(writableDaoSession, new MethodTimer("Create orders", context), orders);
        new WriteOrderProducts(writableDaoSession, new MethodTimer("Create OrderProducts", context), orderProducts);
      }, null);
    }
  }

  @Override
  public void create() {
    create(customers, products, orders, orderProducts);
  }

  @Override
  public void read() {
    final List<Order> orders = writableDaoSession.getOrderDao()._queryCustomer_Orders(9001);
    for (Order order : orders) {
      Log.d(TAG, "read() returned: " + order);
    }

    new AsyncReadFromDatabase<>(writableDaoSession, Customer.class).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    new AsyncReadFromDatabase<>(writableDaoSession, Product.class).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    new AsyncReadFromDatabase<>(writableDaoSession, Order.class).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    new AsyncReadFromDatabase<>(writableDaoSession, OrderProduct.class).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
  }

  @Override
  public void update() {
    throw new UnsupportedOperationException("Method is not yet implemented");
  }

  @Override
  public void delete() {
    throw new UnsupportedOperationException("Delete Method is not yet implemented");
  }

  @Override
  public void deleteAll() {
    Void avoid = null;
    new AsyncDeleteAllFromDatabase(writableDaoSession).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, avoid);
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
   *
   * @return {#@writableDaoSession}
   */
  private DaoSession getWritableDaoSession() {
    synchronized (writeLock) {
      if (writableDaoSession == null) {
        final String dbName = "customer_order_greendao";
        writableDaoSession =
            new DaoMaster(new DatabaseHelper(context, dbName).getWritableDb()).newSession();
      }
    }
    return writableDaoSession;
  }

  /**
   * Read all the data from file
   * <p>Call this method before running Create()</p>
   */
  private void getData() {
    initCompletionServices();
    submitFileReadingRequest(productService, R.raw.products10000);
    submitFileReadingRequest(customerService, R.raw.customers10000);
    submitFileReadingRequest(orderService, R.raw.orders10000);
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
