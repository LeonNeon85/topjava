package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
            meal.setUser(em.getReference(User.class, userId)); // !!!!!!!!
            em.persist(meal);
            return meal;
        } else {
            if (em.createNamedQuery("Meal.UPDATE").
                    setParameter("dateTime", meal.getDateTime()).
                    setParameter("description", meal.getDescription()).
                    setParameter("calories", meal.getCalories()).
                    setParameter("id", meal.getId()).
                    setParameter("userId", userId).executeUpdate() == 0) {
                return null;
            }
            return meal;
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery("Meal.DELETE").setParameter("id", id).setParameter("user_id" , userId).executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        try {
            return em.createNamedQuery("Meal.FIND", Meal.class).setParameter("id", id).setParameter("userId", userId).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery("Meal.ALL_SORTED", Meal.class).setParameter("user_id" , userId).getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery("Meal.BEETWENE", Meal.class).
                setParameter("user_id", userId).
                setParameter("startDate", startDate).
                setParameter("endDate", endDate).
                getResultList();
    }
}