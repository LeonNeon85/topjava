package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */


@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    private Map<Integer, Set<Integer>> usersMeal = new ConcurrentHashMap<>();

    {
        save(1, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        save(1, new Meal(LocalDateTime.of(2015, Month.MAY, 29, 10, 0), "Завтрак", 2500));
        save(1, new Meal(LocalDateTime.of(2015, Month.MAY, 29, 15, 0), "Обед", 500));
        save(1, new Meal(LocalDateTime.of(2015, Month.MAY, 15, 10, 0), "Завтрак", 500));
        save(1, new Meal(LocalDateTime.of(2015, Month.MAY, 15, 10, 0), "Обед", 1600));

        save(2, new Meal(LocalDateTime.of(2015, Month.MAY, 29, 10, 0), "Завтрак", 10));
        save(2, new Meal(LocalDateTime.of(2015, Month.MAY, 29, 15, 0), "Обед", 10));
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id) {
       return repository.remove(id) != null;
    }


    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values().stream().sorted(Comparator.comparing(Meal::getDateTime)).collect(Collectors.toList());
    }

    //New
    @Override
    public boolean delete(Integer userId, Integer mealId) throws NotFoundException {
        if (usersMeal.containsKey(userId) && usersMeal.get(userId).remove(mealId) ) {
            delete(mealId);
            return true;
        }
        return false;
    }

    @Override
    public Collection<Meal> getAll(Integer userId) {
        Set<Integer> usMeal;
        if (usersMeal.containsKey(userId) && !(usMeal = usersMeal.get(userId)).isEmpty() ) {
            return usMeal.stream().map((id)-> get(id)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public Collection<Meal> getFiltered(Integer userId, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        Collection<Meal> allMealForUser = getAll(userId);
        return allMealForUser.stream().filter((meal) -> DateTimeUtil.isBeetweenDateAndTime(meal.getTime(), startTime, endTime, meal.getDate(), startDate, endDate))
                                        .collect(Collectors.toList());
    }

    @Override
    public Meal save(Integer userId, Meal meal) {
        //Сохраняем в БД
        Meal newMeal = save(meal);
        if (usersMeal.containsKey(userId)) {
            usersMeal.get(userId).add(newMeal.getId());
        } else {
            Set<Integer> mealsId = new LinkedHashSet<>();
            mealsId.add(newMeal.getId());
            usersMeal.put(userId, mealsId);
        }
        return newMeal;
    }
}

