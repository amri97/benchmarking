/*
 *
 *  * MIT License
 *  *
 *  * Copyright (c) 2019 Bernard Che Longho
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in all
 *  * copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  * SOFTWARE.
 *
 */

package io.blongho.github.greendao;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import io.blongho.github.greendao.test.Test;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";
  private static boolean permissionGranted = false;
  private Test test;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    if (!permissionGranted) {
      askPermission();
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  /**
   * Create db.
   *
   * @param view the view
   */
  public void createDb(View view) {
    test.init();
  }

  /**
   * Clear db.
   *
   * @param view the view
   */
  public void clearDb(View view) {
    test.deleteAll();
  }

  /**
   * Delete from db.
   *
   * @param view the view
   */
  public void deleteFromDb(View view) {
    test.delete();
  }

  /**
   * Update entry.
   *
   * @param view the view
   */
  public void updateEntry(View view) {
    test.update();
  }

  /**
   * Read data.
   *
   * @param view the view
   */
  public void readData(View view) {
    test.read();
  }

  /**
   * Load data.
   *
   * @param view the view
   */
  public void loadData(View view) {
    test.create();
    showSnackBar(view, "loadData");
  }

  private void showSnackBar(View view, final String method) {
    Snackbar.make(view, method + " ==> Implement this method", Snackbar.LENGTH_SHORT).show();
  }

  private void askPermission() {
    ActivityCompat.requestPermissions(MainActivity.this,
        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
        1);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         String permissions[], int[] grantResults) {
    switch (requestCode) {
      case 1: {

        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          permissionGranted = true;
          test = new Test(MainActivity.this);

          // permission was granted, yay! Do the
          // contacts-related task you need to do.
        } else {

          // permission denied, boo! Disable the
          // functionality that depends on this permission.
          Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
        }
        return;
      }

      // other 'case' lines to check for other
      // permissions this app might request
    }
  }
}
