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

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import io.blongho.github.sqlite.constants.Column;
import io.blongho.github.sqlite.constants.Table;
import io.blongho.github.sqlite.interfaces.DatabaseOperations;
import io.blongho.github.sqlite.model.Customer;
import io.blongho.github.sqlite.model.Order;
import io.blongho.github.sqlite.model.OrderProduct;
import io.blongho.github.sqlite.model.Product;

public class DatabaseManager implements DatabaseOperations {
  private DatabaseHelper dbHelper;
  private SQLiteDatabase dbWriter = null;
  private SQLiteDatabase dbReader = null;

  public DatabaseManager(final Context context) {
    dbHelper = new DatabaseHelper(context);
  }

  @Override
  public void populateDatabase(List<Customer> customers, List<Product> products, List<Order> orders, List<OrderProduct> orderProducts) {
    dbWriter.beginTransaction();
    for (Customer customer : customers) {
      addCustomer(customer);
    }
    for (Product product : products) {
      addProduct(product);
    }
    for (Order order : orders) {
      addOrder(order);
    }
    for (OrderProduct orderProduct : orderProducts) {
      updateOrderProductTable(orderProduct.getCustomer_id(), orderProduct.getOrder_id());
    }
    dbWriter.endTransaction();
  }

  @Override
  public long addCustomer(Customer customer) {
    openWriter();
    ContentValues contentValues = new ContentValues();
    contentValues.put(Column.CUSTOMER_ID, customer.getId());
    contentValues.put(Column.CUSTOMER_NAME, customer.getName());
    contentValues.put(Column.CUSTOMER_ADDR, customer.getAddress());
    long rowid = dbWriter.insertOrThrow(Table.CUSTOMER, null, contentValues);
    closeWriter();
    return rowid;
  }

  @Override
  public long deleteCustomer(Customer customer) {
    return deleteCustomerWithId(customer.getId());
  }

  @Override
  public long deleteCustomerWithName(String customerName) {
    openWriter();
    int row = dbWriter.delete(Table.CUSTOMER, Column.CUSTOMER_NAME + "=?",
        new String[]{customerName});
    closeWriter();
    return row;
  }

  @Override
  public long deleteCustomerWithId(long customerID) {
    openWriter();
    int row = dbWriter.delete(Table.CUSTOMER, Column.CUSTOMER_ID + "=?",
        new String[]{String.valueOf(customerID)});
    closeWriter();
    return row;
  }

  @Override
  public long deleteAllCustomers() {
    openWriter();
    dbWriter.execSQL("DELETE FROM " + Table.CUSTOMER);
    closeWriter();
    return 0;
  }

  @Override
  public long updateCustomer(long customerID) {
    //TODO update customer record
    return 0;
  }

  @Override
  public List<Customer> getAllCustomers() {
    //TODO list all customers in the database
    return null;
  }

  @Override
  public long addProduct(Product product) {
    openWriter();
    ContentValues contentValues = new ContentValues();
    contentValues.put(Column.PRODUCT_ID, product.getId());
    contentValues.put(Column.PRODUCT_NAME, product.getName());
    contentValues.put(Column.PRODUCT_NAME, product.getDescription());
    final long rowid = dbWriter.insertOrThrow(Table.PRODUCT, null, contentValues);
    closeWriter();
    return rowid;
  }

  @Override
  public long deleteProduct(Product product) {
    //TODO delete a product
    return 0;
  }

  @Override
  public long deleteProductWithName(String productName) {
    //TODO delete product with known name
    return 0;
  }

  @Override
  public long deleteProductWithId(long productID) {
    //TODO delete product with known id
    return 0;
  }

  @Override
  public long deleteAllProducts() {
    openWriter();
    dbWriter.execSQL("DELETE FROM " + Table.PRODUCT);
    closeWriter();
    return 0;
  }

  @Override
  public long updateProduct(long productID) {
    //TODO update a product
    return 0;
  }

  @Override
  public List<Product> getAllProducts() {
    //TODO get all products
    return null;
  }

  @Override
  public long addOrder(Order order) {
    openWriter();
    ContentValues contentValues = new ContentValues();
    contentValues.put(Column.ORDER_ID, order.getId());
    contentValues.put(Column.ORDER_CUSTOMER, order.getCustomer_id());
    contentValues.put(Column.ORDER_DATE, order.getDate().toString());
    final long rowid = dbWriter.insertOrThrow(Table.ORDER, null, contentValues);
    closeWriter();
    return rowid;
  }

  @Override
  public long deleteOrder(Order order) {
    //TODO delete an order
    return 0;
  }

  @Override
  public long deleteOrderWithId(long orderID) {
    //TODO delete order with id
    return 0;
  }

  @Override
  public long deleteAllOrders() {
    openWriter();
    dbWriter.execSQL("DELETE FROM " + Table.ORDER);
    closeWriter();
    return 0;
  }

  @Override
  public List<Order> getAllOrders() {
    //TODO get all orders
    return null;
  }

  @Override
  public long updateOrderProductTable(long customerID, long orderID) {
    //TODO update OrderProduct
    return 0;
  }

  private synchronized void openWriter() {
    if (dbWriter == null)
      dbWriter = dbHelper.getWritableDatabase();
  }

  private synchronized void closeWriter() {
    if (dbWriter != null)
      dbWriter.close();
  }

  private synchronized void openReader() {
    if (dbReader == null)
      dbReader = dbHelper.getReadableDatabase();
  }

  private synchronized void closeReader() {
    if (dbReader != null) {
      dbReader.close();
    }
  }
}
