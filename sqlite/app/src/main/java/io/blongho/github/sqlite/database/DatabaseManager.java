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

import io.blongho.github.sqlite.model.Customer;
import io.blongho.github.sqlite.model.Order;
import io.blongho.github.sqlite.model.OrderProduct;
import io.blongho.github.sqlite.model.Product;

public class DatabaseManager implements DatabaseOperations {
  // tables
  public static final String TB_CUSTOMER = "tb_customer";
  public static final String TB_ORDER = "tb_order";
  public static final String TB_PRODUCT = "tb_product";
  public static final String TB_ORDER_PRODUCT = "tb_order_product";
  // COLUMNS
  // Customer columns
  final public static String COL_CUSTOMER_ID = "customer_id";
  final public static String COL_CUSTOMER_NAME = "customer_name";
  final public static String COL_CUSTOMER_ADDR = "customer_addr";
  // Order columns
  final public static String COL_ORDER_ID = "order_id";
  final public static String COL_ORDER_DATE = "order_date";
  public static final String COL_ORDER_CUSTOMER = "order_customer";
  // Product columns
  final public static String COL_PRODUCT_ID = "product_id";
  final public static String COL_PRODUCT_NAME = "product_name";
  final public static String COL_PRODUCT_DESC = "product_desc";
  // OrderProduct columns
  final public static String COL_OP_ID = "op_id";
  public static final String COL_OP_ORDER = "op_order";
  public static final String COL_OP_PRODUCT = "op_product";

  private static final String TAG = "DatabaseManager";

  private static SQLiteDatabase dbWriter = null;
  private final Object writeLock = new Object();

  private DatabaseHelper dbHelper;

  public DatabaseManager(final Context context) {
    dbHelper = new DatabaseHelper(context);
  }

