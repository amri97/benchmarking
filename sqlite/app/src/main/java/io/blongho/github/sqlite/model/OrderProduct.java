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

package io.blongho.github.sqlite.model;

/**
 * The type Order product.
 */
public class OrderProduct {

  private long product;

  private long id;

  private long order;

  /**
   * Instantiates a new Order product.
   */
  public OrderProduct() {
  }

  /**
   * Instantiates a new Order product.
   *
   * @param orderProduct_id the order product id
   * @param product_id      the customer id
   * @param order_id        the order id
   */
  public OrderProduct(final long orderProduct_id, final long product_id, final long order_id) {
    this.id = orderProduct_id;
    this.product = product_id;
    this.order = order_id;
  }

  /**
   * @return the product
   */
  public long getProduct() {
    return product;
  }

  /**
   * @param product the product to set
   */
  public void setProduct(final long product) {
    this.product = product;
  }

  /**
   * @return the id
   */
  public long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(final long id) {
    this.id = id;
  }

  /**
   * @return the order
   */
  public long getOrder() {
    return order;
  }

  /**
   * @param order the order to set
   */
  public void setOrder(final long order) {
    this.order = order;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("OrderProduct{");
    sb.append("orderProduct_id=").append(id);
    sb.append(", product_id=").append(product);
    sb.append(", order_id=").append(order);
    sb.append('}');
    return sb.toString();
  }
}
