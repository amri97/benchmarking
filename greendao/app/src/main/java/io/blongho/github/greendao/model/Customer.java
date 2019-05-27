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
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.DaoException;

/**
 * A Customer object.<br>
 * A Customer object has customer details and the orders done by this customer
 *
 * @author Bernard Che Longho
 * @version 1.0
 * @see http://greenrobot.org/greendao/documentation/modelling-entities/
 * @since 2019-05-21
 * <p>For annotations, check the documentation</p>
 */
@Entity(
    nameInDb = "tb_customer",
    active = true,
    indexes = {@Index(value = "id DESC", unique = true)}
)
public class Customer {
  @Id
  @Property(nameInDb = "customer_id")
  private Long id;
  @Property(nameInDb = "customer_name")
  private String name;
  @Property(nameInDb = "customer_city")
  private String city;

  @ToMany(referencedJoinProperty = "customer")
  @OrderBy("id ASC")
  private List<Order> orders;
  /** Used to resolve relations */
  @Generated(hash = 2040040024)
  private transient DaoSession daoSession;
  /** Used for active entity operations. */
  @Generated(hash = 1697251196)
  private transient CustomerDao myDao;

  /**
   * Instantiates a new Customer.
   */
  public Customer() {
  }

  @Generated(hash = 1894139701)
  public Customer(Long id, String name, String city) {
      this.id = id;
      this.name = name;
      this.city = city;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  /**
   * Gets customer id.
   *
   * @return the customer id
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets customer id.
   *
   * @param id the customer id
   */
  public void setId(final Long id) {
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
   * To-many relationship, resolved on first access (and after reset).
   * Changes to to-many relations are not persisted, make changes to the target entity.
   */
  @Generated(hash = 1084217201)
  public List<Order> getOrders() {
      if (orders == null) {
          final DaoSession daoSession = this.daoSession;
          if (daoSession == null) {
              throw new DaoException("Entity is detached from DAO context");
          }
          OrderDao targetDao = daoSession.getOrderDao();
          List<Order> ordersNew = targetDao._queryCustomer_Orders(id);
          synchronized (this) {
              if (orders == null) {
                  orders = ordersNew;
              }
          }
      }
      return orders;
  }

  /**
   * Sets orders.
   *
   * @param orders the orders
   */
  public void setOrders(final List<Order> orders) {
    this.orders = orders;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("Customer{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", city=").append(city);
    sb.append('}');
    return sb.toString();
  }

  /** Resets a to-many relationship, making the next get call to query for a fresh result. */
  @Generated(hash = 1446109810)
  public synchronized void resetOrders() {
      orders = null;
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
@Generated(hash = 462117449)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getCustomerDao() : null;
}

}
