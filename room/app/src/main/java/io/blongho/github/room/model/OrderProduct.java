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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import io.blongho.github.room.constants.Column;
import io.blongho.github.room.constants.Table;

/**
 * The type Order product.
 */
@Entity (tableName = Table.ORDER_PRODUCT)
public class OrderProduct {
	@PrimaryKey
	@ColumnInfo (name = Column.ORDER_PRODUCT_ID)
	private long id;
	@ColumnInfo (name = Column.ORDER_PRODUCT_CUSTOMER)
	private long customer;
	@ColumnInfo (name = Column.ORDER_PRODUCT_ORDER)
	private long order;

	/**
	 * Instantiates a new Order product.
	 */
	public OrderProduct() {
	}

	/**
	 * Instantiates a new Order product.
	 *
	 * @param orderProductID the order product id
	 * @param customerID the customer id
	 * @param orderID the order id
	 */
	@Ignore
	public OrderProduct(
	  @Nullable final long orderProductID, @NonNull final long customerID, @NonNull final long orderID) {
		this.id = orderProductID;
		this.customer = customerID;
		this.order = orderID;
	}

	/**
	 * Gets order product id.
	 *
	 * @return the order product id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets order product id.
	 *
	 * @param id the order product id
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
	 * Gets order id.
	 *
	 * @return the order id
	 */
	public long getOrder() {
		return order;
	}

	/**
	 * Sets order id.
	 *
	 * @param order the order id
	 */
	public void setOrder(final long order) {
		this.order = order;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("OrderProduct{");
		sb.append("id=").append(id);
		sb.append(", customer=").append(customer);
		sb.append(", order=").append(order);
		sb.append('}');
		return sb.toString();
	}

}
