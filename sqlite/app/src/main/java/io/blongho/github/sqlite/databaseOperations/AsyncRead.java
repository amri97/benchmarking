package io.blongho.github.sqlite.databaseOperations;

import android.os.AsyncTask;

import io.blongho.github.sqlite.database.DatabaseManager;

public class AsyncRead extends AsyncTask<Void, Void, Void> {
  private final DatabaseManager dbManager;

  public AsyncRead(DatabaseManager dbManager) {
    this.dbManager = dbManager;
  }

  @Override
  protected Void doInBackground(Void... voids) {
    new ReadCustomer(dbManager, null);
    new ReadProduct(dbManager, null);
    new ReadOrder(dbManager, null);
    new ReadOrderProducts(dbManager, null);
    return null;
  }
}
