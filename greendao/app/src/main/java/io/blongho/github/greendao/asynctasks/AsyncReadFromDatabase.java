package io.blongho.github.greendao.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import io.blongho.github.greendao.model.Customer;
import io.blongho.github.greendao.model.DaoSession;

public class AsyncReadFromDatabase<T> extends AsyncTask<Void, Void, List<T>> {
  private static final String TAG = "AsyncReadFromDatabase";
  private final DaoSession daoSession;
  private final String type;

  public AsyncReadFromDatabase(DaoSession daoSession, String type) {
    this.daoSession = daoSession;
    this.type = type;
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
  protected List<T> doInBackground(Void... voids) {

    if (type.equalsIgnoreCase("customer")) {
      Log.i(TAG, "doInBackground: a customer");
      List<Customer> customers = daoSession.getCustomerDao().loadAll();
      return (List<T>) customers;

    }

    Log.i(TAG, "doInBackground: not a customer");
    return null;
  }
}
