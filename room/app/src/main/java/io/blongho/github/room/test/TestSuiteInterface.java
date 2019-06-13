package io.blongho.github.room.test;

import io.blongho.github.room.model.Customer;
import io.blongho.github.room.model.Order;
import io.blongho.github.room.model.OrderProduct;
import io.blongho.github.room.model.Product;

interface TestSuiteInterface {
  void init();

  void create(Customer[] customers, Product[] products, Order[] orders, OrderProduct[] orderProducts);

  void create();

  void read();

  void update();

  void delete();

  void deleteAll();

  void destroy();
}