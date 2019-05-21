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

package io.blongho.github.greendao.constants;
/**
 * The database table columns
 */
public final class Column {
  final public static String CUSTOMER_ID = "customer_id";
  final public static String CUSTOMER_NAME = "customer_name";
  final public static String CUSTOMER_ADDR = "customer_addr";
  final public static String ORDER_ID = "order_id";
  final public static String ORDER_DATE = "order_date";
  final public static String PRODUCT_ID = "product_id";
  final public static String PRODUCT_NAME = "product_name";
  final public static String ORDER_CUSTOMER = CUSTOMER_ID;
  final public static String PRODUCT_DESC = "product_desc";
  final public static String ORDER_PRODUCT_ID = "order_product_id";
  final public static String ORDER_PRODUCT_ORDER = "op_" + ORDER_ID;
  public static final String ORDER_PRODUCT_PRODUCT = "op_" + PRODUCT_ID;
}
