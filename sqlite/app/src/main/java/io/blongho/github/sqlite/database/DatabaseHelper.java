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

import androidx.annotation.Nullable;
import io.blongho.github.sqlite.constants.Column;
import io.blongho.github.sqlite.constants.Table;

public class DatabaseHelper extends SQLiteOpenHelper {

  private final static String CREATE_CUSTOMER_TABLE = String.format(
      "CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT NOT NULL, %s TEXT);",
      Table.CUSTOMER, Column.CUSTOMER_ID, Column.CUSTOMER_NAME, Column.CUSTOMER_ADDR);

  private final static String CREATE_PRODUCT_TABLE = String.format(
      "CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT NOT NULL, %s TEXT);",
      Table.PRODUCT, Column.PRODUCT_ID, Column.PRODUCT_NAME, Column.PRODUCT_DESC);

  private final static String CREATE_ORDER_TABLE = String.format(
      "CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s INTEGER, %s datetime default current_timestamp," +
          " FOREIGN KEY(%s) REFERENCES %s(%s) ON UPDATE CASCADE );",
      Table.ORDER, Column.ORDER_ID, Column.ORDER_CUSTOMER, Column.ORDER_DATE, Column.ORDER_CUSTOMER, Table.CUSTOMER
      , Column.CUSTOMER_ID);

  private final static String CREATE_ORDER_PRODUCT_TABLE = String.format(
      "CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s INTEGER, %s INTEGER, " +
          "FOREIGN KEY(%s) REFERENCES %s(%s) ON UPDATE CASCADE" +
          "FOREIGN KEY(%s) REFERENCES %s(%s)) ON UPDATE CASCADE;",
      Table.ORDER_PRODUCT, Column.ORDER_PRODUCT_ID, Column.ORDER_ID, Column.CUSTOMER_ID,
      Column.ORDER_ID, Table.ORDER, Column.ORDER_ID, Column.CUSTOMER_ID, Table.CUSTOMER,
      Column.CUSTOMER_ID);

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
  public DatabaseHelper(
      @Nullable final Context context, @Nullable final String name,
      @Nullable final SQLiteDatabase.CursorFactory factory, final int version) {
    super(context, name, factory, version);
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

  }
}
