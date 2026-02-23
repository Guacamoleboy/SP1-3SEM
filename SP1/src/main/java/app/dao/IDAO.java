package app.dao;

import java.util.List;

public interface IDAO <T> {

    // <T> declares the type for the entire interface.
    // T is then used as the entity type for all methods
    //
    // <R> unlike <T> is on method-method base. Meaning it's a type for the method. Being declared.
    // R is then the variable we can use and return for the specific method.
    // Without <R> we can't use R as it's not declared. <R> doesn't have to be <R>. It can be anything.

    // Attributes
    T create(T entity);
    T update(T entity);
    T getById(Object id);
    <R> R getColumnById(Object id, String column);
    <R> R updateColumnById(Object id, String column, Object value);
    boolean existByColumn(Object o, String column);
    T findEntityByColumn(Object value, String column);
    List<T> getAll();
    T delete(T entity);
    T deleteById(Object id);
    void deleteAll();

}