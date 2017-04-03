package ru.javawebinar.topjava.model;

import java.util.List;

/**
 * Created by Kpks on 29.03.2017.
 */
public interface MealDaoInterfase {
    void addMeal(Meal meal);
    void deleteMeal(int mealId);
    void updateMeal(int mealId, Meal newMeal);
    List<Meal> getAllMeals();
    Meal getMealById(int mealId);
}
