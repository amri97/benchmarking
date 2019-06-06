package io.blongho.github.greendao.databaseOperations;

import androidx.annotation.Nullable;
import io.blongho.github.greendao.model.DaoSession;
import io.blongho.github.greendao.util.MethodTimer;

public class ReadCustomer extends DbOperationAbstraction<Void> {

  public ReadCustomer(DaoSession daosession, MethodTimer timer, @Nullable Void[] items) {
    super(daosession, timer, items);
    doWork();
  }

  @Override
  void doWork() {
    timer.setTag("Reading " + daosession.getCustomerDao().count() + " Customers");
    timer.start();
    daosession.getCustomerDao().loadAll();
    timer.stop();
    timer.showResults();
  }
}
