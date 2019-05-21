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

package io.blongho.github.greendao.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.DaoException;

/**
 * The type Product<br>
 * A product has a name and a description
 *
 * @author Bernard Che Longho
 * @version 1.0
 * @see http://greenrobot.org/greendao/documentation/modelling-entities/
 * @since 2019-05-21
 */
@Entity(
    nameInDb = "tb_products",
    active = true,
    indexes = {@Index(value = "id DESC", unique = true)}
)
public class Product {
  @Id
  @Property(nameInDb = "product_id")
  private long id;
  @Property(nameInDb = "product_name")
  @NotNull
  private String name;
  @Property(nameInDb = "product_description")
  private String description;
  @ToMany
  @JoinEntity(
      entity = OrderProduct.class,
      sourceProperty = "product",
      targetProperty = "order"
  )
  private List<Order> ordersWithThisProduct;
  /** Used to resolve relations */
  @Generated(hash = 2040040024)
  private transient DaoSession daoSession;
  /** Used for active entity operations. */
  @Generated(hash = 694336451)
  private transient ProductDao myDao;

  /**
   * Instantiates a new Product.
   */
  public Product() {
  }

  @Generated(hash = 517059296)
  public Product(long id, @NotNull String name, String description) {
      this.id = id;
      this.name = name;
      this.description = description;
  }

  /**
   * To-many relationship, resolved on first access (and after reset).
   * Changes to to-many relations are not persisted, make changes to the target entity.
   */
  @Generated(hash = 1365647064)
  public List<Order> getOrdersWithThisProduct() {
      if (ordersWithThisProduct == null) {
          final DaoSession daoSession = this.daoSession;
          if (daoSession == null) {
              throw new DaoException("Entity is detached from DAO context");
          }
          OrderDao targetDao = daoSession.getOrderDao();
          List<Order> ordersWithThisProductNew = targetDao
                  ._queryProduct_OrdersWithThisProduct(id);
          synchronized (this) {
              if (ordersWithThisProduct == null) {
                  ordersWithThisProduct = ordersWithThisProductNew;
              }
          }
      }
      return ordersWithThisProduct;
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

  /** Resets a to-many relationship, making the next get call to query for a fresh result. */
  @Generated(hash = 1032865744)
  public synchronized void resetOrdersWithThisProduct() {
      ordersWithThisProduct = null;
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
@Generated(hash = 1171535257)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getProductDao() : null;
}
}
