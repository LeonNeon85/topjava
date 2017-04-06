package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;

import java.time.LocalTime;
import java.util.List;


@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    private final Logger LOG = LoggerFactory.getLogger(getClass());

//
//    public Meal save(Meal meal) {
////        checkNew(user);
//        LOG.info("saveMeal " + meal);
//        return service.save(meal);
//    }
//
//    public void delete(Integer userId, Meal userMeal) throws NotFoundException {
//        LOG.info("delete " + userMeal);
//        service.delete(userId, userMeal);
//    }
//
//    public Meal getMeal(Integer mealId) throws NotFoundException {
//        LOG.info("getMeal " + mealId);
//        return service.getMeal(mealId);
//    }
//
//    public List<MealWithExceed> getAll(Integer userId) {
//        LOG.info("getAll " + userId);
//        return service.getAll(userId);
//    }
//
//    public List<MealWithExceed> getFiltered(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
//        LOG.info("getFiltered " + startDate + startTime + "- " + endDate + endTime);
//        return service.getFiltered(startDate, startTime, endDate, endTime);
//    }
//
//    public void update(Integer userId, Meal userMeal){
//        LOG.info("update" + userId);
////        checkIdConsistent(user, id);
//        service.update(userId, userMeal);
//    }
}