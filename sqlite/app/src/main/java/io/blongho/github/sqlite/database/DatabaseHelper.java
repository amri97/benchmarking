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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
  private final static String dbName = "customer_order_sqlite";
  private static final String TAG = "DatabaseHelper";
  private final static String CREATE_CUSTOMER_TABLE = "CREATE TABLE IF NOT EXISTS tb_customer (" +
      "customer_id INTEGER PRIMARY KEY, " +
      "customer_name TEXT NOT NULL, " +
      "customer_addr TEXT);";
  private final static String CREATE_PRODUCT_TABLE = "CREATE TABLE IF NOT EXISTS tb_product (" +
      "product_id INTEGER PRIMARY KEY, " +
      "product_name TEXT NOT NULL, " +
      "product_desc TEXT);";
  private final static String CREATE_ORDER_TABLE = "CREATE TABLE IF NOT EXISTS tb_order (" +
      "order_id INTEGER PRIMARY KEY, " +
      "order_customer INTEGER NOT NULL," +
      "order_date DATETIME DEFAULT current_timestamp," +
      "FOREIGN KEY(order_customer) " +
      "REFERENCES tb_customer(customer_id)" +
      "ON DELETE CASCADE ON UPDATE CASCADE);";
  private final static String CREATE_ORDER_PRODUCT_TABLE = "CREATE TABLE IF NOT EXISTS tb_order_product (" +
      "op_id INTEGER PRIMARY KEY, " +
      "op_order INTEGER NOT NULL, " +
      "op_product INTEGER NOT NULL, " +
      "FOREIGN KEY(op_order) REFERENCES tb_order(order_id) ON DELETE CASCADE ON UPDATE CASCADE, " +
      "FOREIGN KEY(op_product) REFERENCES tb_product(product_id) ON DELETE CASCADE ON UPDATE CASCADE);";
  private static int dbVersion = 1;

  /**
   * Create a helper object to create, open, and/or manage a database. This method always returns very quickly.  The
   * database is not actually created or opened until one of {@link #getWritableDatabase} or
   * {@link #getReadableDatabase}
   * is called.
   *
   * @param context to use for locating paths to the the database
   * @param name    of the database file, or null for an in-memory database
   * @param factory to use for creating cursor objects, or null for the default
   * @param version number of the database (starting at 1); if the database is older, {@link #onUpgrade} will be
   *                used to
   *                upgrade the database; if the database is newer, {@link #onDowngrade} will be used to downgrade
   *                the database
   */
  private DatabaseHelper(
      @Nullable final Context context, @Nullable final String name,
      @Nullable final SQLiteDatabase.CursorFactory factory, final int version) {
    super(context, name, factory, version);
  }

  /**
   * Create a helper object to create, open, and/or manage a database.
   * This method always returns very quickly.  The database is not actually
   * created or opened until one of {@link #getWritableDatabase} or
   * {@link #getReadableDatabase} is called.
   *
   * @param context to use for locating paths to the the database
   */
  public DatabaseHelper(@NonNull final Context context) {
    this(context, dbName, null, dbVersion);
  }

  /**
   * Called when the database is created for the first time. This is where the creation of tables and the initial
   * population of the tables should happen.
   *
   * @param db The database.
   */
  @Override
  public void onCreate(final SQLiteDatabase db) {
    db.execSQL(CREATE_CUSTOMER_TABLE);
    db.execSQL(CREATE_PRODUCT_TABLE);
    db.execSQL(CREATE_ORDER_TABLE);
    db.execSQL(CREATE_ORDER_PRODUCT_TABLE);
    Log.d(TAG, "onCreate() called with: db = [" + db + "]");
  }

  /**
   * Called when the database needs to be upgraded. The implementation should use this method to drop tables, add
   * tables, or do anything else it needs to upgrade to the new schema version.
   *
   * <p>
   * The SQLite ALTER TABLE documentation can be found
   * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
   * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns you can use ALTER
   * TABLE
   * to rename the old table, then create the new table and then populate the new table with the contents of the old
   * table.
   * </p><p>
   * This method executes within a transaction.  If an exception is thrown, all changes will automatically be rolled
   * back.
   * </p>
   *
   * @param db         The database.
   * @param oldVersion The old database version.
   * @param newVersion The new database version.
   */
  @Override
  public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS tb_customer;");
    db.execSQL("DROP TABLE IF EXISTS tb_product;");
    db.execSQL("DROP TABLE IF EXISTS tb_order;");
    db.execSQL("DROP TABLE IF EXISTS tb_order_product;");
    // do any other upgrade stuff here, maybe adding table culums etc
    onCreate(db);
  }

  @Override
  public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    onUpgrade(db, oldVersion, newVersion);
  }

  @Override
  public void onOpen(SQLiteDatabase db) {
    super.onOpen(db);
  }
}
