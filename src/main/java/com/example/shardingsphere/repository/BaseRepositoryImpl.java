package com.example.shardingsphere.repository;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.Serializable;

/**
 * @author Rimal
 */
@NoRepositoryBean
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

  private static final Logger logger = LoggerFactory.getLogger(BaseRepositoryImpl.class);

  protected final EntityManager entityManager;


  public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation,
                            EntityManager entityManager) {
    super(entityInformation, entityManager);
    this.entityManager = entityManager;
  }

  /*private T clientFilterCheck(T t) {
    if (null != t && BotRequestContextHolder.getEnableFilterFlag()) {
      //if domain has client filter applied
      //do an if check manually
      if (ClassJvmCache.getInstance().doesDomainHaveClientFilter(t.getClass().getCanonicalName())) {
        try {
          if (BaseUtils.areNotEqual(PropertyUtils.getProperty(t, "clientId"), BotRequestContextHolder.getClientId())) {
            logger.info("Client id on " + t.getClass() + " not matching with client in context. Returning null");
            t = null;
          }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
          logger.error("Error encountered while matching client id", e);
        }
      }
    }
    return t;
  }*/

  @Override
  public T findByHash(String value) {
    T t = getSession().bySimpleNaturalId(this.getDomainClass()).load(value);
//    t = clientFilterCheck(t);
    return t;
  }

  /*@Override
  public T getById(ID id) {
    T t = super.findById(id).orElse(null);
//    t = clientFilterCheck(t);
    return t;
  }*/

  /*@Override
  public T findOne(ID id) {
    T t = super.findById(id).orElse(null);
    t = clientFilterCheck(t);
    return t;
  }*/

  @Override
  public TypedQuery<T> createQuery(String queryStr, Class<T> resultClass) {
    return entityManager.createQuery(queryStr, resultClass);
  }

  @Override
  public Query createNativeQuery(String queryStr) {
    return entityManager.createNativeQuery(queryStr);
  }

  @Override
  public Query createNativeQuery(String queryStr, Class resultClass) {
    return entityManager.createNativeQuery(queryStr, resultClass);
  }

  @Override
  public T getSingleResult(TypedQuery<T> query) {
    try {
      return query.getSingleResult();
    } catch (javax.persistence.NoResultException e) {
      return null;
    }
  }

  @Override
  public void create(T entity) {
    entityManager.persist(entity);
  }

  @Override
  public T update(T entity) {
    return entityManager.merge(entity);
  }

  @Override
  public void delete(T entity) {
    entityManager.remove(entity);
  }

  /*@Override
  @Transactional(readOnly = true)
  public <R> R runWithClientFilterDisabled(Supplier<R> supplier) {
    Assert.notNull(supplier, "Supplier cannot be null");

    boolean filterTurnedOff = disableClientFilterIfNeeded();

    R r = supplier.get();

    if (filterTurnedOff) {
      enableClientFilter();
    }

    return r;
  }*/


  /**
   * @return returns true if the filter is disabled.
   * Based on this value, we can later call the {@link #enableClientFilter()} method once we are done with our use case.
   */
  /*private boolean disableClientFilterIfNeeded() {
    boolean turnOffFilter = BotRequestContextHolder.getEnableFilterFlag() || isFilterEnabled(AppConstants.CLIENT_FILTER);
    if (turnOffFilter) {
      BotRequestContextHolder.setEnableFilterFlag(false);
      Session session = getSession();
      if (null != session) {
        session.disableFilter(AppConstants.CLIENT_FILTER);
      }
    }

    return turnOffFilter;
  }

  private void enableClientFilter() {
    BotRequestContextHolder.setEnableFilterFlag(true);
    Session session = getSession();
    if (null != session && null != BotRequestContextHolder.getClientId()) {
      Filter filter = session.enableFilter(AppConstants.CLIENT_FILTER);
      filter.setParameter(AppConstants.CLIENT_FILTER_CLIENT_ID_PARAM, BotRequestContextHolder.getClientId());
    }
  }*/
  private boolean isFilterEnabled(String filterName) {
    Session session = getSession();
    return null != session && null != session.getEnabledFilter(filterName);
  }

  private Session getSession() {
    return entityManager.unwrap(Session.class);
  }
}