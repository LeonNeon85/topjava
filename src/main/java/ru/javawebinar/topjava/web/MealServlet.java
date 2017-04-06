package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private MealRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new InMemoryMealRepositoryImpl();
    }


    /*@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        repository.save(meal);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                LOG.info("Delete {}", id);
                repository.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = action.equals("create") ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        repository.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/meal.jsp").forward(request, response);
                break;
            case "all":
            default:
                LOG.info("getAll");
                request.setAttribute("meals",
                        MealsUtil.getWithExceeded(repository.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }*/

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("type");

        switch (action) {
            case "create" :
                Integer userId = getUserId(request);
                String id = request.getParameter("id");
                Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                        LocalDateTime.parse(request.getParameter("dateTime")),
                        request.getParameter("description"),
                        Integer.valueOf(request.getParameter("calories")));

                LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
                repository.save(userId, meal);
                request.setAttribute("userId", userId);
                request.setAttribute("meals",
                        MealsUtil.getWithExceeded(repository.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY));
    //                request.setAttribute("meals",
    //                        MealsUtil.getWithExceeded(repository.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "filter":
            default:
                //фильтрация списка
                userId = getUserId(request);
                LOG.info("filtered users" );

                LocalDate startDate = LocalDate.parse(request.getParameter("startDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate endDate =LocalDate.parse(request.getParameter("endDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalTime startTime = LocalTime.parse(request.getParameter("startTime"), DateTimeFormatter.ofPattern("HH:mm"));
                LocalTime endTime = LocalTime.parse(request.getParameter("endTime"), DateTimeFormatter.ofPattern("HH:mm"));

                request.setAttribute("userId", userId);
                request.setAttribute("meals",
                        MealsUtil.getWithExceeded(repository.getFiltered(userId, startDate, startTime, endDate, endTime ),
                                MealsUtil.DEFAULT_CALORIES_PER_DAY));

                request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int id;
        int userId ;

        switch (action == null ? "all" : action) {
            case "delete":
                id = getId(request);
                userId = getUserId(request);
                LOG.info("Delete {}", id);
                repository.delete(userId, id);
                // не перенаправляет на етот метод с параметром userId
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
               LOG.info("update");
                userId = getUserId(request);
                final Meal meal = action.equals("create") ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        repository.get(getId(request));
                request.setAttribute("meal", meal);
                request.setAttribute("userId", userId);
                request.getRequestDispatcher("/meal.jsp").forward(request, response);
                break;
            case "all":
            default:
                LOG.info("getAll");
                request.setAttribute("userId", AuthorizedUser.id());
                request.setAttribute("meals",
                        MealsUtil.getWithExceeded(repository.getAll(AuthorizedUser.id()), MealsUtil.DEFAULT_CALORIES_PER_DAY));
//                request.setAttribute("meals",
//                        MealsUtil.getWithExceeded(repository.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }



    private int getId(HttpServletRequest request) {
//        String paramId = request.getParameter("id");
//        if (paramId == null || paramId.isEmpty()) {
//            return 0;
//        }
//        return Integer.valueOf(paramId);
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

    private int getUserId(HttpServletRequest request) {
//        String paramUserId = request.getParameter("userId");
//        if (paramUserId == null || paramUserId.isEmpty()) {
//            return 0;
//        }
//        return Integer.valueOf(paramUserId);
        String paramUserId = Objects.requireNonNull(request.getParameter("userId"));
        return Integer.valueOf(paramUserId);
    }
}