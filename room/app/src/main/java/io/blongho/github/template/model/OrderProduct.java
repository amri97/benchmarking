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

package io.blongho.github.template.model;

/**
 * The type Order product.
 */
public class OrderProduct {
	private long orderProduct_id;
	private Customer customer_id;
	private Order order_id;

	/**
	 * Instantiates a new Order product.
	 */
	public OrderProduct() {
	}

	/**
	 * Instantiates a new Order product.
	 *
	 * @param orderProduct_id the order product id
	 * @param customer_id     the customer id
	 * @param order_id        the order id
	 */
	public OrderProduct(final long orderProduct_id, final Customer customer_id, final Order order_id) {
		this.orderProduct_id = orderProduct_id;
		this.customer_id = customer_id;
		this.order_id = order_id;
	}

	/**
	 * Gets order product id.
	 *
	 * @return the order product id
	 */
	public long getOrderProduct_id() {
		return orderProduct_id;
	}

	/**
	 * Sets order product id.
	 *
	 * @param orderProduct_id the order product id
	 */
	public void setOrderProduct_id(final long orderProduct_id) {
		this.orderProduct_id = orderProduct_id;
	}

	/**
	 * Gets customer id.
	 *
	 * @return the customer id
	 */
	public Customer getCustomer_id() {
		return customer_id;
	}

	/**
	 * Sets customer id.
	 *
	 * @param customer_id the customer id
	 */
	public void setCustomer_id(final Customer customer_id) {
		this.customer_id = customer_id;
	}

	/**
	 * Gets order id.
	 *
	 * @return the order id
	 */
	public Order getOrder_id() {
		return order_id;
	}

	/**
	 * Sets order id.
	 *
	 * @param order_id the order id
	 */
	public void setOrder_id(final Order order_id) {
		this.order_id = order_id;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("OrderProduct{");
		sb.append("orderProduct_id=").append(orderProduct_id);
		sb.append(", customer_id=").append(customer_id);
		sb.append(", order_id=").append(order_id);
		sb.append('}');
		return sb.toString();
	}


}
