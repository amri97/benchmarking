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

package io.blongho.github.sqlite.database;

import java.util.List;

import io.blongho.github.sqlite.model.Customer;
import io.blongho.github.sqlite.model.Order;
import io.blongho.github.sqlite.model.OrderProduct;
import io.blongho.github.sqlite.model.Product;

/**
 * The interface Database operations.
 */
public interface DatabaseOperations {
  /**
   * Add customer.
   *
   * @param customer the customer
   */
// Customer operations
  void addCustomer(final Customer... customer);

  /**
   * Delete customer.
   *
   * @param customer the customer
   */
  void deleteCustomer(final Customer... customer);

  /**
   * Delete customer with name.
   *
   * @param customerName the customer name
   */
  void deleteCustomerWithName(final String customerName);

  /**
   * Delete customer with id.
   *
   * @param customerID the customer id
   */
  void deleteCustomerWithId(final long customerID);

  /**
   * Update customer.
   *
   * @param customer the customer
   */
  void updateCustomer(final Customer... customer);

  /**
   * Gets all customers.
   *
   * @return the all customers
   */
  List<Customer> getAllCustomers();

  /**
   * Add product.
   *
   * @param products the products
   */
// Product operations
  void addProduct(final Product... products);

  /**
   * Delete product.
   *
   * @param products the products
   */
  void deleteProduct(final Product... products);

  /**
   * Delete product with name.
   *
   * @param productName the product name
   */
  void deleteProductWithName(final String productName);

  /**
   * Delete product with id.
   *
   * @param productID the product id
   */
  void deleteProductWithId(final long productID);

  /**
   * Update product.
   *
   * @param products the products
   */
  void updateProduct(final Product... products);

  /**
   * Gets all products.
   *
   * @return the all products
   */
  List<Product> getAllProducts();

  /**
   * Add order.
   *
   * @param orders the orders
   */
// Order operations
  void addOrder(final Order... orders);

  /**
   * Delete order.
   *
   * @param orders the orders
   */
  void deleteOrder(final Order... orders);

  /**
   * Delete order with id.
   *
   * @param orderID the order id
   */
  void deleteOrderWithId(final long orderID);

  /**
   * Gets all orders.
   *
   * @return the all orders
   */
  List<Order> getAllOrders();

  // update orderProduct table

  /**
   * Delete all.
   *
   * @param tables the tables
   */
  void deleteAll(final String... tables);

  /**
   * Delete order product.
   *
   * @param orderProducts the order products
   */
  void deleteOrderProduct(final OrderProduct... orderProducts);

  /**
   * Add order product.
   *
   * @param orderProducts the order products
   */
  void addOrderProduct(final OrderProduct... orderProducts);

  /**
   * Gets all order products.
   *
   * @return the all order products
   */
  List<OrderProduct> getAllOrderProducts();

  /**
   * Customer count long.
   *
   * @return the long
   */
  long customerCount();

  /**
   * Product count long.
   *
   * @return the long
   */
  long productCount();

  /**
   * Order count long.
   *
   * @return the long
   */
  long orderCount();

  /**
   * Order product count long.
   *
   * @return the long
   */
  long orderProductCount();

  void deleteAllCustomers();

  void deleteAllProducts();

  void deleteAllOrders();

  void deleteAllOrderProducts();
}
