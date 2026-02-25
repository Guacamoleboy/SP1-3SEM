package app.dao;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class EntityManagerDAO<T> implements IDAO<T> {

    // DELETE, UPDATE. All CRUD methods that are equal in every DAO.
    // Instead of using update, delete etc in every DAO simply use the EntityManagerDAO CRUD setup.

    // Attributes
    protected EntityManager em;
    protected Class<T> classSpecific;

    // ________________________________________________________

    protected EntityManagerDAO(EntityManager em, Class<T> entityClass){
        this.em = em;
        this.classSpecific = entityClass;
    }

    // ________________________________________________________

    @Override
    public T create(T t) {
        return executeQuery(() -> em.merge(t));
    }

    // ________________________________________________________

    @Override
    public T update(T t) {
        return executeQuery(() -> em.merge(t));
    }

    // ________________________________________________________

    @Override
    public T delete(T t) {
        return executeQuery(() -> {
            em.remove(t);
            return t;
        });
    }

    // ________________________________________________________

    @Override
    public T deleteById(Object id) {
        return executeQuery(() -> {
            T t = findById(id);
            if (t != null) {
                em.remove(t);
            }
            return t;
        });
    }

    // ________________________________________________________

    @Override
    public T getById(Object id) {
        return executeQuery(() -> findById(id));
    }

    // ________________________________________________________

    @Override
    public <R> R getColumnById(Object id, String column) {
        return executeQuery(() -> {
            String JPQL = "SELECT x." + column + " FROM " + classSpecific.getSimpleName() + " x WHERE x.id = :id";
            return (R) em.createQuery(JPQL, Object.class)
                    .setParameter("id", id)
                    .getSingleResult();
        });
    }

    // ________________________________________________________
    // TODO: Refactor to Long / Integer, as we return affected rows no matter the input

    @Override
    public <R> R updateColumnById(Object id, String column, Object value) {
        return executeQuery(() -> {
            String JPQL = "UPDATE " + classSpecific.getSimpleName() + " x SET x." + column + " = :value WHERE x.id = :id";
            int updatedRows = em.createQuery(JPQL)
                    .setParameter("value", value)
                    .setParameter("id", id)
                    .executeUpdate();
            return (R) Integer.valueOf(updatedRows);
        });
    }

    // ________________________________________________________

    @Override
    public boolean existByColumn(Object value, String column) {
        String JPQL = "SELECT COUNT(x) FROM " + classSpecific.getSimpleName() + " x WHERE x." + column + " = :value";
        Long count = executeQuery(() -> em.createQuery(JPQL, Long.class)
                .setParameter("value", value)
                .getSingleResult());
        return count != null && count > 0;
    }

    // ________________________________________________________

    @Override
    public T findEntityByColumn(Object value, String column) {
        return executeQuery(() -> {
            String JPQL = "SELECT x FROM " + classSpecific.getSimpleName() + " x WHERE x." + column + " = :value";
            List<T> results = em.createQuery(JPQL, classSpecific)
                    .setParameter("value", value)
                    .getResultList();
            return results.isEmpty() ? null : results.get(0);
        });
    }

    // ________________________________________________________

    @Override
    public List<T> getAll() {
        return executeQuery(() -> {
            String JPQL = "SELECT x FROM " + classSpecific.getSimpleName() + " x";
            return em.createQuery(JPQL, classSpecific)
                    .getResultList();
        });
    }

    // ________________________________________________________
    // JPA instead of JPQL (cascade fix)

    @Override
    public void deleteAll() {
        List<T> entities = getAll();
        for (T entity : entities) {
            em.remove(entity);
        }
    }

    // ________________________________________________________
    // Unknown object type query execute using Supplier from java.util

    protected <R> R executeQuery(Supplier<R> query) {
        boolean startedTransaction = false;
        try {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
                startedTransaction = true;
            }
            R result = query.get();
            if (startedTransaction) {
                em.getTransaction().commit();
            }
            return result;
        } catch (RuntimeException e) {
            if (startedTransaction && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    // ________________________________________________________
    // void execute

    protected void executeQuery(Runnable task) {
        executeQuery(() -> {
            task.run();
            return null;
        });
    }

    // ________________________________________________________
    // Java 17 doesn't handle switch-case for instanceof

    private T findById(Object id) {
        if (id instanceof Long) {
            return em.find(classSpecific, (Long) id);
        } else if (id instanceof Integer) {
            return em.find(classSpecific, (Integer) id);
        } else if (id instanceof String) {
            return em.find(classSpecific, (String) id);
        } else if (id instanceof UUID) {
            return em.find(classSpecific, (UUID) id);
        } else {
            throw new IllegalArgumentException("Unsupported ID type: " + id.getClass());
        }
    }

}
