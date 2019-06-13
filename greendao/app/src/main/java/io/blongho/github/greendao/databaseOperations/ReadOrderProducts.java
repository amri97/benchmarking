package io.blongho.github.greendao.databaseOperations;

import androidx.annotation.Nullable;
import io.blongho.github.greendao.model.DaoSession;
import io.blongho.github.greendao.util.MethodTimer;

public class ReadOrderProducts extends DbOperationAbstraction<Void> {
  public ReadOrderProducts(DaoSession daosession, MethodTimer timer, @Nullable Void[] items) {
    super(daosession, timer, items);
    doWork();
  }

  @Override
  void doWork() {
    timer.setTag("Reading " + daosession.getOrderProductDao().count() + " OrderProducts");
    timer.start();
    daosession.getOrderProductDao().loadAll();
    timer.stop();
    timer.showResults();
  }
}
