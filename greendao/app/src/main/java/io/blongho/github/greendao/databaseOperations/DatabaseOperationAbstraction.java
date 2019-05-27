package io.blongho.github.greendao.databaseOperations;

import io.blongho.github.greendao.model.DaoSession;
import io.blongho.github.greendao.util.MethodTimer;

abstract class DatabaseOperationAbstraction<T> {
  final DaoSession daosession;
  final MethodTimer timer;
  final T[] items;

  DatabaseOperationAbstraction(DaoSession daosession, MethodTimer timer, T[] items) {
    this.daosession = daosession;
    this.timer = timer;
    this.items = items;
  }

  abstract void doWork();

}
