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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.blongho.github.sqlite.constants.Column;
import io.blongho.github.sqlite.constants.Table;
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
  public void populateDatabase(final List<Customer> customers, final List<Product> products, final List<Order> orders,
                               final List<OrderProduct> orderProducts) {
    dbWriter.beginTransaction();
    for (final Customer customer : customers) {
      addCustomer(customer);
    }
    for (final Product product : products) {
      addProduct(product);
    }
    for (final Order order : orders) {
      addOrder(order);
    }
    for (final OrderProduct orderProduct : orderProducts) {
      updateOrderProductTable(orderProduct.getProduct_id(), orderProduct.getOrder_id());
    }
    dbWriter.endTransaction();
  }

  @Override
  public long addCustomer(final Customer customer) {
    openWriter();
    final ContentValues contentValues = new ContentValues();
    contentValues.put(Column.CUSTOMER_ID, customer.getId());
    contentValues.put(Column.CUSTOMER_NAME, customer.getName());
    contentValues.put(Column.CUSTOMER_ADDR, customer.getAddress());
    final long rowid = dbWriter.insertOrThrow(Table.CUSTOMER, null, contentValues);
    closeWriter();
    return rowid;
  }

  @Override
  public long deleteCustomer(final Customer customer) {
    return deleteCustomerWithId(customer.getId());
  }

  @Override
  public long deleteCustomerWithName(final String customerName) {
    openWriter();
    final int row = dbWriter.delete(Table.CUSTOMER, Column.CUSTOMER_NAME + "=?",
        new String[]{customerName});
    closeWriter();
    return row;
  }

  @Override
  public long deleteCustomerWithId(final long customerID) {
    openWriter();
    final int row = dbWriter.delete(Table.CUSTOMER, Column.CUSTOMER_ID + "=?",
        new String[]{String.valueOf(customerID)});
    closeWriter();
    return row;
  }

  @Override
  public long updateCustomer(final Customer customer) {
    final ContentValues values = new ContentValues();
    values.put(Column.CUSTOMER_ID, customer.getId());
    values.put(Column.CUSTOMER_NAME, customer.getName());
    values.put(Column.CUSTOMER_ADDR, customer.getAddress());
    openWriter();
    final int row = dbWriter.update(Table.CUSTOMER, values, Column.CUSTOMER_ID + "=" + customer.getId(), null);
    closeWriter();
    return row;
  }

  @Override
  public List<Customer> getAllCustomers() {
    openReader();
    final Cursor cursor = dbReader.rawQuery("SELECT * FROM " + Table.CUSTOMER, null);
    final List<Customer> customers = new ArrayList<>();
    if (cursor.moveToFirst()) {
      while (!cursor.isAfterLast()) {
        final long id = cursor.getLong(cursor.getColumnIndex(Column.CUSTOMER_ID));
        final String name = cursor.getString(cursor.getColumnIndex(Column.CUSTOMER_NAME));
        final String address = cursor.getString(cursor.getColumnIndex(Column.CUSTOMER_ADDR));
        final Customer customer = new Customer(id, name, address);
        customers.add(customer);
        cursor.moveToNext();
      }
    }
    cursor.close();
    closeReader();
    return customers;
  }

  @Override
  public long addProduct(final Product product) {
    final ContentValues contentValues = new ContentValues();
    contentValues.put(Column.PRODUCT_ID, product.getId());
    contentValues.put(Column.PRODUCT_NAME, product.getName());
    contentValues.put(Column.PRODUCT_NAME, product.getDescription());
    openWriter();
    final long row = dbWriter.insertOrThrow(Table.PRODUCT, null, contentValues);
    closeWriter();
    return row;
  }

  @Override
  public long deleteProduct(final Product product) {
    return deleteProductWithName(product.getName());
  }

  @Override
  public long deleteProductWithName(final String productName) {
    openWriter();
    final long row = dbWriter.delete(Table.PRODUCT, Column.PRODUCT_NAME + "?=", new String[]{productName});
    closeWriter();
    return row;
  }

  @Override
  public long deleteProductWithId(final long productID) {
    openWriter();
    final long row = dbWriter.delete(Table.PRODUCT, Column.PRODUCT_ID + "?=",
        new String[]{String.valueOf(productID)});
    closeWriter();
    return row;
  }

  @Override
  public long updateProduct(final Product product) {
    final ContentValues values = new ContentValues();
    values.put(Column.PRODUCT_ID, product.getId());
    values.put(Column.PRODUCT_NAME, product.getName());
    values.put(Column.PRODUCT_DESC, product.getDescription());
    openWriter();
    final int row = dbWriter.update(Table.CUSTOMER, values, Column.CUSTOMER_ID + "=" + product.getId(), null);
    closeWriter();
    return row;
  }

  @Override
  public List<Product> getAllProducts() {
    openReader();
    final Cursor cursor = dbReader.rawQuery("SELECT * FROM " + Table.PRODUCT, null);
    final List<Product> products = new ArrayList<>();
    if (cursor.moveToFirst()) {
      while (!cursor.isAfterLast()) {
        final long id = cursor.getLong(cursor.getColumnIndex(Column.PRODUCT_ID));
        final String name = cursor.getString(cursor.getColumnIndex(Column.PRODUCT_NAME));
        final String desc = cursor.getString(cursor.getColumnIndex(Column.PRODUCT_DESC));
        final Product product = new Product(id, name, desc);
        products.add(product);
        cursor.moveToNext();
      }
    }
    cursor.close();
    closeReader();
    return products;
  }

  @Override
  public long addOrder(final Order order) {
    openWriter();
    final ContentValues contentValues = new ContentValues();
    contentValues.put(Column.ORDER_ID, order.getId());
    contentValues.put(Column.ORDER_CUSTOMER, order.getCustomer_id());
    contentValues.put(Column.ORDER_DATE, order.getDate().toString());
    final long row = dbWriter.insertOrThrow(Table.ORDER, null, contentValues);
    closeWriter();
    return row;
  }

  @Override
  public long deleteOrder(final Order order) {
    return deleteOrderWithId(order.getId());
  }

  @Override
  public long deleteOrderWithId(final long orderID) {
    openWriter();
    final long row = dbWriter.delete(Table.ORDER, Column.ORDER_ID + "?=",
        new String[]{String.valueOf(orderID)});
    closeWriter();
    return row;
  }

  @Override
  public List<Order> getAllOrders() {
    openReader();
    final Cursor cursor = dbReader.rawQuery("SELECT * FROM " + Table.ORDER, null);
    final List<Order> orders = new ArrayList<>();
    if (cursor.moveToFirst()) {
      while (!cursor.isAfterLast()) {
        final long id = cursor.getLong(cursor.getColumnIndex(Column.ORDER_ID));
        final long customer = cursor.getLong(cursor.getColumnIndex(Column.CUSTOMER_ID));
        final String dateString = cursor.getString(cursor.getColumnIndex(Column.ORDER_DATE));
        final Date date = new Date(DateFormat.getBestDateTimePattern(Locale.getDefault(), dateString));
        final Order order = new Order(id, customer, date);
        orders.add(order);
        cursor.moveToNext();
      }
    }
    cursor.close();
    closeReader();
    return orders;
  }

  @Override
  public long updateOrderProductTable(final long product, final long orderID) {
    final ContentValues values = new ContentValues();
    values.put(Column.PRODUCT_ID, product);
    values.put(Column.ORDER_ID, orderID);
    openWriter();
    final long row = dbWriter.insertOrThrow(Table.ORDER_PRODUCT, null, values);
    closeWriter();
    return row;
  }

  @Override
  public long updateOrderProductTable(final long orderProductID, final long productID, final long orderID) {
    final ContentValues values = new ContentValues();
    values.put(Column.ORDER_PRODUCT_ID, orderProductID);
    values.put(Column.PRODUCT_ID, productID);
    values.put(Column.ORDER_ID, orderID);
    openWriter();
    final long row = dbWriter.insertOrThrow(Table.ORDER_PRODUCT, null, values);
    closeWriter();
    return row;
  }

  @Override
  public void deleteAll(final String table) {
    openWriter();
    dbWriter.delete(table, null, null);
    closeWriter();
  }

  private synchronized void openWriter() {
    if (dbWriter == null) {
      dbWriter = dbHelper.getWritableDatabase();
    }
  }

  private synchronized void closeWriter() {
    if (dbWriter != null) {
      dbWriter.close();
    }
    dbWriter = null;
  }

  private synchronized void openReader() {
    if (dbReader == null) {
      dbReader = dbHelper.getReadableDatabase();
    }
  }

  private synchronized void closeReader() {
    if (dbReader != null) {
      dbReader.close();
    }
    dbReader = null;
  }
}
