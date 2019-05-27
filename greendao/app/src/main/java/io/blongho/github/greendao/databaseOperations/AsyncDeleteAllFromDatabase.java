package io.blongho.github.greendao.databaseOperations;

import android.os.AsyncTask;

import io.blongho.github.greendao.model.DaoSession;
import io.blongho.github.greendao.util.MethodTimer;

public class AsyncDeleteAllFromDatabase extends AsyncTask<Void, Void, Void> {
  private static final String TAG = "AsyncDeleteAllFromDatabase";
  private final DaoSession daoSession;
  private MethodTimer timer = new MethodTimer(TAG);

  public AsyncDeleteAllFromDatabase(DaoSession daoSession) {
    this.daoSession = daoSession;
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
    timer.setTag("Dropping all customers");
    timer.start();
    daoSession.getCustomerDao().deleteAll();
    timer.stop();
    timer.setTag("Dropping all products");
    timer.start();
    daoSession.getProductDao().deleteAll();
    timer.stop();
    timer.setTag("Dropping all orders");
    timer.start();
    daoSession.getOrderDao().deleteAll();
    timer.stop();
    timer.setTag("Dropping all order products");
    timer.start();
    daoSession.getOrderProductDao().deleteAll();
    timer.stop();
    timer.showResults();
    return null;
  }
}
