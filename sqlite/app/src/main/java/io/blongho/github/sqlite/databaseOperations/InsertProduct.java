package io.blongho.github.sqlite.databaseOperations;

import io.blongho.github.sqlite.database.DatabaseManager;
import io.blongho.github.sqlite.model.Product;
import io.blongho.github.sqlite.util.MethodTimer;

public class InsertProduct extends DbOperationAbstraction<Product> {
  public InsertProduct(DatabaseManager databaseManger, Product[] items) {
    super(databaseManger, items);
    doWork();
  }

  @Override
  protected void doWork() {
    final MethodTimer timer = new MethodTimer("Inserting " + items.length + " Products");
    timer.start();
    databaseManger.addProduct(items);
    timer.stop();
    timer.showResults();
  }
}
