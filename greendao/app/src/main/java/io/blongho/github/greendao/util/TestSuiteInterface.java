package io.blongho.github.greendao.util;

import io.blongho.github.greendao.model.Customer;
import io.blongho.github.greendao.model.Order;
import io.blongho.github.greendao.model.OrderProduct;
import io.blongho.github.greendao.model.Product;

interface TestSuiteInterface {
  void init();

  void create(Customer[] customers, Product[] products, Order[] orders, OrderProduct[] orderProducts);

  void create();

  void read();

  void update();

  void delete();

  void deleteAll();
}
