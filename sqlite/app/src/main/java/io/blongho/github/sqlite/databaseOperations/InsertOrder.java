package io.blongho.github.sqlite.databaseOperations;

import io.blongho.github.sqlite.database.DatabaseManager;
import io.blongho.github.sqlite.model.Order;
import io.blongho.github.sqlite.util.MethodTimer;

public class InsertOrder extends DbOperationAbstraction<Order> {
  public InsertOrder(DatabaseManager databaseManger, Order[] items) {
    super(databaseManger, items);
    doWork();
  }

  @Override
  protected void doWork() {
    final MethodTimer timer = new MethodTimer("Inserting " + items.length + " Orders");
    timer.start();
    databaseManger.addOrder(items);
    timer.stop();
    timer.showResults();
  }
}
