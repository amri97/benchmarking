package io.blongho.github.sqlite.AsyncTasks;

import android.os.AsyncTask;

import io.blongho.github.sqlite.database.DatabaseManager;
import io.blongho.github.sqlite.util.MethodTimer;

public class AsyncDeleteAll extends AsyncTask<Void, Void, Void> {
  private final DatabaseManager manager;

  public AsyncDeleteAll(DatabaseManager manager) {
    this.manager = manager;
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
    final MethodTimer timer = new MethodTimer("Deleting all data from the db");
    timer.start();
    manager.deleteAll(DatabaseManager.TB_CUSTOMER, DatabaseManager.TB_PRODUCT, DatabaseManager.TB_ORDER, DatabaseManager.TB_ORDER_PRODUCT);
    timer.stop();
    timer.showResults();
    return null;
  }
}
