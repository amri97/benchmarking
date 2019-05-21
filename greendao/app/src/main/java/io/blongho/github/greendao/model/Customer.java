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

package io.blongho.github.greendao.model;

import java.util.List;

/**
 * A Customer object.<br>
 * A Customer object has customer details and the orders done by this customer
 *
 * @author Bernard Che Longho
 * @version 1.0
 * @since 2019-05-21
 */
public class Customer {
  private Long id;
  private String name;
  private String city;
  private List<Order> orders;

  /**
   * Instantiates a new Customer.
   */
  public Customer() {
  }

  public Customer(Long id, String name, String city) {
    this.id = id;
    this.name = name;
    this.city = city;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  /**
   * Gets customer id.
   *
   * @return the customer id
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets customer id.
   *
   * @param id the customer id
   */
  public void setId(final Long id) {
    this.id = id;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * Gets orders.
   *
   * @return the orders
   */
  public List<Order> getOrders() {
    return orders;
  }

  /**
   * Sets orders.
   *
   * @param orders the orders
   */
  public void setOrders(final List<Order> orders) {
    this.orders = orders;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("Customer{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", city=").append(city);
    sb.append('}');
    return sb.toString();
  }

}
