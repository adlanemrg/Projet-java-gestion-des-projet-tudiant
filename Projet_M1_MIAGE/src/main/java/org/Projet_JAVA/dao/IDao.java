package org.Projet_JAVA.dao;

import java.util.List;

/**
 *
 * @author lachgar
 */
public interface IDao<T> {

    boolean create(T o);

    boolean delete(T o);

    boolean update(T o);

    List<T> findAll();

    T findById(int id);

}
