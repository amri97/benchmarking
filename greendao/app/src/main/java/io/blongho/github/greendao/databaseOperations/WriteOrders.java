package io.blongho.github.greendao.databaseOperations;

import io.blongho.github.greendao.model.DaoSession;
import io.blongho.github.greendao.model.Order;
import io.blongho.github.greendao.util.MethodTimer;

public final class WriteOrders extends DatabaseOperationAbstraction<Order> {
  public WriteOrders(DaoSession daosession, MethodTimer timer, Order[] items) {
    super(daosession, timer, items);
    doWork();
  }

  @Override
  void doWork() {
    timer.setTag("Loading the database with " + items.length + " orders");
    timer.start();
    daosession.getOrderDao().insertOrReplaceInTx(items);
    timer.stop();
    timer.showResults();
  }
}
