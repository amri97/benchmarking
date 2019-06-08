package io.blongho.github.sqlite.databaseOperations;

import android.os.AsyncTask;

import io.blongho.github.sqlite.database.DatabaseManager;
import io.blongho.github.sqlite.util.MethodTimer;

public class AsyncDeleteAll extends AsyncTask<Void, Void, Void> {
  private final DatabaseManager manager;
  private long customers, products, orders, orderPrdts;

  public AsyncDeleteAll(DatabaseManager manager) {
    this.manager = manager;
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
    customers = manager.customerCount();
    products = manager.productCount();
    orders = manager.orderCount();
    orderPrdts = manager.orderProductCount();
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
    final MethodTimer timer = new MethodTimer("Deleting all " + customers + " Customers");
    timer.start();
    manager.deleteAllCustomers();
    timer.stop();
    timer.showResults();

    final MethodTimer productTimer = new MethodTimer("Deleting all " + products + " Products");
    productTimer.start();
    manager.deleteAllProducts();
    productTimer.stop();
    productTimer.showResults();

    final MethodTimer orderTimer = new MethodTimer("Deleting all " + orders + " Orders");
    orderTimer.start();
    manager.deleteAllOrders();
    orderTimer.stop();
    orderTimer.showResults();

    final MethodTimer opTimer = new MethodTimer("Deleting all " + orderPrdts + " OrderProducts");
    opTimer.start();
    manager.deleteAllOrderProducts();
    opTimer.stop();
    opTimer.showResults();
    return null;
  }
}
