package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.meal.AbstractMealController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;


/**
 * Created by Kpks on 09.06.2017.
 */
@Controller
@RequestMapping("/meals")
public class MealController extends AbstractMealController {

    @RequestMapping(value = "/createOrUpdate", method = RequestMethod.POST)
    public String createOrUpdate(HttpServletRequest request) {
        final Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        if (request.getParameter("id").isEmpty()) {
            super.create(meal);
        } else {
            super.update(meal, getId(request));
        }
        System.out.println("---------------- inside createOrUpdate ");
        return "redirect:/meals";
    }


    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String getBetween (HttpServletRequest request) {
        LocalDate startDate = DateTimeUtil.parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = DateTimeUtil.parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = DateTimeUtil.parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = DateTimeUtil.parseLocalTime(request.getParameter("endTime"));
        request.setAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(HttpServletRequest request) {
        int id = super.getId(request);
        super.delete(id);
        return "redirect:/meals";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String showAll(HttpServletRequest request) {
        request.setAttribute("meals", super.getAll());
        return "meals";
    }


    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(HttpServletRequest request) {
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        request.setAttribute("meal", meal);
        request.setAttribute("action", "create");

        System.out.println(request.getServletPath() + " - this is getServletPath");
        System.out.println(request.getContextPath() + " - this is getContextPath");
        System.out.println(request.getRequestURI() + " - this is getRequestURI");
        System.out.println(request.getRequestURL() + " - this is getRequestURL");
        String url = request.getRequestURL().toString();
        System.out.println(url.substring(0,(url.length() - request.getRequestURI().length())) + request.getContextPath() + "/"+" - this is getRequestURL");
        // <base href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/" />
        return "meal";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(HttpServletRequest request) {
        final Meal meal = super.get(getId(request));
        request.setAttribute("meal", meal);
        return "meal";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String root(HttpServletRequest request) {
        request.setAttribute("meals", super.getAll());
        return "meals";
    }
}


