package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

public class MealTestData {

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    private static int userId = START_SEQ + 2;
    private static int adminId = START_SEQ + 2 + 3;

    public static final Meal USER_MEAL1 = new Meal(userId, LocalDateTime.now(), "Завтрак User" , 2500);
    public static final Meal USER_MEAL2 = new Meal(userId+1, LocalDateTime.now(), "Обед User" , 500);
    public static final Meal USER_MEAL3 = new Meal(userId+2, LocalDateTime.now(), "Ужин User" , 500);

    public static final Meal ADMIN_MEAL1 = new Meal(adminId, LocalDateTime.of(2015, Month.MAY, 6, 0, 0), "Завтрак Admin" , 1500);
    public static final Meal ADMIN_MEAL2 = new Meal(adminId+1, LocalDateTime.of(2015, Month.MAY, 6, 0, 0), "Obed Admi" , 1500);
    public static final Meal ADMIN_MEAL3 = new Meal(adminId+2, LocalDateTime.of(2015, Month.MAY, 6, 0, 0), "Yzin Admi" , 1500);

//    public static final List<Meal> userMealsList = new ArrayList<>();
//    public static final List<Meal> adminMealsList = new ArrayList<>();

//    static {
//        userMealsList.add(USER_MEAL1);
//        userMealsList.add(USER_MEAL2);
//        userMealsList.add(USER_MEAL3);
//
//        adminMealsList.add(ADMIN_MEAL1);
//        adminMealsList.add(ADMIN_MEAL2);
//        adminMealsList.add(ADMIN_MEAL3);
//    }

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>( (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getId(), actual.getId())
            && Objects.equals(expected.getDateTime().toLocalDate(), actual.getDateTime().toLocalDate())
            && Objects.equals(expected.getDescription(), actual.getDescription())
            && Objects.equals(expected.getCalories(), actual.getCalories())
            ));
}
