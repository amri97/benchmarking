package io.blongho.github.sqlite.databaseOperations;

import io.blongho.github.sqlite.database.DatabaseManager;
import io.blongho.github.sqlite.model.OrderProduct;
import io.blongho.github.sqlite.util.MethodTimer;

public class InsertOrderProduct extends DbOperationAbstraction<OrderProduct> {
  public InsertOrderProduct(DatabaseManager databaseManger, OrderProduct[] items) {
    super(databaseManger, items);
    doWork();
  }

  @Override
  protected void doWork() {
    final MethodTimer timer = new MethodTimer("Inserting " + items.length + " OrderProducts");
    timer.start();
    databaseManger.addOrderProduct(items);
    timer.stop();
    timer.showResults();
  }
}
