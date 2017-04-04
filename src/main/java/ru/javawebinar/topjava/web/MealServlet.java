package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealDao;
import ru.javawebinar.topjava.model.MealDaoInterfase;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Kpks on 27.03.2017.
 */

// у нас интанцируеться етот сервлет только один раз !! поетому у нас одна версия етого сервлета

public class MealServlet extends HttpServlet {

    private MealDaoInterfase mealDao = new MealDao();
    private int caloriesPerDay = 2000;

    private final static String INSERT_OR_EDIT = "/edit.jsp";
    private final static String LIST_MEALS = "/meals.jsp";

    private static final Logger LOG = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("redirect to meal");

        String forward="";
        String action = req.getParameter("action");
        if (action == null || action.isEmpty() || action.equalsIgnoreCase("listMeals")) {
            forward = LIST_MEALS;
            List<MealWithExceed> listMealWithExceeded = MealsUtil.getFilteredWithExceededWithoutTimeFiltration(mealDao.getAllMeals(), caloriesPerDay);
            req.setAttribute("mealsListExeeded", listMealWithExceeded);

        } else if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(req.getParameter("mealId"));
            mealDao.deleteMeal(mealId);
            forward = LIST_MEALS;
            List<MealWithExceed> listMealWithExceeded = MealsUtil.getFilteredWithExceededWithoutTimeFiltration(mealDao.getAllMeals(), caloriesPerDay);
            req.setAttribute("mealsListExeeded", listMealWithExceeded);

        } else if (action.equalsIgnoreCase("update")) {
            forward = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(req.getParameter("mealId"));
            Meal meal = mealDao.getMealById(mealId);
            req.setAttribute("Meal", meal);
        }


        req.getRequestDispatcher(forward).forward(req, resp);
        LOG.debug("exit from doGet inside MealServlet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("inside doPost  in meal");
        req.setCharacterEncoding("UTF-8");

        try {
            String date = req.getParameter("dateTime");
            String description = req.getParameter("description");
            Integer calories = Integer.parseInt(req.getParameter("calories"));

            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime ld = LocalDateTime.parse(date, df);


            Meal newMeal = new Meal(ld, description, calories);

            String mealId = req.getParameter("mealId");
            if (mealId == null || mealId.isEmpty())  {
                mealDao.addMeal(newMeal);
            } else  {
                mealDao.updateMeal(Integer.parseInt(mealId), newMeal);
            }

            List<MealWithExceed> listMealWithExceeded = MealsUtil.getFilteredWithExceededWithoutTimeFiltration(mealDao.getAllMeals(), caloriesPerDay);
            req.setAttribute("mealsListExeeded", listMealWithExceeded);
            req.getRequestDispatcher(LIST_MEALS).forward(req, resp);

            LOG.debug("EXIT  doPost  in meal");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
