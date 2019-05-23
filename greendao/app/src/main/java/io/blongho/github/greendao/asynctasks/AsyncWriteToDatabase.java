package io.blongho.github.greendao.asynctasks;

import android.os.AsyncTask;

import io.blongho.github.greendao.model.Customer;
import io.blongho.github.greendao.model.CustomerDao;
import io.blongho.github.greendao.model.DaoSession;
import io.blongho.github.greendao.model.Order;
import io.blongho.github.greendao.model.OrderDao;
import io.blongho.github.greendao.model.OrderProduct;
import io.blongho.github.greendao.model.OrderProductDao;
import io.blongho.github.greendao.model.Product;
import io.blongho.github.greendao.model.ProductDao;
import io.blongho.github.greendao.util.MethodTimer;

public class AsyncWriteToDatabase<T> extends AsyncTask<T, Void, Void> {
  private static final String TAG = "AsyncWriteToDatabase";
  private static MethodTimer timer = new MethodTimer(TAG);
  private final DaoSession daosession;

  public AsyncWriteToDatabase(DaoSession daosession) {
    this.daosession = daosession;
  }

  /**
   * Override this method to perform a computation on a background thread. The
   * specified parameters are the parameters passed to {@link #execute}
   * by the caller of this task.
   * <p>
   * This method can call {@link #publishProgress} to publish updates
   * on the UI thread.
   *
   * @param items The parameters of the task.
   * @return A result, defined by the subclass of this task.
   * @see #onPreExecute()
   * @see #onPostExecute
   * @see #publishProgress
   */
  @Override
  protected Void doInBackground(T... items) {
    if (items.length > 0) {
      if (items[0].getClass().isInstance(new Customer())) {
        timer.addTag("Populating " + items.length + " customers");
        timer.start();
        final CustomerDao customerDao = daosession.getCustomerDao();
        for (T t : items) {
          customerDao.insertOrReplace((Customer) t);
        }
        timer.stop();
        //customerDao.deleteAll();
      } else if (items[0].getClass().isInstance(new Product())) {
        timer.addTag("Populating " + items.length + " products");
        timer.start();
        final ProductDao productDao = daosession.getProductDao();
        for (T item : items) {
          productDao.insertOrReplace((Product) (item));
        }
        timer.stop();
        //productDao.detachAll();

      } else if (items[0].getClass().isInstance(new Order())) {
        timer.addTag("Populating " + items.length + " orders");
        timer.start();
        final OrderDao orderDao = daosession.getOrderDao();
        for (T item : items) {
          orderDao.insertOrReplace((Order) item);
        }
        timer.stop();
        //orderDao.detachAll();

      } else if (items[0].getClass().isInstance(new OrderProduct())) {
        timer = new MethodTimer("Populating " + items.length + " orderProducts");
        timer.start();
        final OrderProductDao orderProductDao = daosession.getOrderProductDao();
        for (T item : items) {
          orderProductDao.insertOrReplace((OrderProduct) item);
        }
        timer.stop();
        //orderProductDao.deleteAll();
      }
      timer.showResults();
    }
    return null;
  }
}