  @Override
  public void addCustomer(final Customer... customers) {
    final ContentValues contentValues = new ContentValues();
    getWritableDatabase();
    for (Customer customer : customers) {
      contentValues.put(COL_CUSTOMER_ID, customer.getId());
      contentValues.put(COL_CUSTOMER_NAME, customer.getName());
      contentValues.put(COL_CUSTOMER_ADDR, customer.getCity());
      dbWriter.insertWithOnConflict(TB_CUSTOMER, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
      contentValues.clear();
    }
  }

  @Override
  public void deleteCustomer(final Customer... customers) {
    getWritableDatabase();
    for (Customer customer : customers) {
      dbWriter.delete(TB_CUSTOMER, COL_CUSTOMER_ID + "=?", new String[]{String.valueOf(customer.getId())});
    }
  }

  @Override
  public void deleteCustomerWithName(final String customerName) {
    getWritableDatabase();
    dbWriter.delete(TB_CUSTOMER, COL_CUSTOMER_NAME + "=?", new String[]{customerName});
  }

  @Override
  public void deleteCustomerWithId(final long customerID) {
    getWritableDatabase();
    dbWriter.delete(TB_CUSTOMER, COL_CUSTOMER_ID + "=?", new String[]{String.valueOf(customerID)});
  }

  @Override
  public void updateCustomer(final Customer... customers) {
    final ContentValues contentValues = new ContentValues();
    getWritableDatabase();
    for (Customer customer : customers) {
      contentValues.put(COL_CUSTOMER_ID, customer.getId());
      contentValues.put(COL_CUSTOMER_NAME, customer.getName());
      contentValues.put(COL_CUSTOMER_ADDR, customer.getCity());
      dbWriter.updateWithOnConflict(TB_CUSTOMER, contentValues, COL_CUSTOMER_ID + "=?",
          new String[]{String.valueOf(customer.getId())}, SQLiteDatabase.CONFLICT_REPLACE);
      contentValues.clear();
    }
  }

  @Override
  public List<Customer> getAllCustomers() {
    getWritableDatabase();
    final Cursor cursor = dbWriter.rawQuery("SELECT * FROM " + TB_CUSTOMER, null);
    final List<Customer> customers = new ArrayList<>();
    if (cursor.moveToFirst()) {
      while (!cursor.isAfterLast()) {
        final long id = cursor.getLong(cursor.getColumnIndex(COL_CUSTOMER_ID));
        final String name = cursor.getString(cursor.getColumnIndex(COL_CUSTOMER_NAME));
        final String address = cursor.getString(cursor.getColumnIndex(COL_CUSTOMER_ADDR));
        final Customer customer = new Customer(id, name, address);
        customers.add(customer);
        cursor.moveToNext();
      }
    }
    cursor.close();
    return customers;
  }

  @Override
  public void addProduct(final Product... products) {
    final ContentValues contentValues = new ContentValues();
    getWritableDatabase();
    for (Product product : products) {
      contentValues.put(COL_PRODUCT_ID, product.getId());
      contentValues.put(COL_PRODUCT_NAME, product.getName());
      contentValues.put(COL_PRODUCT_DESC, product.getDescription());
      dbWriter.insertWithOnConflict(TB_PRODUCT, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
      contentValues.clear();
    }
  }

  @Override
  public void deleteProduct(final Product... products) {
    getWritableDatabase();
    for (Product product : products) {
      dbWriter.delete(TB_PRODUCT, COL_PRODUCT_ID + "=?", new String[]{String.valueOf(product.getId())});
    }
  }

  @Override
  public void deleteProductWithName(final String productName) {
    getWritableDatabase();
    dbWriter.delete(TB_PRODUCT, COL_PRODUCT_NAME + "=?", new String[]{productName});
  }

  @Override
  public void deleteProductWithId(final long productID) {
    getWritableDatabase();
    dbWriter.delete(TB_PRODUCT, COL_PRODUCT_ID + "=?", new String[]{String.valueOf(productID)});
  }

  @Override
  public void updateProduct(final Product... products) {
    final ContentValues contentValues = new ContentValues();
    getWritableDatabase();
    for (Product product : products) {
      contentValues.put(COL_PRODUCT_ID, product.getId());
      contentValues.put(COL_PRODUCT_NAME, product.getName());
      contentValues.put(COL_PRODUCT_DESC, product.getDescription());
      dbWriter.updateWithOnConflict(TB_PRODUCT, contentValues, COL_PRODUCT_ID + "=?" + product.getId(),
          null, SQLiteDatabase.CONFLICT_REPLACE);
      contentValues.clear();
    }
  }

  @Override
  public List<Product> getAllProducts() {
    getWritableDatabase();
    final Cursor cursor = dbWriter.rawQuery("SELECT * FROM " + TB_PRODUCT, null);
    final List<Product> products = new ArrayList<>();
    if (cursor.moveToFirst()) {
      while (!cursor.isAfterLast()) {
        final long id = cursor.getLong(cursor.getColumnIndex(COL_PRODUCT_ID));
        final String name = cursor.getString(cursor.getColumnIndex(COL_PRODUCT_NAME));
        final String desc = cursor.getString(cursor.getColumnIndex(COL_PRODUCT_DESC));
        final Product product = new Product(id, name, desc);
        products.add(product);
        cursor.moveToNext();
      }
    }
    cursor.close();
    return products;
  }

  @Override
  public void addOrder(final Order... orders) {
    final ContentValues contentValues = new ContentValues();
    getWritableDatabase();
    for (Order order : orders) {
      contentValues.put(COL_ORDER_ID, order.getId());
      contentValues.put(COL_ORDER_CUSTOMER, order.getCustomer());
      contentValues.put(COL_ORDER_DATE, order.getDate().toString());
      dbWriter.insertWithOnConflict(TB_ORDER, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
      contentValues.clear();
    }
  }

  @Override
  public void deleteOrder(final Order... orders) {
    getWritableDatabase();
    for (Order order : orders) {
      dbWriter.delete(TB_ORDER, COL_ORDER_ID + "=?", new String[]{String.valueOf(order.getId())});
    }
  }

  @Override
  public void deleteOrderWithId(final long orderID) {
    getWritableDatabase();
    dbWriter.delete(TB_ORDER, COL_ORDER_ID + "=?", new String[]{String.valueOf(orderID)});

  }

  @Override
  public List<Order> getAllOrders() {
    getWritableDatabase();
    final Cursor cursor = dbWriter.rawQuery("SELECT * FROM " + TB_ORDER, null);
    final List<Order> orders = new ArrayList<>();
    long id, customer;
    String dateString;
    if (cursor.moveToFirst()) {
      while (!cursor.isAfterLast()) {
        id = cursor.getLong(cursor.getColumnIndex(COL_ORDER_ID));
        customer = cursor.getLong(cursor.getColumnIndex(COL_ORDER_CUSTOMER));
        dateString = cursor.getString(cursor.getColumnIndex(COL_ORDER_DATE));
        orders.add(new Order(id, customer, new Date(dateString)));
        cursor.moveToNext();
      }
    }
    cursor.close();
    return orders;
  }

  @Override
  public void deleteAll(final String... tables) {
    getWritableDatabase();

    for (String table : tables) {
      dbWriter.delete(table, null, null);
    }
  }

  @Override
  public void deleteOrderProduct(OrderProduct... orderProduct) {
    getWritableDatabase();
    for (OrderProduct product : orderProduct) {
      dbWriter.delete(TB_ORDER_PRODUCT, COL_OP_ID + "=?", new String[]{String.valueOf(product.getId())});
    }
  }

  @Override
  public void addOrderProduct(OrderProduct... orderProducts) {
    final ContentValues contentValues = new ContentValues();
    getWritableDatabase();
    for (OrderProduct orderProduct : orderProducts) {
      contentValues.put(COL_OP_ID, orderProduct.getId());
      contentValues.put(COL_OP_ORDER, orderProduct.getOrder());
      contentValues.put(COL_OP_PRODUCT, orderProduct.getProduct());
      dbWriter.insertWithOnConflict(TB_ORDER_PRODUCT, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
      contentValues.clear();
    }
  }

  @Override
  public List<OrderProduct> getAllOrderProducts() {
    getWritableDatabase();
    final Cursor cursor = dbWriter.rawQuery("SELECT * FROM " + TB_ORDER_PRODUCT, null);
    List<OrderProduct> orderProducts = new ArrayList<>();
    long id, order, product;

    if (cursor.moveToFirst()) {
      if (!cursor.isAfterLast()) {
        id = cursor.getLong(cursor.getColumnIndex(COL_OP_ID));
        order = cursor.getLong(cursor.getColumnIndex(COL_OP_ORDER));
        product = cursor.getLong(cursor.getColumnIndex(COL_OP_PRODUCT));
        orderProducts.add(new OrderProduct(id, order, product));
        cursor.moveToNext();
      }
    }
    cursor.close();
    return orderProducts;
  }

  /**
   * Add item long.
   *
   * @param <T>  the type parameter
   * @param item the item
   * @return the long
   */
  public <T> Long addItem(final T item) {
    if (item.getClass().isInstance(new Customer())) {
      //Log.e(TAG, "addItem: " + item);
      addCustomer((Customer) item);
    } else if (item.getClass().isInstance(new Order())) {
      //Log.e(TAG, "addItem: " + item);
      addOrder((Order) item);
    } else if (item.getClass().isInstance(new Product())) {
      //Log.e(TAG, "addItem: " + item);
      addProduct((Product) item);
    } else if (item.getClass().isInstance(new OrderProduct())) {
      //Log.e(TAG, "addItem: " + item);
      addOrderProduct((OrderProduct) item);
    }
    return null;
  }

  /**
   * Delete item.
   *
   * @param <T>  the type parameter
   * @param item the item
   */
  public <T> void deleteItem(T item) {
    if (item.getClass().isInstance(new Customer())) {
      deleteCustomer((Customer) item);
    } else if (item.getClass().isInstance(new Order())) {
      deleteOrder((Order) item);
    } else if (item.getClass().isInstance(new Product())) {
      deleteProduct((Product) item);
    } else if (item.getClass().isInstance(new OrderProduct())) {
      deleteOrderProduct((OrderProduct) item);
    }
  }

  /**
   * Delete items.
   *
   * @param <T>   the type parameter
   * @param items the items
   */
  public <T> void deleteItems(T[] items) {
    if (items.length > 0) {
      if (items[0].getClass().isInstance(new Customer())) {
        deleteCustomer((Customer[]) items);
      } else if (items[0].getClass().isInstance(new Order())) {
        deleteOrder((Order[]) items);
      } else if (items[0].getClass().isInstance(new Product())) {
        deleteProduct((Product[]) items);
      } else if (items[0].getClass().isInstance(new OrderProduct())) {
        deleteOrderProduct((OrderProduct[]) items);
      }
    }

  }

  /**
   * Open writeableDatabase in a thread-safe way
   *
   * @return dbWriter
   */
  public SQLiteDatabase getWritableDatabase() {
    if (dbWriter == null) {
      synchronized (writeLock) {
        if (dbWriter == null) {
          dbWriter = dbHelper.getWritableDatabase();
        }
      }
    }
    return dbWriter;
  }

  /**
   * from https://developer.android.com/training/data-storage/sqlite#PersistingDbConnection
   * Since getWritableDatabase() and getReadableDatabase() are expensive to call when the database is closed,
   * you should leave your database connection open for as long as you possibly need to access it.
   * <p>
   * Typically, it is optimal to close the database in the onDestroy() of the calling Activity.
   */
  // Close opened database instances in a thread-safe manner
  private void close() {
    if (dbWriter.inTransaction()) {
      synchronized (writeLock) {
        dbWriter.close();
      }
    }
    if (dbWriter.isOpen() && !dbWriter.inTransaction()) {
      dbWriter.close();
      dbHelper.close();
    }
  }
}
