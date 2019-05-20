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
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {
	private static final String TAG = "MainActivity";

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
		// TODO implement code for creating the database here
		showSnackBar(view, "createDb");

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
		// TODO implement code for clearing the database
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
		// TODO implement logic for reading some data from the database
		showSnackBar(view, "readData");
	}

	/**
	 * Load data.
	 *
	 * @param view the view
	 */
	public void loadData(View view) {
		// TODO logic for loading the database with data
		showSnackBar(view, "loadData");
	}
}
