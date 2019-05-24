package io.blongho.github.room.asynctasks;

import android.os.AsyncTask;
import android.util.TimingLogger;

import io.blongho.github.room.database.AppDatabaseRepository;
import io.blongho.github.room.model.Customer;
import io.blongho.github.room.model.Order;
import io.blongho.github.room.model.OrderProduct;
import io.blongho.github.room.model.Product;
import io.blongho.github.room.util.MethodTimer;

/**
 * The type Async add.
 *
 * @param <T> the type parameter
 */
public class AsyncAdd<T> extends AsyncTask<T, Void, Void> {
  private static final String TAG = "AsyncAdd";
  private final AppDatabaseRepository repository;
  private final Class<T> objectType;

  /**
   * Instantiates a new Async add.
   *
   * @param repository the repository
   * @param objectType the object type
   */
  public AsyncAdd(AppDatabaseRepository repository, Class<T> objectType) {
    this.repository = repository;
    this.objectType = objectType;
  }

  @Override
  protected Void doInBackground(T... items) {
    final int numberOfItems = items.length;
    if (objectType.isInstance(new Customer())) {
      Customer[] customers = new Customer[numberOfItems];
      for (int i = 0; i < numberOfItems; i++) {
        customers[i] = (Customer) items[i];
      }
      final MethodTimer timer = new MethodTimer("Inserting values into the database");

      timer.addTag("Adding " + numberOfItems + " customers");
      timer.start();
      repository.insertCustomer(customers);
      timer.stop();
      timer.showResults();
    } else if (objectType.isInstance(new Product())) {
      Product[] products = new Product[numberOfItems];
      for (int i = 0; i < numberOfItems; i++) {
        products[i] = (Product) items[i];
      }
      final MethodTimer timer = new MethodTimer("Inserting values into the database");

      timer.addTag("Adding " + numberOfItems + " products");
      timer.start();
      repository.insertProduct(products);
      timer.stop();
      timer.showResults();
    } else if (objectType.isInstance(new Order())) {
      Order[] orders = new Order[numberOfItems];
      for (int i = 0; i < numberOfItems; i++) {
        orders[i] = (Order) items[i];
      }
      final MethodTimer timer = new MethodTimer("Inserting values into the database");
      timer.addTag("Adding " + numberOfItems + " orders");
      timer.start();
      repository.insertOrder(orders);
      timer.stop();
      timer.showResults();
    } else if (objectType.isInstance(new OrderProduct())) {
      OrderProduct[] orderProducts = new OrderProduct[numberOfItems];
      for (int i = 0; i < items.length; i++) {
        orderProducts[i] = (OrderProduct) items[i];
      }
      final MethodTimer timer = new MethodTimer("Inserting values into the database");
      timer.addTag("Adding " + numberOfItems + " orderProducts");
      timer.start();
      repository.insertOrderProduct(orderProducts);
      timer.stop();
      timer.showResults();
    }

    return null;
  }
}