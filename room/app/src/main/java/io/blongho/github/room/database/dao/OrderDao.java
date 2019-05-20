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

package io.blongho.github.room.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.blongho.github.room.model.Order;
import io.blongho.github.room.model.Product;

/**
 * The interface Order dao.
 */
@Dao
public interface OrderDao {
	/**
	 * Gets all orders.
	 *
	 * @return the all orders
	 */
	@Query ("SELECT * FROM TB_ORDER")
	List<Order> getAllOrders();

	/**
	 * Insert orders.
	 *
	 * @param orders the orders
	 */
	@Insert (onConflict = OnConflictStrategy.REPLACE)
	void insertOrders(Order... orders);

	/**
	 * Update order.
	 *
	 * @param orders the orders
	 *
	 * @return the affected row
	 */
	@Update
	int updateOrder(Order orders);

	/**
	 * Delete order.
	 *
	 * @param order the order
	 */
	@Delete
	void deleteOrder(Order order);

	/**
	 * Delete all orders.
	 */
	@Query ("DELETE FROM TB_ORDER")
	void deleteAllOrders();

	/**
	 * Gets products in order.
	 *
	 * @param orderID the order id
	 *
	 * @return the products in order
	 */

	@Query ("SELECT * FROM TB_PRODUCT LEFT JOIN TB_ORDER ON TB_ORDER.order_id=:orderID")
	List<Product> getProductsInOrder(final long orderID);

	/**
	 * Delete order with attribute.
	 *
	 * @param attribute the attribute
	 */
	@Query ("DELETE FROM TB_ORDER WHERE order_id=:attribute OR customer_id=:attribute OR order_date=:attribute")
	void deleteOrderWithAttribute(final String attribute);
}
