package io.blongho.github.sqlite.databaseOperations;

import io.blongho.github.sqlite.database.DatabaseManager;

abstract class DbOperationAbstraction<T> {
  final DatabaseManager databaseManger;
  final T[] items;

  DbOperationAbstraction(DatabaseManager databaseManger, T[] items) {
    this.databaseManger = databaseManger;
    this.items = items;
  }

  abstract protected void doWork();
}
