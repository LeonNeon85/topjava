package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

   //  через цыклы
//    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
//
//        Map<LocalDate, Integer> dateMap = new HashMap<>();
//        for (UserMeal userMeal : mealList) {
//            LocalDate current = userMeal.getDateTime().toLocalDate();
//            // new version
//            dateMap.merge(current, userMeal.getCalories(), (x, y) -> x + y );
//
//            // old version
////            if (dateMap.containsKey(current)) {
////                dateMap.put(current, dateMap.get(current) + userMeal.getCalories());
////            } else {
////                dateMap.put(current, userMeal.getCalories());
////            }
//        }
//
//        List<UserMealWithExceed> result = new ArrayList<>();
//        for (UserMeal userMeal : mealList) {
//            LocalTime userMealLocalTime = userMeal.getDateTime().toLocalTime();
//
//            if (TimeUtil.isBetween(userMealLocalTime, startTime, endTime)) {
//                boolean currentExceed = dateMap.getOrDefault( userMeal.getDateTime().toLocalDate(), caloriesPerDay) <= caloriesPerDay;
//
//                result.add(new UserMealWithExceed(userMeal.getDateTime(),
//                        userMeal.getDescription(),
//                        userMeal.getCalories(),
//                        currentExceed));
//            }
//        }
//        return result;
//    }

    // Stream API
    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // преподователя - вместо моего метода isExceed -и потом из етой мапы доставать сумму калорий по дате !!!
        Map<LocalDate, Integer> localDateIntegerMap = mealList.stream().
                collect(Collectors.groupingBy((um) -> um.getDateTime().toLocalDate(),
                        Collectors.summingInt(um -> um.getCalories())));

        return mealList.stream()
                .filter((userMeal) -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map((userMeal) -> new UserMealWithExceed(userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        isExceed(userMeal, mealList, caloriesPerDay)))
                .collect(Collectors.toList());
    }

    // верно - но  не логично каждый раз вызывать метод - нужно было сделать как у преподователя
    private static boolean isExceed(UserMeal userMeal, List<UserMeal> mealList, int caloriesPerDay) {
        Integer calories = mealList.stream()
                .filter((user) -> (user.getDateTime().toLocalDate()).equals(userMeal.getDateTime().toLocalDate()))
                .map(UserMeal::getCalories)
                .reduce(0,(number1, number2) -> number1 + number2);
        return calories <= caloriesPerDay;
    }
}
