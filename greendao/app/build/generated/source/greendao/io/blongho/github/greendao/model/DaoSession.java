package io.blongho.github.greendao.model;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import io.blongho.github.greendao.model.Customer;
import io.blongho.github.greendao.model.Order;
import io.blongho.github.greendao.model.OrderProduct;
import io.blongho.github.greendao.model.Product;

import io.blongho.github.greendao.model.CustomerDao;
import io.blongho.github.greendao.model.OrderDao;
import io.blongho.github.greendao.model.OrderProductDao;
import io.blongho.github.greendao.model.ProductDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig customerDaoConfig;
    private final DaoConfig orderDaoConfig;
    private final DaoConfig orderProductDaoConfig;
    private final DaoConfig productDaoConfig;

    private final CustomerDao customerDao;
    private final OrderDao orderDao;
    private final OrderProductDao orderProductDao;
    private final ProductDao productDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        customerDaoConfig = daoConfigMap.get(CustomerDao.class).clone();
        customerDaoConfig.initIdentityScope(type);

        orderDaoConfig = daoConfigMap.get(OrderDao.class).clone();
        orderDaoConfig.initIdentityScope(type);

        orderProductDaoConfig = daoConfigMap.get(OrderProductDao.class).clone();
        orderProductDaoConfig.initIdentityScope(type);

        productDaoConfig = daoConfigMap.get(ProductDao.class).clone();
        productDaoConfig.initIdentityScope(type);

        customerDao = new CustomerDao(customerDaoConfig, this);
        orderDao = new OrderDao(orderDaoConfig, this);
        orderProductDao = new OrderProductDao(orderProductDaoConfig, this);
        productDao = new ProductDao(productDaoConfig, this);

        registerDao(Customer.class, customerDao);
        registerDao(Order.class, orderDao);
        registerDao(OrderProduct.class, orderProductDao);
        registerDao(Product.class, productDao);
    }
    
    public void clear() {
        customerDaoConfig.clearIdentityScope();
        orderDaoConfig.clearIdentityScope();
        orderProductDaoConfig.clearIdentityScope();
        productDaoConfig.clearIdentityScope();
    }

    public CustomerDao getCustomerDao() {
        return customerDao;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public OrderProductDao getOrderProductDao() {
        return orderProductDao;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

}