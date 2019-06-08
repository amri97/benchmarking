package io.blongho.github.sqlite.test;

import io.blongho.github.sqlite.model.Customer;
import io.blongho.github.sqlite.model.Order;
import io.blongho.github.sqlite.model.OrderProduct;
import io.blongho.github.sqlite.model.Product;

interface TestSuiteInterface {
  void init();

  void  create(Customer[] customers, Product[] products, Order[] orders, OrderProduct[] orderProducts);

  void create();

  void read();

  void update();

  void delete();

  void deleteAll();
}