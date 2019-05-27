package io.blongho.github.greendao.databaseOperations;

import io.blongho.github.greendao.model.Customer;
import io.blongho.github.greendao.model.DaoSession;
import io.blongho.github.greendao.util.MethodTimer;

public final class WriteCustomers extends DatabaseOperationAbstraction<Customer> {

  public WriteCustomers(DaoSession daosession, MethodTimer timer, Customer[] items) {
    super(daosession, timer, items);
    doWork();
  }

  @Override
  void doWork() {
    timer.setTag("Loading the database with " + items.length + " customers");
    timer.start();
    daosession.getCustomerDao().insertOrReplaceInTx(items);
    timer.stop();
    timer.showResults();
  }
}