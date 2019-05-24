package io.blongho.github.sqlite.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import io.blongho.github.sqlite.database.DatabaseManager;
import io.blongho.github.sqlite.model.Customer;
import io.blongho.github.sqlite.model.Order;
import io.blongho.github.sqlite.model.OrderProduct;
import io.blongho.github.sqlite.model.Product;
import io.blongho.github.sqlite.util.MethodTimer;

public class AsyncRead<T> extends AsyncTask<Void, Void, Void> {
  private static final String TAG = "AsyncRead";
  private static final MethodTimer timer = new MethodTimer(TAG);
  private final Class<T> objectClass;
  private final DatabaseManager dbManager;

  public AsyncRead(DatabaseManager dbManager, Class<T> objectClass) {
    this.dbManager = dbManager;
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
      timer.addTag("Reading customers from db");
      timer.start();
      dbManager.getAllCustomers();
      timer.stop();
    } else if (objectClass.isInstance(new Product())) {
      timer.addTag("Reading products from db");
      timer.start();
      dbManager.getAllProducts();
      timer.stop();
    } else if (objectClass.isInstance(new Order())) {
      timer.addTag("Reading orders from db");
      timer.start();
      dbManager.getAllOrders();
      timer.stop();
    } else if (objectClass.isInstance(new OrderProduct())) {
      timer.addTag("Reading OrderProducts from db");
      timer.start();
      dbManager.getAllOrderProducts();
      timer.stop();
    }
    timer.showResults();
    return null;
  }

  @Override
  protected void onPostExecute(Void aVoid) {
    super.onPostExecute(aVoid);
    Log.d(TAG, "onPostExecute() called with: aVoid = [" + aVoid + "]");
  }
}
