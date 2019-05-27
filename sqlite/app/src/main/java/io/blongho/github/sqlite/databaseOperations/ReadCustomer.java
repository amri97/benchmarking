package io.blongho.github.sqlite.databaseOperations;

import android.util.Log;

import io.blongho.github.sqlite.database.DatabaseManager;
import io.blongho.github.sqlite.util.MethodTimer;

public class ReadCustomer extends DbOperationAbstraction<Void> {
  private static final String TAG = "ReadCustomer";

  ReadCustomer(DatabaseManager databaseManger, Void[] items) {
    super(databaseManger, items);
    doWork();
  }

  @Override
  protected void doWork() {
    final long customers = databaseManger.customerCount();
    if (customers == 0) {
      Log.e(TAG, "doWork() called with [" + customers + "] Customers");
      return;
    }
    final MethodTimer timer = new MethodTimer("Reading " + customers + " Customers");
    timer.start();
    databaseManger.getAllCustomers();
    timer.stop();
    timer.showResults();
  }
}
