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

package io.blongho.github.sqlite.interfaces;

import java.util.List;

import io.blongho.github.sqlite.model.Customer;
import io.blongho.github.sqlite.model.Order;
import io.blongho.github.sqlite.model.OrderProduct;
import io.blongho.github.sqlite.model.Product;

/**
 * Defines the operations done on the database
 */
public interface DatabaseOperations {
  /**
   * Populate datbase.
   *
   * @param customers     the customers
   * @param products      the products
   * @param orders        the orders
   * @param orderProducts the order products
   */
  void populateDatabase(List<Customer> customers, List<Product> products, List<Order> orders,
                        List<OrderProduct> orderProducts);

  /**
   * Add customer long.
   *
   * @param customer the customer
   * @return the long
   */
// Customer operations
  long addCustomer(final Customer customer);

  /**
   * Delete customer long.
   *
   * @param customer the customer
   * @return the long
   */
  long deleteCustomer(final Customer customer);

  /**
   * Delete customer with name long.
   *
   * @param customerName the customer name
   * @return the long
   */
  long deleteCustomerWithName(final String customerName);

  /**
   * Delete customer with id long.
   *
   * @param customerID the customer id
   * @return the long
   */
  long deleteCustomerWithId(final long customerID);

  /**
   * Delete all customers long.
   *
   * @return the long
   */
  long deleteAllCustomers();

  /**
   * Update customer long.
   *
   * @param customerID the customer id
   * @return the long
   */
  long updateCustomer(final long customerID);

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
   * @return the long
   */
// Product operations
  long addProduct(final Product product);

  /**
   * Delete product long.
   *
   * @param product the product
   * @return the long
   */
  long deleteProduct(final Product product);

  /**
   * Delete product with name long.
   *
   * @param productName the product name
   * @return the long
   */
  long deleteProductWithName(final String productName);

  /**
   * Delete product with id long.
   *
   * @param productID the product id
   * @return the long
   */
  long deleteProductWithId(final long productID);

  /**
   * Delete all products long.
   *
   * @return the long
   */
  long deleteAllProducts();

  /**
   * Update product long.
   *
   * @param productID the product id
   * @return the long
   */
  long updateProduct(final long productID);

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
   * @return the long
   */
// Order operations
  long addOrder(final Order order);

  /**
   * Delete order long.
   *
   * @param order the order
   * @return the long
   */
  long deleteOrder(final Order order);

  /**
   * Delete order with id long.
   *
   * @param orderID the order id
   * @return the long
   */
  long deleteOrderWithId(final long orderID);

  /**
   * Delete all orders long.
   *
   * @return the long
   */
  long deleteAllOrders();

  /**
   * Gets all orders.
   *
   * @return the list of orders in the system
   */
  List<Order> getAllOrders();

  // update orderProduct table

  /**
   * Call this method whenever an order is made
   *
   * @param customerID the customer who made the order
   * @param orderID    the id of the order
   * @return the number of rows affected
   */
  long updateOrderProductTable(long customerID, long orderID);
}
