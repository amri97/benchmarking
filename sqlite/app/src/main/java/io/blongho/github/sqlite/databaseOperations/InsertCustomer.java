package io.blongho.github.sqlite.databaseOperations;

import io.blongho.github.sqlite.database.DatabaseManager;
import io.blongho.github.sqlite.model.Customer;
import io.blongho.github.sqlite.util.MethodTimer;

public class InsertCustomer extends DbOperationAbstraction<Customer> {

  public InsertCustomer(DatabaseManager databaseManger, Customer[] items) {
    super(databaseManger, items);
    doWork();
  }

  @Override
  protected void doWork() {
    final MethodTimer timer = new MethodTimer("Inserting " + items.length + " Customers");
    timer.start();
    databaseManger.addCustomer(items);
    timer.stop();
    timer.showResults();
  }
}
