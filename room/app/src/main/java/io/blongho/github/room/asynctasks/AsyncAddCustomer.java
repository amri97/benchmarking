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
import android.util.Log;

import io.blongho.github.room.database.AppDatabaseRepository;
import io.blongho.github.room.model.Customer;

public class AsyncAddCustomer extends AsyncTask<Customer, Void, Void> {
	private final AppDatabaseRepository repository;
	private static final String TAG = "AsyncAddCustomer";
	public AsyncAddCustomer(final AppDatabaseRepository repository) {
		this.repository = repository;
	}

	@Override
	protected Void doInBackground(final Customer... customers) {
		repository.insertCustomer(customers);
    Log.i(TAG, "doInBackground: Customers added: " + customers.length);
		return null;
	}
}
