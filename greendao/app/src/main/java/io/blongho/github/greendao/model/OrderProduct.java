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

package io.blongho.github.greendao.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.DaoException;

/**
 * The type Order product.
 * <p>For annotations, check the documentation</p>
 *
 * @see http://greenrobot.org/greendao/documentation/modelling-entities/
 * <p>For modelling relationships, see the documentation</p>
 * @see http://greenrobot.org/greendao/documentation/relations/
 */
@Entity(
    nameInDb = "tb_order_product",
    indexes = {@Index(value = "id DESC", unique = true)},
    active = true
)
public class OrderProduct {
  private long id;
  private long order;
  private long product;
  /** Used to resolve relations */
  @Generated(hash = 2040040024)
  private transient DaoSession daoSession;
  /** Used for active entity operations. */
  @Generated(hash = 884699745)
  private transient OrderProductDao myDao;

  /**
   * Instantiates a new Order product.
   */
  public OrderProduct() {
  }

  @Generated(hash = 174718089)
  public OrderProduct(long id, long order, long product) {
      this.id = id;
      this.order = order;
      this.product = product;
  }

  /**
   * Gets order product id.
   *
   * @return the order product id
   */
  public long getId() {
    return id;
  }

  /**
   * Sets order product id.
   *
   * @param id the order product id
   */
  public void setId(final long id) {
    this.id = id;
  }

  /**
   * Gets order.
   *
   * @return the order
   */
  public long getOrder() {
    return order;
  }

  /**
   * Sets order.
   *
   * @param order the order
   */
  public void setOrder(long order) {
    this.order = order;
  }

  /**
   * Gets product.
   *
   * @return the product
   */
  public long getProduct() {
    return product;
  }

  /**
   * Sets product.
   *
   * @param product the product
   */
  public void setProduct(long product) {
    this.product = product;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("OrderProduct{");
    sb.append("id=").append(id);
    sb.append(", order=").append(order);
    sb.append(", product=").append(product);
    sb.append('}');
    return sb.toString();
  }

  /**
   * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
   * Entity must attached to an entity context.
   */
  @Generated(hash = 128553479)
  public void delete() {
      if (myDao == null) {
          throw new DaoException("Entity is detached from DAO context");
      }
      myDao.delete(this);
  }

  /**
   * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
   * Entity must attached to an entity context.
   */
  @Generated(hash = 1942392019)
  public void refresh() {
      if (myDao == null) {
          throw new DaoException("Entity is detached from DAO context");
      }
      myDao.refresh(this);
  }

  /**
   * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
   * Entity must attached to an entity context.
   */
  @Generated(hash = 713229351)
  public void update() {
      if (myDao == null) {
          throw new DaoException("Entity is detached from DAO context");
      }
      myDao.update(this);
  }

  /** called by internal mechanisms, do not call yourself. */
@Generated(hash = 1412736234)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getOrderProductDao() : null;
}
}
