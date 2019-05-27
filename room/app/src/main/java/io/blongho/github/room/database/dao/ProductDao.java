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

import io.blongho.github.room.model.Product;

/**
 * The interface Product dao.
 */
@Dao
public interface ProductDao {
	/**
	 * Gets all products.
	 *
	 * @return the all products
	 */
	@Query ("SELECT * FROM TB_PRODUCT")
	List<Product> getAllProducts();

	/**
	 * Insert products.
	 *
	 * @param products the products
	 */
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insertProducts(Product... products);

	/**
	 * Update product.
	 *
	 * @param product the product
	 *
	 * @return the affected row.
	 */
	@Update(onConflict = OnConflictStrategy.REPLACE)
	int updateProduct(Product... product);

	/**
	 * Delete product.
	 *
	 * @param product the product
	 */
	@Delete
	void deleteProduct(Product... product);

	/**
	 * Delete all products.
	 */
	@Query ("DELETE FROM TB_PRODUCT")
	void deleteAllProducts();

	/**
	 * Delete product with attribute.
	 * <p> This method gets an order based on the id, the date, the id of the
	 * customer</p>
	 *
	 * @param attribute the attribute
	 */
	@Query ("DELETE FROM TB_PRODUCT WHERE product_id =:attribute OR product_name=:attribute OR " +
	        "product_desc=:attribute")
	void deleteProductWithAttribute(final String attribute);

	@Query("SELECT COUNT(*) FROM TB_PRODUCT")
	long productCount();
}
