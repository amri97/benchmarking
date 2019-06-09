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

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import java.util.List;

/**
 * The type Order<br>
 * An order holds an identifier to the customer and the list of products ordered.
 *
 * @author Bernard Che Longho
 * @version 1.0
 * @see http://greenrobot.org/greendao/documentation/modelling-entities/ <p>For modelling relationships, see the documentation</p>
 * @see http://greenrobot.org/greendao/documentation/relations/
 * @since 2019 -05-21 <p>For annotations, check the documentation</p>
 */
@Entity(
    nameInDb = "tb_order",
    active = true,
    indexes = {@Index(value = "id, customer DESC", unique = true)}
)
public class Order {
  @Id
  @Property(nameInDb = "order_id")
  private long id;
  @Property(nameInDb = "customer_id")
  @NotNull
  private long customer;
  @Property(nameInDb = "order_date")
  private Date date;

  @ToMany
  @JoinEntity(
      entity = OrderProduct.class,
      sourceProperty = "order",
      targetProperty = "product"
  )
  private List<Product> products;

  @ToOne(joinProperty = "customer")
  private Customer orderCustomer;
  /**
   * Used to resolve relations
   */
  @Generated(hash = 2040040024)
  private transient DaoSession daoSession;
  /**
   * Used for active entity operations.
   */
  @Generated(hash = 949219203)
  private transient OrderDao myDao;
  @Generated(hash = 248710861)
  private transient Long orderCustomer__resolvedKey;

  /**
   * Instantiates a new Order.
   */
  public Order() {
  }

  @Generated(hash = 60304869)
  public Order(long id, long customer, Date date) {
    this.id = id;
    this.customer = customer;
    this.date = date;
  }

  /**
   * Instantiates a new Order.
   *
   * @param id       the id
   * @param customer the customer
   */
  public Order(long id, long customer) {
    this.id = id;
    this.customer = customer;
    date = new Date();
  }

  /**
   * Gets order id.
   *
   * @return the order id
   */
  public long getId() {
    return id;
  }

  /**
   * Sets order id.
   *
   * @param id the order id
   */
  public void setId(final long id) {
    this.id = id;
  }

  /**
   * To-many relationship, resolved on first access (and after reset).
   * Changes to to-many relations are not persisted, make changes to the target entity.
   */
  @Generated(hash = 516355143)
  public List<Product> getProducts() {
    if (products == null) {
      final DaoSession daoSession = this.daoSession;
      if (daoSession == null) {
        throw new DaoException("Entity is detached from DAO context");
      }
      ProductDao targetDao = daoSession.getProductDao();
      List<Product> productsNew = targetDao._queryOrder_Products(id);
      synchronized (this) {
        if (products == null) {
          products = productsNew;
        }
      }
    }
    return products;
  }

  /**
   * Sets products.
   *
   * @param products the products
   */
  public void setProducts(final List<Product> products) {
    this.products = products;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("Order{");
    sb.append("id=").append(id);
    sb.append(", customer=").append(customer);
    sb.append(", date=").append(date);
    sb.append('}');
    return sb.toString();
  }

  /**
   * Gets date.
   *
   * @return the date
   */
  public Date getDate() {
    return date;
  }

  /**
   * Sets date.
   *
   * @param date the date
   */
  public void setDate(final Date date) {
    this.date = date;
  }

  /**
   * Gets customer.
   *
   * @return the customer
   */
  public long getCustomer() {
    return this.customer;
  }

  /**
   * Sets customer.
   *
   * @param customer the customer
   */
  public void setCustomer(long customer) {
    this.customer = customer;
  }

  /**
   * To-one relationship, resolved on first access.
   */
  @Generated(hash = 1525901129)
  public Customer getOrderCustomer() {
    long __key = this.customer;
    if (orderCustomer__resolvedKey == null || !orderCustomer__resolvedKey.equals(__key)) {
      final DaoSession daoSession = this.daoSession;
      if (daoSession == null) {
        throw new DaoException("Entity is detached from DAO context");
      }
      CustomerDao targetDao = daoSession.getCustomerDao();
      Customer orderCustomerNew = targetDao.load(__key);
      synchronized (this) {
        orderCustomer = orderCustomerNew;
        orderCustomer__resolvedKey = __key;
      }
    }
    return orderCustomer;
  }

  /**
   * called by internal mechanisms, do not call yourself.
   */
  @Generated(hash = 1826891727)
  public void setOrderCustomer(@NotNull Customer orderCustomer) {
    if (orderCustomer == null) {
      throw new DaoException("To-one property 'customer' has not-null constraint; cannot set to-one to null");
    }
    synchronized (this) {
      this.orderCustomer = orderCustomer;
      customer = orderCustomer.getId();
      orderCustomer__resolvedKey = customer;
    }
  }

  /**
   * Resets a to-many relationship, making the next get call to query for a fresh result.
   */
  @Generated(hash = 513498032)
  public synchronized void resetProducts() {
    products = null;
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

  /**
   * called by internal mechanisms, do not call yourself.
   */
  @Generated(hash = 965731666)
  public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getOrderDao() : null;
  }
}
