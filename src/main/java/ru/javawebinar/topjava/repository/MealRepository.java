package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
public interface MealRepository {

    Meal save(Meal meal);

    boolean delete(int id);

    Meal get(int id);

    Collection<Meal> getAll();

    ////

    Meal save(Integer userId, Meal meal);

    boolean delete(Integer userId, Integer mealId) throws NotFoundException;

    Collection<Meal> getAll(Integer userId);

    Collection<Meal> getFiltered(Integer userId, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime);

//    void update(Integer userId, Meal userMeal);

}
