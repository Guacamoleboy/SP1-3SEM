package app.service;

import app.dao.EntityManagerDAO;
import java.util.List;

public class EntityManagerService<T> {

    // DELETE, UPDATE. All CRUD methods that are equal in every DAO.
    // Instead of using update, delete etc in every DAO simply use the EntityManagerDAO CRUD setup.

    // Attributes
    protected final EntityManagerDAO<T> entityManagerDAO;
    protected final Class<T> classSpecific;

    // _____________________________________________________

    public EntityManagerService(EntityManagerDAO<T> entityManagerDAO, Class<T> entityClass){
        this.entityManagerDAO = entityManagerDAO;
        this.classSpecific = entityClass;
    }

    // _________________________________________________

    public T create(T entity){
        validateNotEmpty(entity, classSpecific.getSimpleName() + ".entity");
        return entityManagerDAO.create(entity);
    }

    // _________________________________________________

    public T update(T entity){
        validateNotEmpty(entity, classSpecific.getSimpleName() + ".entity");
        return entityManagerDAO.update(entity);
    }

    // _________________________________________________

    public void deleteById(Object id){
        validateNotEmpty(id, classSpecific.getSimpleName() + ".id");
        entityManagerDAO.deleteById(id);
    }

    // _________________________________________________

    public void deleteAll(){
        entityManagerDAO.deleteAll();
    }

    // _________________________________________________

    public T getById(Object id){
        validateNotEmpty(id, classSpecific.getSimpleName() + ".id");
        return entityManagerDAO.getById(id);
    }

    // _________________________________________________

    public List<T> getAll(){
        return entityManagerDAO.getAll();
    }

    // _________________________________________________

    public boolean existByColumn(Object value, String column) {
        validateNotEmpty(value, classSpecific.getSimpleName() + ".value");
        validateNotEmpty(column, classSpecific.getSimpleName() + "." + column);
        return entityManagerDAO.existByColumn(value, column);
    }

    // _________________________________________________
    // String column is the value in the Entity class. Not the DB column.
    // For example id_ending (DB) idEnding (Entity). Use the Entity version.

    public <R> R getColumnById(Object id, String column){
        validateNotEmpty(id, classSpecific.getSimpleName() + ".id");
        validateNotEmpty(column, classSpecific.getSimpleName() + "." + column);
        return entityManagerDAO.getColumnById(id, column);
    }

    // _________________________________________________
    // Same as above. Just with value attached.

    public <R> R updateColumnById(Object id, String column, Object value){
        validateNotEmpty(id, classSpecific.getSimpleName() + ".id");
        validateNotEmpty(column, classSpecific.getSimpleName() + "." + column);
        return entityManagerDAO.updateColumnById(id, column, value);
    }

    // _________________________________________________

    public T findEntityByColumn(Object value, String column) {
        validateNotEmpty(value, "value");
        validateNotEmpty(column, classSpecific.getSimpleName() + "." + column);
        return entityManagerDAO.findEntityByColumn(value, column);
    }

    // _________________________________________________

    protected void validateNotEmpty(Object value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " må ikke være null");
        }
        if (value instanceof String text && text.isBlank()) {
            throw new IllegalArgumentException(fieldName + " kan ikke være tom");
        }
    }

    // _________________________________________________

}