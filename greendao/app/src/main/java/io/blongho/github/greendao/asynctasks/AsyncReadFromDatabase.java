package io.blongho.github.greendao.asynctasks;

import android.os.AsyncTask;

import io.blongho.github.greendao.model.Customer;
import io.blongho.github.greendao.model.DaoSession;
import io.blongho.github.greendao.model.Order;
import io.blongho.github.greendao.model.OrderProduct;
import io.blongho.github.greendao.model.Product;
import io.blongho.github.greendao.util.MethodTimer;

/**
 * The type Async read from database.
 *
 * @param <T> the type parameter
 */
public class AsyncReadFromDatabase<T> extends AsyncTask<Void, Void, Void> {
  private static final String TAG = "AsyncReadFromDatabase";
  private static MethodTimer timer = new MethodTimer(TAG);
  private final DaoSession daoSession;
  private final Class<T> objectClass;

  /**
   * Instantiates a new Async read from database.
   *
   * @param daoSession  the dao session
   * @param objectClass the object class
   */
  public AsyncReadFromDatabase(DaoSession daoSession, Class<T> objectClass) {
    this.daoSession = daoSession;
    this.objectClass = objectClass;
  }

  /**
   * Override this method to perform a computation on a background thread. The
   * specified parameters are the parameters passed to {@link #execute}
   * by the caller of this task.
   * <p>
   * This method can call {@link #publishProgress} to publish updates
   * on the UI thread.
   *
   * @param voids The parameters of the task.
   * @return A result, defined by the subclass of this task.
   * @see #onPreExecute()
   * @see #onPostExecute
   * @see #publishProgress
   */
  @Override
  protected Void doInBackground(Void... voids) {
    if (objectClass.isInstance(new Customer())) {
      timer.addTag("Reading all customers from database");
      timer.start();
      daoSession.getCustomerDao().loadAll();
      timer.stop();
    } else if (objectClass.isInstance(new Product())) {
      timer.addTag("Reading all products form the database");
      timer.start();
      daoSession.getProductDao().loadAll();
      timer.stop();
    } else if (objectClass.isInstance(new Order())) {
      timer.addTag("Reading all orders from the database");
      timer.start();
      daoSession.getOrderDao().loadAll();
      timer.stop();
    } else if (objectClass.isInstance(new OrderProduct())) {
      timer.addTag("Reading all order products from database");
      timer.start();
      daoSession.getOrderProductDao().loadAll();
      timer.stop();
    }
    timer.showResults();
    return null;
  }
}
