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

package io.blongho.github.greendao.util;

import android.content.Context;

import java.util.concurrent.Callable;

import androidx.annotation.RawRes;

public class ReadFromFile implements Callable<String> {
  private final Context context;
  @RawRes
  private final int filename;

  public ReadFromFile(Context context, int filename) {
    this.context = context;
    this.filename = filename;
  }

  /**
   * Computes a result, or throws an exception if unable to do so.
   *
   * @return computed result
   * @throws Exception if unable to compute a result
   */
  @Override
  public String call() throws Exception {
    return AssetsReader.readFromAssets(context, filename);
  }
}
