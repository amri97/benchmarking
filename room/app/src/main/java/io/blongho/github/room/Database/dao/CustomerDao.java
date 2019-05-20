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

package io.blongho.github.room.Database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.blongho.github.room.model.Customer;
import io.blongho.github.room.model.Order;

/**
 * The interface Customer dao.
 */
@Dao
public interface CustomerDao {
	/**
	 * Gets all customers.
	 *
	 * @return the all customers
	 */
	@Query ("SELECT * FROM TB_CUSTOMER")
	List<Customer> getAllCustomers();

	/**
	 * Insert customers.
	 *
	 * @param customers the customers
	 *
	 * @return the number of rows affected
	 */
	@Insert
	int insertCustomers(Customer... customers);

	/**
	 * Update customer.
	 *
	 * @param customer the customer
	 *
	 * @return the affected row
	 */
	@Update
	int updateCustomer(Customer customer);

	/**
	 * Delete customer.
	 *
	 * @param customer the customer
	 */
	@Delete
	void deleteCustomer(Customer customer);

	/**
	 * Delete all customers long.
	 */
	@Query ("DELETE FROM TB_CUSTOMER")
	void deleteAllCustomers();

	/**
	 * Gets order by customer.
	 *
	 * @param customerID the customer id
	 *
	 * @return the orders by customer
	 */
	@Query ("SELECT * FROM TB_ORDER WHERE TB_ORDER.customer_id=:customerID")
	List<Order> getOrderByCustomer(final long customerID);

	/**
	 * Delete customer with attribute.
	 *
	 * @param attribute the attribute
	 */
	@Query (
	  "DELETE FROM TB_CUSTOMER WHERE customer_name =:attribute OR customer_addr =:attribute OR customer_id " +
	  "=:attribute")
	void deleteCustomerWithAttribute(final String attribute);
}
