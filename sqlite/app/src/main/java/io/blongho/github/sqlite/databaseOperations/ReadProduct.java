package io.blongho.github.sqlite.databaseOperations;

import android.util.Log;

import io.blongho.github.sqlite.database.DatabaseManager;
import io.blongho.github.sqlite.util.MethodTimer;

public class ReadProduct extends DbOperationAbstraction<Void> {
  private static final String TAG = "ReadProduct";

  ReadProduct(DatabaseManager databaseManger, Void[] items) {
    super(databaseManger, items);
    doWork();
  }

  @Override
  protected void doWork() {
    final long products = databaseManger.productCount();
    if (products == 0) {
      Log.e(TAG, "doWork() called with [" + products + "] Products");
      return;
    }
    final MethodTimer timer = new MethodTimer("Reading " + products + " Products");
    timer.start();
    databaseManger.getAllProducts();
    timer.stop();
    timer.showResults();
  }
}
