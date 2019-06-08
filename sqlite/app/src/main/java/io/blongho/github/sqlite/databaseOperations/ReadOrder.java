package io.blongho.github.sqlite.databaseOperations;

import android.util.Log;

import io.blongho.github.sqlite.database.DatabaseManager;
import io.blongho.github.sqlite.util.MethodTimer;

public class ReadOrder extends DbOperationAbstraction<Void> {
  private static final String TAG = "ReadOrder";

  ReadOrder(DatabaseManager databaseManger, Void[] items) {
    super(databaseManger, items);
    doWork();
  }

  @Override
  protected void doWork() {
    final long orders = databaseManger.orderCount();
    if (orders == 0) {
      Log.e(TAG, "doWork() called with [" + orders + "] Orders");
      return;
    }
    final MethodTimer timer = new MethodTimer("Reading " + orders + " Orders");
    timer.start();
    databaseManger.getAllOrders();
    timer.stop();
    timer.showResults();
  }
}
