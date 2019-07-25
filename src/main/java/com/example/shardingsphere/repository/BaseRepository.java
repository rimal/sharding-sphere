package com.example.shardingsphere.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.Serializable;

/**
 * @author Rimal
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

  TypedQuery<T> createQuery(String queryStr, Class<T> resultClass);

  Query createNativeQuery(String queryStr);

  Query createNativeQuery(String queryStr, Class resultClass);

  T getSingleResult(TypedQuery<T> query);

  void create(T entity);

  T update(T entity);

  @Override
  void delete(T entity);

//  <R> R runWithClientFilterDisabled(Supplier<R> supplier);

  /*@Override
  @Deprecated
  default T getOne(ID id) {
    throw new SpdUnsupportedException("This method is no longer supported");
  }
*/
  T findByHash(String value);

  T getById(ID id);
}
