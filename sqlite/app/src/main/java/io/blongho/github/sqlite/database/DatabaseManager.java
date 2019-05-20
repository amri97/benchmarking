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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.blongho.github.sqlite.constants.Column;
import io.blongho.github.sqlite.constants.Table;
import io.blongho.github.sqlite.model.Customer;
import io.blongho.github.sqlite.model.Order;
import io.blongho.github.sqlite.model.OrderProduct;
import io.blongho.github.sqlite.model.Product;

public class DatabaseManager implements DatabaseOperations {
  private static final String TAG = "DatabaseManager";
  private DatabaseHelper dbHelper;

  public DatabaseManager(final Context context) {
    dbHelper = new DatabaseHelper(context);
  }

  @Override
  public void populateDatabase(final List<Customer> customers, final List<Product> products, final List<Order> orders,
                               final List<OrderProduct> orderProducts) {
    final SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();
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
      updateOrderProductTable(orderProduct.getId(), orderProduct.getProduct(), orderProduct.getOrder());
    }
    dbWriter.endTransaction();
  }

  @Override
  public long addCustomer(final Customer customer) {
    final SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();
    final ContentValues contentValues = new ContentValues();
    contentValues.put(Column.CUSTOMER_ID, customer.getId());
    contentValues.put(Column.CUSTOMER_NAME, customer.getName());
    contentValues.put(Column.CUSTOMER_ADDR, customer.getCity());
    final long rowid = dbWriter.insertWithOnConflict(Table.CUSTOMER, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    dbWriter.close();
    return rowid;
  }

  @Override
  public long deleteCustomer(final Customer customer) {
    return deleteCustomerWithId(customer.getId());
  }

  @Override
  public long deleteCustomerWithName(final String customerName) {
    final SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();
    final int row = dbWriter.delete(Table.CUSTOMER, Column.CUSTOMER_NAME + "=?",
        new String[]{customerName});
    dbWriter.close();
    return row;
  }

  @Override
  public long deleteCustomerWithId(final long customerID) {
    final SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();
    final int row = dbWriter.delete(Table.CUSTOMER, Column.CUSTOMER_ID + "=?",
        new String[]{String.valueOf(customerID)});
    dbWriter.close();
    return row;
  }

  @Override
  public long updateCustomer(final Customer customer) {
    final ContentValues values = new ContentValues();
    values.put(Column.CUSTOMER_ID, customer.getId());
    values.put(Column.CUSTOMER_NAME, customer.getName());
    values.put(Column.CUSTOMER_ADDR, customer.getCity());
    final SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();
    final int row = dbWriter.update(Table.CUSTOMER, values, Column.CUSTOMER_ID + "=" + customer.getId(), null);
    dbWriter.close();
    return row;
  }

  @Override
  public List<Customer> getAllCustomers() {
    final SQLiteDatabase writer = dbHelper.getWritableDatabase();
    final Cursor cursor = writer.rawQuery("SELECT * FROM " + Table.CUSTOMER, null);
    final List<Customer> customers = new ArrayList<>();
    if (cursor.moveToFirst()) {
      while (!cursor.isAfterLast()) {
        final long id = cursor.getLong(cursor.getColumnIndex(Column.CUSTOMER_ID));
        final String name = cursor.getString(cursor.getColumnIndex(Column.CUSTOMER_NAME));
        final String address = cursor.getString(cursor.getColumnIndex(Column.CUSTOMER_ADDR));
        final Customer customer = new Customer(id, name, address);
        //Log.e(TAG, "getAllCustomers: " + customer);
        customers.add(customer);
        cursor.moveToNext();
      }
    }
    cursor.close();
    writer.close();
    return customers;
  }

  @Override
  public long addProduct(final Product product) {
    final ContentValues contentValues = new ContentValues();
    contentValues.put(Column.PRODUCT_ID, product.getId());
    contentValues.put(Column.PRODUCT_NAME, product.getName());
    contentValues.put(Column.PRODUCT_DESC, product.getDescription());
    final SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();
    final long row = dbWriter.insertWithOnConflict(Table.PRODUCT, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    dbWriter.close();
    return row;
  }

  @Override
  public long deleteProduct(final Product product) {
    return deleteProductWithName(product.getName());
  }

  @Override
  public long deleteProductWithName(final String productName) {
    final SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();
    final long row = dbWriter.delete(Table.PRODUCT, Column.PRODUCT_NAME + "?=", new String[]{productName});
    dbWriter.close();
    return row;
  }

  @Override
  public long deleteProductWithId(final long productID) {
    final SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();
    final long row = dbWriter.delete(Table.PRODUCT, Column.PRODUCT_ID + "?=",
        new String[]{String.valueOf(productID)});
    dbWriter.close();
    return row;
  }

  @Override
  public long updateProduct(final Product product) {
    final ContentValues values = new ContentValues();
    values.put(Column.PRODUCT_ID, product.getId());
    values.put(Column.PRODUCT_NAME, product.getName());
    values.put(Column.PRODUCT_DESC, product.getDescription());
    final SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();
    final int row = dbWriter.update(Table.PRODUCT, values, Column.CUSTOMER_ID + "=" + product.getId(), null);
    dbWriter.close();
    return row;
  }

  @Override
  public List<Product> getAllProducts() {
    final SQLiteDatabase dbReader = dbHelper.getReadableDatabase();
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
    dbReader.close();
    return products;
  }

  @Override
  public long addOrder(final Order order) {
    final ContentValues contentValues = new ContentValues();
    contentValues.put(Column.ORDER_ID, order.getId());
    contentValues.put(Column.ORDER_CUSTOMER, order.getCustomer());
    contentValues.put(Column.ORDER_DATE, order.getDate().toString());
    final SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();
    final long row = dbWriter.insertWithOnConflict(Table.ORDER, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    dbWriter.close();
    return row;
  }

  @Override
  public long deleteOrder(final Order order) {
    return deleteOrderWithId(order.getId());
  }

  @Override
  public long deleteOrderWithId(final long orderID) {
    final SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();
    final long row = dbWriter.delete(Table.ORDER, Column.ORDER_ID + "?=",
        new String[]{String.valueOf(orderID)});
    dbWriter.close();
    return row;
  }

  @Override
  public List<Order> getAllOrders() {
    final SQLiteDatabase dbReader = dbHelper.getReadableDatabase();
    final Cursor cursor = dbReader.rawQuery("SELECT * FROM " + Table.ORDER, null);
    final List<Order> orders = new ArrayList<>();
    if (cursor.moveToFirst()) {
      while (!cursor.isAfterLast()) {
        final long id = cursor.getLong(cursor.getColumnIndex(Column.ORDER_ID));
        final long customer = cursor.getLong(cursor.getColumnIndex(Column.CUSTOMER_ID));
        final String dateString = cursor.getString(cursor.getColumnIndex(Column.ORDER_DATE));
        //SimpleFormatter formatter = new SimpleFormatter();
        final Date date = new Date(dateString);
        final Order order = new Order(id, customer, date);
        orders.add(order);
        cursor.moveToNext();
      }
    }
    cursor.close();
    dbReader.close();
    return orders;
  }

  @Override
  public long updateOrderProductTable(final long product, final long orderID) {
    final ContentValues values = new ContentValues();
    values.put(Column.PRODUCT_ID, product);
    values.put(Column.ORDER_ID, orderID);
    final SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();
    final long row = dbWriter.insertWithOnConflict(Table.ORDER_PRODUCT, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    dbWriter.close();
    return row;
  }

  @Override
  public long updateOrderProductTable(final long orderProductID, final long productID, final long orderID) {
    final ContentValues values = new ContentValues();
    values.put(Column.ORDER_PRODUCT_ID, orderProductID);
    values.put(Column.PRODUCT_ID, productID);
    values.put(Column.ORDER_ID, orderID);
    final SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();
    final long row = dbWriter.insertWithOnConflict(Table.ORDER_PRODUCT, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    dbWriter.close();
    return row;
  }

  @Override
  public void deleteAll(final String table) {
    final SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();
    dbWriter.delete(table, null, null);
    dbWriter.close();
  }

  public <T> Long addItem(final T item) {
    final Customer customer = new Customer();
    final Order order = new Order();
    final Product product = new Product();
    final OrderProduct orderProduct = new OrderProduct();

    if (item.getClass().isInstance(customer)) {
      //Log.e(TAG, "addItem: " + item);
      return addCustomer((Customer) item);
    } else if (item.getClass().isInstance(order)) {
      //Log.e(TAG, "addItem: " + item);
      return addOrder((Order) item);
    } else if (item.getClass().isInstance(product)) {
      //Log.e(TAG, "addItem: " + item);
      return addProduct((Product) item);
    } else if (item.getClass().isInstance(orderProduct)) {
      final OrderProduct pro = (OrderProduct) item;
      //Log.e(TAG, "addItem: " + item);
      return updateOrderProductTable(pro.getId(), pro.getOrder(), pro.getProduct());
    }
    return null;
  }
}
