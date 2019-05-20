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

package io.blongho.github.room.asynctasks;

import android.os.AsyncTask;

import io.blongho.github.room.database.AppDatabaseRepository;

public abstract class AsyncInsert<T> extends AsyncTask<T[], Void, Void> {
	private static final String TAG = "AsyncInsert";
	protected AppDatabaseRepository repository;

	public AsyncInsert(final AppDatabaseRepository repository) {
		this.repository = repository;
	}


	/*
	private <T> void callRightMethod(T... items) {
		final Customer     customer     = new Customer();
		final Product      product      = new Product();
		final Order        order        = new Order();
		final OrderProduct orderProduct = new OrderProduct();

		if (items.getClass().isInstance(customer)) {
			Log.e(TAG, "callRightMethod: customer::");
			for (final T item : items) {
				repository.addCustomer((Customer) item);
			}
		}
		else if (items.getClass().isInstance(product)) {
			repository.addProduct((Product[]) items);
		}
		else if (items.getClass().isInstance(order)) {
			repository.addOrder((Order[]) items);
		}
		else if (items.getClass().isInstance(orderProduct)) {
			repository.insertOrderProduct((OrderProduct[]) items);
		}
		else Log.e(TAG, "callRightMethod: " + "Bullshit");
	}
	*/
}
