package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

//5 Сделать MealServiceTest из MealService (Ctrl+Shift+T и выбрать JUnit4) и реализовать тесты.
//
//        5.1 Сделать тестовые данные MealTestData, АНАЛОГИЧНЫЕ пропопулированным в populateDB.sql. Сравниваем данные через MealTestData.MATCHER
//        5.2 Сделать тесты на чужую еду (delete, get, update) с тем чтобы получить NotFoundException.


/**
 * Created by Kpks on 17.04.2017.
 */

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(USER_MEAL1.getId(), USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_MEAL2, USER_MEAL3), service.getAll(USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        service.delete(ADMIN_MEAL1.getId() , USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_MEAL2, USER_MEAL3), service.getAll(USER_ID));
    }

    @Test
    public void testGet() throws Exception {
        Meal actual = service.get(ADMIN_MEAL1.getId() , ADMIN_ID);
        MATCHER.assertEquals(ADMIN_MEAL1, actual);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
       service.get(USER_MEAL1.getId() , ADMIN_ID);
    }

    @Test
    public void getBetweenDates() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(USER_MEAL1, USER_MEAL2, USER_MEAL3),
                service.getBetweenDates(LocalDate.of(2014, Month.JUNE, 1), LocalDate.of(2017, Month.SEPTEMBER, 1), USER_ID));
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(USER_MEAL1, USER_MEAL2, USER_MEAL3), service.getAll(USER_ID));
    }

    @Test
    public void update() throws Exception {
        Meal updatedMeal = new Meal(USER_MEAL1.getId(), USER_MEAL1.getDateTime(), "Обновленный завтрак", 200);
        service.update(updatedMeal, USER_ID);
        MATCHER.assertEquals(updatedMeal, service.get(USER_MEAL1.getId(), USER_ID) );
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        Meal updatedMeal = new Meal(USER_MEAL1.getId(), USER_MEAL1.getDateTime(), "Обновленный завтрак", 200);
        service.update(updatedMeal, ADMIN_ID);
    }

    @Test
    public void save() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.now(), "dfr", 15);
        Meal savedMeal = service.save(newMeal, USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_MEAL1, USER_MEAL2, USER_MEAL3, newMeal) ,service.getAll(USER_ID));
    }
}