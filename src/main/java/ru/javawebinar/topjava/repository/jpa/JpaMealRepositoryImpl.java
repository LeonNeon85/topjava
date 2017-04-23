package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else {
            return em.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE).setParameter("id", id).setParameter("user_id" , userId).executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return em.find(Meal.class, id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        System.out.println(userId);
        List<Meal> res1 = em.createNamedQuery(Meal.ALL_SORTED, Meal.class).setParameter("user_id" , userId).getResultList();
        List<Meal> res2 = em.createQuery("SELECT m FROM Meal m WHERE m.id=:user_id ORDER BY m.dateTime desc").setParameter("user_id" , userId).getResultList();
        System.out.println();
        return res1;
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(Meal.BEETWENE, Meal.class).
                setParameter("user_id", userId).
                setParameter("startDate", startDate).
                setParameter("endDate", endDate).
                getResultList();
    }
}