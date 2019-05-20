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

package io.blongho.github.room.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import io.blongho.github.room.Database.dao.CustomerDao;
import io.blongho.github.room.Database.dao.OrderDao;
import io.blongho.github.room.Database.dao.OrderProductDao;
import io.blongho.github.room.Database.dao.ProductDao;
import io.blongho.github.room.model.Customer;
import io.blongho.github.room.model.Order;
import io.blongho.github.room.model.OrderProduct;
import io.blongho.github.room.model.Product;

@Database (entities = {Customer.class, Product.class, Order.class, OrderProduct.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
	private final static String databaseName = "customer_order_room";

	private static AppDatabase instance;

	static AppDatabase getInstance(final Context context) {
		if (instance == null) {
			synchronized (AppDatabase.class) {
				if (instance == null) {
					instance = Room.databaseBuilder(context, AppDatabase.class, databaseName).build();
				}
			}
		}
		return instance;
	}

	public static void destroyInstance() {
		instance = null;
	}

	abstract CustomerDao customerDao();

	abstract ProductDao productDao();

	abstract OrderDao orderDao();

	abstract OrderProductDao orderProductDao();

}
