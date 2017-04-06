package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

//    @Override
//    public Meal save(Meal meal) {
//        return null;
//    }
//
//    @Override
//    public boolean delete(int id) {
//        return false;
//    }
//
//    @Override
//    public Meal get(int id) {
//        return null;
//    }
//
//    @Override
//    public Collection<Meal> getAll() {
//        return null;
//    }
//
//    @Override
//    public Meal save(Integer userId, Meal meal) {
//        return null;
//    }
//
//    @Override
//    public boolean delete(Integer userId, Meal userMeal) throws NotFoundException {
//        return false;
//    }
//
//    @Override
//    public Collection<Meal> getAll(Integer userId) {
//        return null;
//    }
//
//    @Override
//    public Collection<Meal> getFiltered(Integer userId, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
//        return null;
//    }
}