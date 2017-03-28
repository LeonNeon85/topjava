package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Kpks on 27.03.2017.
 */
public class MealServlet extends HttpServlet {

    private static final Logger LOG = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("redirect to meal");
        // оправляем список еды без фильтрации по времени
        req.setAttribute("mealsListExeeded", MealsUtil.getFilteredWithExceededWithoutTimeFiltration(MealsUtil.mealsListCreated(), 2000));
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
        //resp.sendRedirect("meals.jsp");
    }
}
