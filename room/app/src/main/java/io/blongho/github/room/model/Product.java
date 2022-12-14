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

package io.blongho.github.room.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * The type Product<br> A product has a name and a description
 *
 * @author Bernard Che Longho
 * @version 1.0
 * @since 2019-05-10
 */
@Entity(tableName = "tb_product",
    indices = {@Index(value = {"product_id"})}
)
public class Product {
  @PrimaryKey
  @ColumnInfo(name = "product_id")
  private long id;
  @ColumnInfo(name = "product_name")
  private String name;
  @ColumnInfo(name = "product_desc")
  private String description;

  /**
   * Instantiates a new Product.
   */
  public Product() {
  }

  /**
   * Instantiates a new Product.
   *
   * @param product_id  the product id
   * @param name        the name
   * @param description the description
   */
  @Ignore
  public Product(@NonNull final long product_id, @NonNull final String name, @Nullable final String description) {
    this.id = product_id;
    this.name = name;
    this.description = description;
  }

  /**
   * Gets product id.
   *
   * @return the product id
   */
  public long getId() {
    return id;
  }

  /**
   * Sets product id.
   *
   * @param id the product id
   */
  public void setId(final long id) {
    this.id = id;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * Gets description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets description.
   *
   * @param description the description
   */
  public void setDescription(final String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("Product{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", description='").append(description).append('\'');
    sb.append('}');
    return sb.toString();
  }

}
