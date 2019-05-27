package io.blongho.github.sqlite.databaseOperations;

import android.util.Log;

import io.blongho.github.sqlite.database.DatabaseManager;
import io.blongho.github.sqlite.util.MethodTimer;

public class ReadOrderProducts extends DbOperationAbstraction<Void> {
  private static final String TAG = "ReadOrderProducts";

  ReadOrderProducts(DatabaseManager databaseManger, Void[] items) {
    super(databaseManger, items);
    doWork();
  }

  @Override
  protected void doWork() {
    final long odpts = databaseManger.orderProductCount();
    if (odpts == 0) {
      Log.e(TAG, "doWork() called with " + odpts + " OrderProducts");
      return;
    }
    final MethodTimer timer = new MethodTimer("Reading " + odpts + " OrderProducts");
    timer.start();
    databaseManger.getAllOrderProducts();
    timer.stop();
    timer.showResults();
  }
}
