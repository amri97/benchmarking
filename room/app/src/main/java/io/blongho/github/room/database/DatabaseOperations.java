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

import java.util.List;

import io.blongho.github.room.model.Customer;
import io.blongho.github.room.model.Order;
import io.blongho.github.room.model.OrderProduct;
import io.blongho.github.room.model.Product;

/**
 * Defines the operations done on the database
 */
interface DatabaseOperations {
  /**
   * Populate datbase.
   *
   * @param customers     the customers
   * @param products      the products
   * @param orders        the orders
   * @param orderProducts the order products
   */
  void populateDatabase(Customer[] customers, Product[] products, Order[] orders, OrderProduct[] orderProducts);

  /**
   * Add customer long.
   *
   * @param customer the customer
   */
// Customer operations
  void insertCustomer(final Customer... customer);

  /**
   * Delete customer long.
   *
   * @param customer the customer
   */
  void deleteCustomer(final Customer... customer);

  /**
   * Delete customer with name long.
   *
   * @param customerName the customer name
   */
  void deleteCustomerWithName(final String customerName);

  /**
   * Delete customer with id long.
   *
   * @param customerID the customer id
   */
  void deleteCustomerWithId(final long customerID);

  /**
   * Update customer long.
   *
   * @param customer the customer id
   * @return the long
   */
  int updateCustomer(final Customer... customer);

  /**
   * Gets all customers.
   *
   * @return the all customers
   */
  List<Customer> getAllCustomers();

  /**
   * Add product long.
   *
   * @param product the product
   */
// Product operations
  void insertProduct(final Product... product);

  /**
   * Delete product long.
   *
   * @param product the product
   */
  void deleteProduct(final Product... product);

  /**
   * Delete product with name long.
   *
   * @param productName the product name
   */
  void deleteProductWithName(final String productName);

  /**
   * Delete product with id long.
   *
   * @param productID the product id
   */
  void deleteProductWithId(final long productID);

  /**
   * Update product long.
   *
   * @param product the product id
   * @return the long
   */
  int updateProduct(final Product... product);

  /**
   * Gets all products.
   *
   * @return the all products
   */
  List<Product> getAllProducts();

  /**
   * Add order long.
   *
   * @param order the order
   */
// Order operations
  void insertOrder(final Order... order);

  /**
   * Delete order long.
   *
   * @param order the order
   */
  void deleteOrder(final Order... order);

  /**
   * Delete order with id long.
   *
   * @param orderID the order id
   */
  void deleteOrderWithId(final long orderID);

  /**
   * Gets all orders.
   *
   * @return the list of orders in the system
   */
  List<Order> getAllOrders();

  /**
   * Call this method whenever an order is made
   *
   * @param orderProduct the order product
   */
  void insertOrderProduct(final OrderProduct... orderProduct);

  /**
   * Delete all items from a particular table
   */
  void deleteAll();

  /**
   * Customer count long.
   *
   * @return the long
   */
  public long customerCount();

  /**
   * Gets all order products.
   *
   * @return the all order products
   */
  public List<OrderProduct> getAllOrderProducts();

  /**
   * Gets products in order.
   *
   * @param orderID the order id
   * @return the products in order
   */
  public List<Product> getProductsInOrder(final long orderID);

  /**
   * Orders by customer list.
   *
   * @param customerID the customer id
   * @return the list
   */
  public List<Order> ordersByCustomer(long customerID);

  /**
   * Order product count long.
   *
   * @return the long
   */
  long orderProductCount();

  /**
   * Order count long.
   *
   * @return the long
   */
  long orderCount();

  /**
   * Product count long.
   *
   * @return the long
   */
  long productCount();

  /**
   * Delete all customers.
   */
  void deleteAllCustomers();

  /**
   * Delete all products.
   */
  void deleteAllProducts();

  /**
   * Delete all orders.
   */
  void deleteAllOrders();

  /**
   * Delete all order products.
   */
  void deleteAllOrderProducts();
}
