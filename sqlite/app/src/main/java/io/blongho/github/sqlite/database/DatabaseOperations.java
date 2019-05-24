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

public interface DatabaseOperations {
  // Customer operations
  void addCustomer(final Customer... customer);

  void deleteCustomer(final Customer... customer);

  void deleteCustomerWithName(final String customerName);

  void deleteCustomerWithId(final long customerID);

  void updateCustomer(final Customer... customer);

  List<Customer> getAllCustomers();

  // Product operations
  void addProduct(final Product... products);

  void deleteProduct(final Product... products);

  void deleteProductWithName(final String productName);

  void deleteProductWithId(final long productID);

  void updateProduct(final Product... products);

  List<Product> getAllProducts();

  // Order operations
  void addOrder(final Order... orders);

  void deleteOrder(final Order... orders);

  void deleteOrderWithId(final long orderID);

  List<Order> getAllOrders();

  // update orderProduct table

  void deleteAll(final String... tables);

  void deleteOrderProduct(final OrderProduct... orderProducts);

  void addOrderProduct(final OrderProduct... orderProducts);

  List<OrderProduct> getAllOrderProducts();
}
