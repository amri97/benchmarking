/*
 * MIT License
 *
 * Copyright (c) 2019 Bernard Che Longho
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.blongho.github.room.database;

import android.content.Context;

import java.util.List;

import androidx.annotation.Nullable;
import io.blongho.github.room.database.dao.CustomerDao;
import io.blongho.github.room.database.dao.OrderDao;
import io.blongho.github.room.database.dao.OrderProductDao;
import io.blongho.github.room.database.dao.ProductDao;
import io.blongho.github.room.model.Customer;
import io.blongho.github.room.model.Order;
import io.blongho.github.room.model.OrderProduct;
import io.blongho.github.room.model.Product;

public class AppDatabaseRepository implements DatabaseOperations {
  private CustomerDao customerDao;
  private ProductDao productDao;
  private OrderDao orderDao;
  private OrderProductDao orderProductDao;

  private AppDatabase db;

  public AppDatabaseRepository(final Context context) {
    db = AppDatabase.getInstance(context);
    customerDao = db.customerDao();
    productDao = db.productDao();
    orderDao = db.orderDao();
    orderProductDao = db.orderProductDao();
  }

  @Override
  public void populateDatabase(
      final List<Customer> customers, final List<Product> products, final List<Order> orders,
      @Nullable final List<OrderProduct> orderProducts) {
    for (final Customer customer : customers) {
      addCustomer(customer);
    }
    for (final Product product : products) {
      addProduct(product);
    }
    for (final Order order : orders) {
      addOrder(order);
    }
    if (orderProducts != null) {
      for (final OrderProduct orderProduct : orderProducts) {
        insertOrderProduct(orderProduct);
      }
    }
  }

  @Override
  public void addCustomer(final Customer... customer) {
    customerDao.insertCustomers(customer);
  }

  @Override
  public void deleteCustomer(final Customer customer) {
    customerDao.deleteCustomer(customer);
  }

  @Override
  public void deleteCustomerWithName(final String customerName) {
    customerDao.deleteCustomerWithAttribute(customerName);
  }

  @Override
  public void deleteCustomerWithId(final long customerID) {
    customerDao.deleteCustomerWithAttribute(String.valueOf(customerID));
  }

  @Override
  public int updateCustomer(final Customer customer) {
    return customerDao.updateCustomer(customer);
  }

  @Override
  public List<Customer> getAllCustomers() {
    return customerDao.getAllCustomers();
  }

  @Override
  public void addProduct(final Product... product) {
    productDao.insertProducts(product);
  }

  @Override
  public void deleteProduct(final Product product) {
    productDao.deleteProduct(product);
  }

  @Override
  public void deleteProductWithName(final String productName) {
    productDao.deleteProductWithAttribute(productName);
  }

  @Override
  public void deleteProductWithId(final long productID) {
    productDao.deleteProductWithAttribute(String.valueOf(productID));
  }

  @Override
  public int updateProduct(final Product product) {
    return productDao.updateProduct(product);
  }

  @Override
  public List<Product> getAllProducts() {
    return productDao.getAllProducts();
  }

  @Override
  public void addOrder(final Order... order) {
    orderDao.insertOrders(order);
  }

  @Override
  public void deleteOrder(final Order order) {
    orderDao.deleteOrder(order);
  }

  @Override
  public void deleteOrderWithId(final long orderID) {
    orderDao.deleteOrderWithAttribute(String.valueOf(orderID));
  }

  @Override
  public List<Order> getAllOrders() {
    return orderDao.getAllOrders();
  }

  @Override
  public void insertOrderProduct(final OrderProduct... orderProduct) {
    orderProductDao.insertOrderProducts(orderProduct);
  }

  @Override
  public void deleteAll() {
    customerDao.deleteAllCustomers();
    productDao.deleteAllProducts();
    orderDao.deleteAllOrders();
    orderProductDao.deleteAllOrderProducts();
  }

  public List<OrderProduct> getAllOrderProducts() {
    return orderProductDao.getAllOrderProducts();
  }

  public List<Product> getProductsInOrder(final long orderID) {
    return orderDao.getProductsInOrder(orderID);
  }
}
