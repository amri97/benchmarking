package io.blongho.github.greendao.databaseOperations;

import io.blongho.github.greendao.model.DaoSession;
import io.blongho.github.greendao.model.OrderProduct;
import io.blongho.github.greendao.util.MethodTimer;

public final class WriteOrderProducts extends DatabaseOperationAbstraction<OrderProduct> {
  public WriteOrderProducts(DaoSession daosession, MethodTimer timer, OrderProduct[] items) {
    super(daosession, timer, items);
    doWork();
  }

  @Override
 void doWork() {
    timer.setTag("Loading the database with " + items.length + " OrderProducts");
    timer.start();
    daosession.getOrderProductDao().insertOrReplaceInTx(items);
    timer.stop();
    timer.showResults();
  }
}
