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

import io.blongho.github.room.database.dao.CustomerDao;
import io.blongho.github.room.database.dao.OrderDao;
import io.blongho.github.room.database.dao.OrderProductDao;
import io.blongho.github.room.database.dao.ProductDao;
import io.blongho.github.room.model.Customer;
import io.blongho.github.room.model.Order;
import io.blongho.github.room.model.OrderProduct;
import io.blongho.github.room.model.Product;

/**
 * The type App database repository.
 */
public class AppDatabaseRepository implements DatabaseOperations {
  private CustomerDao customerDao;
  private ProductDao productDao;
  private OrderDao orderDao;
  private OrderProductDao orderProductDao;

  /**
   * Instantiates a new App database repository.
   *
   * @param context the context
   */
  public AppDatabaseRepository(final Context context) {
    final AppDatabase db = AppDatabase.getInstance(context);
    customerDao = db.customerDao();
    productDao = db.productDao();
    orderDao = db.orderDao();
    orderProductDao = db.orderProductDao();
  }

  /**
   * Populate datbase.
   *
   * @param customers     the customers
   * @param products      the products
   * @param orders        the orders
   * @param orderProducts the order products
   */
  @Override
  public void populateDatabase(Customer[] customers, Product[] products, Order[] orders, OrderProduct[] orderProducts) {
    customerDao.insertCustomer(customers);
    productDao.insertProducts(products);
    orderDao.insertOrder(orders);
    orderProductDao.insertOrderProduct(orderProducts);
  }

  @Override
  public void insertCustomer(final Customer... customer) {
    customerDao.insertCustomer(customer);
  }

  @Override
  public void deleteCustomer(final Customer... customer) {
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
  public int updateCustomer(final Customer... customer) {
    return customerDao.updateCustomer(customer);
  }

  @Override
  public List<Customer> getAllCustomers() {
    return customerDao.getAllCustomers();
  }

  @Override
  public void insertProduct(final Product... product) {
    productDao.insertProducts(product);
  }

  @Override
  public void deleteProduct(final Product... product) {
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
  public int updateProduct(final Product... product) {
    return productDao.updateProduct(product);
  }

  @Override
  public List<Product> getAllProducts() {
    return productDao.getAllProducts();
  }

  @Override
  public void insertOrder(final Order... order) {
    orderDao.insertOrder(order);
  }

  @Override
  public void deleteOrder(final Order... order) {
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
    orderProductDao.insertOrderProduct(orderProduct);
  }

  @Override
  public void deleteAll() {
    customerDao.deleteAllCustomers();
    productDao.deleteAllProducts();
    orderDao.deleteAllOrders();
    orderProductDao.deleteAllOrderProducts();
  }

  @Override
  public long customerCount() {
    return customerDao.customerCount();
  }

  @Override
  public List<OrderProduct> getAllOrderProducts() {
    return orderProductDao.getAllOrderProducts();
  }

  @Override
  public List<Product> getProductsInOrder(final long orderID) {
    return orderDao.getProductsInOrder(orderID);
  }

  @Override
  public List<Order> ordersByCustomer(long customerID) {
    return customerDao.ordersByCustomer(customerID);
  }

  @Override
  public long orderProductCount() {
    return orderProductDao.orderProductCount();
  }

  @Override
  public long orderCount() {
    return orderDao.orderCount();
  }

  @Override
  public long productCount() {
    return productDao.productCount();
  }

  /**
   * Delete all customers.
   */
  @Override
  public void deleteAllCustomers() {
    customerDao.deleteAllCustomers();
  }

  /**
   * Delete all products.
   */
  @Override
  public void deleteAllProducts() {
    productDao.deleteAllProducts();
  }

  /**
   * Delete all orders.
   */
  @Override
  public void deleteAllOrders() {
    orderDao.deleteAllOrders();
  }

  /**
   * Delete all order products.
   */
  @Override
  public void deleteAllOrderProducts() {
    orderProductDao.deleteAllOrderProducts();
  }
}
