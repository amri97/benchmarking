package io.blongho.github.greendao.databaseOperations;

import androidx.annotation.Nullable;
import io.blongho.github.greendao.model.DaoSession;
import io.blongho.github.greendao.util.MethodTimer;

public class ReadProducts extends DbOperationAbstraction<Void> {
  public ReadProducts(DaoSession daosession, MethodTimer timer, @Nullable Void[] items) {
    super(daosession, timer, items);
    doWork();
  }

  @Override
  void doWork() {
    timer.setTag("Reading " + daosession.getProductDao().count() + " Products");
    timer.start();
    daosession.getProductDao().loadAll();
    timer.stop();
    timer.showResults();
  }
}
