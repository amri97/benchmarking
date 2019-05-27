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

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.blongho.github.room.model.OrderProduct;

/**
 * The interface Order product dao.
 */
@Dao
public interface OrderProductDao {
  /**
   * Gets all order products.
   *
   * @return the all order products
   */
  @Query("SELECT * FROM TB_ORDER_PRODUCT")
  List<OrderProduct> getAllOrderProducts();

  /**
   * Insert order products.
   *
   * @param orderProducts the order products
   */
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertOrderProduct(OrderProduct... orderProducts);

  /**
   * Update order product.
   *
   * @param orderProduct the order product
   * @return the long
   */
  @Update(onConflict = OnConflictStrategy.REPLACE)
  int updateOrderProduct(OrderProduct... orderProduct);

  /**
   * Delete order product long.
   *
   * @param orderProduct the order product
   */
  @Delete
  void deleteOrderProduct(OrderProduct... orderProduct);

  /**
   * Delete all order products long.
   */
  @Query("DELETE FROM TB_ORDER_PRODUCT")
  void deleteAllOrderProducts();

  /**
   * Number of OrderProducts in the system
   *
   * @return the number of OrderProducts in the system
   */
  @Query("SELECT COUNT(*) FROM tb_order_product")
  long orderProductCount();
}
