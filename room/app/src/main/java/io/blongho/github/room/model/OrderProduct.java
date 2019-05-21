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

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import io.blongho.github.room.constants.Column;
import io.blongho.github.room.constants.Table;

/**
 * The type Order product.
 */
@Entity(tableName = Table.ORDER_PRODUCT,
    primaryKeys = {
        Column.ORDER_PRODUCT_ID
    },
    foreignKeys = {
        @ForeignKey(
            entity = Order.class,
            parentColumns = Column.ORDER_ID,
            childColumns = Column.ORDER_PRODUCT_ORDER,
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = Product.class,
            parentColumns = Column.PRODUCT_ID,
            childColumns = Column.ORDER_PRODUCT_PRODUCT,
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    },
    indices = {
        @Index(
            value = {
                Column.ORDER_PRODUCT_ID,
                Column.ORDER_PRODUCT_ORDER,
                Column.ORDER_PRODUCT_PRODUCT},
            unique = true
        )
    })
public class OrderProduct {
  @ColumnInfo(name = Column.ORDER_PRODUCT_ID)
  private long id;
  @ColumnInfo(name = Column.ORDER_PRODUCT_ORDER)
  private long order;
  @ColumnInfo(name = Column.ORDER_PRODUCT_PRODUCT)
  private long product;

  /**
   * Instantiates a new Order product.
   */
  public OrderProduct() {
  }

  /**
   * Instantiates a new Order product.
   *
   * @param orderProductID the order product id
   * @param orderID        the order id
   * @param productID      the product id
   */
  @Ignore
  public OrderProduct(final long orderProductID, final long orderID, final long productID) {
    this.id = orderProductID;
    this.order = orderID;
    this.product = productID;
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  public long getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(final long id) {
    this.id = id;
  }

  /**
   * Gets product.
   *
   * @return the product
   */
  public long getProduct() {
    return product;
  }

  /**
   * Sets product.
   *
   * @param product the product
   */
  public void setProduct(final long product) {
    this.product = product;
  }

  /**
   * Gets order.
   *
   * @return the order
   */
  public long getOrder() {
    return order;
  }

  /**
   * Sets order.
   *
   * @param order the order
   */
  public void setOrder(final long order) {
    this.order = order;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("OrderProduct{");
    sb.append("id=").append(id);
    sb.append(", order=").append(order);
    sb.append(", product=");
    sb.append(product);
    sb.append('}');
    return sb.toString();
  }

}
