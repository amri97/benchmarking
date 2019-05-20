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

package io.blongho.github.room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.blongho.github.room.asynctasks.AsyncAddCustomer;
import io.blongho.github.room.asynctasks.AsyncAddOrder;
import io.blongho.github.room.asynctasks.AsyncAddOrderProduct;
import io.blongho.github.room.asynctasks.AsyncAddProduct;
import io.blongho.github.room.asynctasks.AsyncReadCustomer;
import io.blongho.github.room.asynctasks.AsyncReadOrderProduct;
import io.blongho.github.room.asynctasks.AsyncReadOrders;
import io.blongho.github.room.asynctasks.AsyncReadProduct;
import io.blongho.github.room.database.AppDatabaseRepository;
import io.blongho.github.room.model.Customer;
import io.blongho.github.room.model.Order;
import io.blongho.github.room.model.OrderProduct;
import io.blongho.github.room.model.Product;
import io.blongho.github.room.util.AssetsReader;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {
	private static final String TAG = "MainActivity";
	private AppDatabaseRepository repository;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	/**
	 * Create db.
	 *
	 * @param view the view
	 */
	public void createDb(View view) {
		initializeRepository();
		showSnackBar(view, "createDb");

	}

	private void initializeRepository() {
		if (repository == null) {
			repository = new AppDatabaseRepository(getApplicationContext());
		}
	}

	private void showSnackBar(View view, final String method) {
		Snackbar.make(view, method + " ==> Implement this method", Snackbar.LENGTH_SHORT).show();
	}

	/**
	 * Clear db.
	 *
	 * @param view the view
	 */
	public void clearDb(View view) {
		initializeRepository();
		repository.deleteAll();
		showSnackBar(view, "clearDb");
	}

	/**
	 * Delete from db.
	 *
	 * @param view the view
	 */
	public void deleteFromDb(View view) {
		// TODO implement code for deleting an item or items from the database
		showSnackBar(view, "deleteFromDb");
	}

	/**
	 * Update entry.
	 *
	 * @param view the view
	 */
	public void updateEntry(View view) {
		// TODO implement code for updating an entry or entries in the database
		showSnackBar(view, "updateEntry");
	}

	/**
	 * Read data.
	 *
	 * @param view the view
	 */
	public void readData(View view) {
		List<Customer>     customers     = new ArrayList<>();
		List<Product>      products      = new ArrayList<>();
		List<Order>        orders        = new ArrayList<>();
		List<OrderProduct> orderProducts = new ArrayList<>();
		initializeRepository();
		try {
			customers = new AsyncReadCustomer(repository).execute().get();
			products = new AsyncReadProduct(repository).execute().get();
			orders = new AsyncReadOrders(repository).execute().get();
			orderProducts = new AsyncReadOrderProduct(repository).execute().get();
			/*for (final Customer customer : customers) {
				Log.e(TAG, "readData: " + customer);
			}*/

			/*showItems(customers);
			showItems(products);
			showItems(orders);*/
			showItems(orderProducts);

		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// TODO implement logic for reading some data from the database
		showSnackBar(view, "readData");
	}

	private <T> void showItems(List<T> items) {
		for (final T item : items) {
			Log.i(TAG, "showItems: " + item);
		}
	}

	/**
	 * Load data.
	 *
	 * @param view the view
	 */
	public void loadData(View view) {
		Gson gson = new Gson();
		Customer[] customers = gson
		  .fromJson(AssetsReader.readFromAssets(getApplicationContext(), R.raw.customer), Customer[].class);
		Product[] products = gson
		  .fromJson(AssetsReader.readFromAssets(getApplicationContext(), R.raw.products), Product[].class);
		Order[] orders = gson
		  .fromJson(AssetsReader.readFromAssets(getApplicationContext(), R.raw.orders), Order[].class);
		OrderProduct[] orderProducts = gson
		  .fromJson(AssetsReader.readFromAssets(getApplicationContext(), R.raw.order_products), OrderProduct[].class);
		for (final OrderProduct orderProduct : orderProducts) {
			Log.e(TAG, "loadData: " + orderProduct );
		}
		initializeRepository();
		new AsyncAddCustomer(repository).execute(customers);
		new AsyncAddProduct(repository).execute(products);
		new AsyncAddOrder(repository).execute(orders);
		new AsyncAddOrderProduct(repository).execute(orderProducts);
		showSnackBar(view, "loadData");
	}
}
