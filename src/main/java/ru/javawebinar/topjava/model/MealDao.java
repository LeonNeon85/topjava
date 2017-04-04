package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by Kpks on 29.03.2017.
 */
public class MealDao implements MealDaoInterfase {
    private  List<Meal> meals = Collections.synchronizedList(new ArrayList<Meal>());

    public MealDao() {
        addDataToList();
    }

    @Override
    public void addMeal(Meal meal) {
        meals.add(meal);
    }

    @Override
    public void deleteMeal(int mealId) {
        meals.remove(getMealById(mealId));
    }

    @Override
    public void updateMeal(int mealId, Meal newMeal) {
        Meal mealForChange = getMealById(mealId);
        mealForChange.setCalories(newMeal.getCalories());
        mealForChange.setDescription(newMeal.getDescription());
    }

    @Override
    public List<Meal> getAllMeals() {
        return meals;
    }

    @Override
    public Meal getMealById(int mealId) {
        Optional<Meal> searchMeal = meals.stream().filter((meal -> meal.getId() == mealId)).findFirst();
        return searchMeal.get();
    }

    private void addDataToList() {
            meals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
            meals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
            meals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
            meals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
            meals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
            meals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
            meals.add(new Meal(LocalDateTime.of(2015, Month.JANUARY, 7, 8, 0), "Завтрак", 1900));
            meals.add(new Meal(LocalDateTime.of(2015, Month.JANUARY, 7, 13, 0), "Обед", 2500));
            meals.add(new Meal(LocalDateTime.of(2013, Month.OCTOBER, 15, 7, 0), "Завтрак", 1000));
            meals.add(new Meal(LocalDateTime.of(2013, Month.OCTOBER, 15, 18, 0), "Ужин", 500));
            meals.add(new Meal(LocalDateTime.of(2011, Month.FEBRUARY, 7, 21, 0), "Ужин", 3000));
    }
}
