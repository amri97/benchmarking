package io.blongho.github.greendao.databaseOperations;

import io.blongho.github.greendao.model.DaoSession;
import io.blongho.github.greendao.model.Product;
import io.blongho.github.greendao.util.MethodTimer;

public final class WriteProducts extends DatabaseOperationAbstraction<Product> {
  public WriteProducts(DaoSession daosession, MethodTimer timer, Product[] items) {
    super(daosession, timer, items);
    doWork();
  }

  @Override
  void doWork() {
    timer.setTag("Loading the database with " + items.length + " products");
    timer.start();
    daosession.getProductDao().insertOrReplaceInTx(items);
    timer.stop();
    timer.showResults();
  }
}
