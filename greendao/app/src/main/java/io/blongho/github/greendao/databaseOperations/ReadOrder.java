package io.blongho.github.greendao.databaseOperations;

import androidx.annotation.Nullable;
import io.blongho.github.greendao.model.DaoSession;
import io.blongho.github.greendao.util.MethodTimer;

public class ReadOrder extends DbOperationAbstraction<Void> {

  public ReadOrder(DaoSession daosession, MethodTimer timer, @Nullable Void[] items) {
    super(daosession, timer, items);
    doWork();
  }

  @Override
  void doWork() {
    timer.setTag("Reading " + daosession.getOrderDao().count() + " Orders");
    timer.start();
    daosession.getOrderDao().loadAll();
    timer.stop();
    timer.showResults();
  }
}
