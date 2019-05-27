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

package io.blongho.github.room.model;

import java.util.Date;
import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.TypeConverters;
import io.blongho.github.room.constants.Column;
import io.blongho.github.room.constants.Table;

/**
 * The type Order<br> An order holds an identifier to the customer and the list of products ordered.
 *
 * @author Bernard Che Longho
 * @version 1.0
 * @since 2019-05-10
 */
@Entity(tableName = Table.ORDER,
    primaryKeys = {Column.ORDER_ID},
    foreignKeys = {
        @ForeignKey(
            entity = Customer.class,
            parentColumns = Column.CUSTOMER_ID,
            childColumns = Column.ORDER_CUSTOMER,
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    },
    indices = {
        @Index(
            value = {
                Column.ORDER_ID,
                Column.ORDER_CUSTOMER
            },
            unique = true)
    })
public class Order {
  @ColumnInfo(name = Column.ORDER_DATE)
  @TypeConverters(DateConverter.class)
  private Date date;
  @ColumnInfo(name = Column.ORDER_ID)
  private long id;
  @ColumnInfo(name = Column.ORDER_CUSTOMER)
  private long customer;
  @Ignore
  private List<Product> products;

  /**
   * Instantiates a new Order.
   */
  public Order() {
  }

  /**
   * Instantiates a new Order.
   *
   * @param order_id    the order id
   * @param customer_id the customer id
   */
  @Ignore
  public Order(final long order_id, final long customer_id) {
    this.id = order_id;
    this.customer = customer_id;
    date = new Date();
  }

  /**
   * Gets order id.
   *
   * @return the order id
   */
  public long getId() {
    return id;
  }

  /**
   * Sets order id.
   *
   * @param id the order id
   */
  public void setId(final long id) {
    this.id = id;
  }

  /**
   * Gets customer id.
   *
   * @return the customer id
   */
  public long getCustomer() {
    return customer;
  }

  /**
   * Sets customer id.
   *
   * @param customer the customer id
   */
  public void setCustomer(final long customer) {
    this.customer = customer;
  }

  /**
   * Gets products.
   *
   * @return the products
   */
  public List<Product> getProducts() {
    return products;
  }

  /**
   * Sets products.
   *
   * @param products the products
   */
  public void setProducts(final List<Product> products) {
    this.products = products;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("Order{");
    sb.append("id=").append(id);
    sb.append(", customer=").append(customer);
    sb.append(", date=").append(date);
    sb.append('}');
    return sb.toString();
  }

  /**
   * Gets date.
   *
   * @return the date
   */
  public Date getDate() {
    return date;
  }

  /**
   * Sets date.
   *
   * @param date the date
   */
  public void setDate(final Date date) {
    this.date = date;
  }
}
