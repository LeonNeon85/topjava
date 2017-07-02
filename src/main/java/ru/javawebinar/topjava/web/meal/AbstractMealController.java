package ru.javawebinar.topjava.web.meal;

/**
 * Created by Kpks on 09.06.2017.
 */
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import ru.javawebinar.topjava.AuthorizedUser;
        import ru.javawebinar.topjava.model.Meal;
        import ru.javawebinar.topjava.service.MealService;
        import ru.javawebinar.topjava.to.MealWithExceed;
        import ru.javawebinar.topjava.util.DateTimeUtil;
        import ru.javawebinar.topjava.util.MealsUtil;

        import javax.servlet.http.HttpServletRequest;
        import java.time.LocalDate;
        import java.time.LocalTime;
        import java.util.List;
        import java.util.Objects;

        import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
        import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

/**
 * Created by Kpks on 27.05.2017.
 */

@Controller
public class AbstractMealController {
    private static final Logger LOG = LoggerFactory.getLogger(MealRestController.class);

    protected MealService service;

    @Autowired
    public void setService(MealService service) {
        this.service = service;
    }

    public MealService getService() {
        return service;
    }

    public Meal get(int id) {
        int userId = AuthorizedUser.id();
        LOG.info("get meal {} for User {}", id, userId);
        return service.get(id, userId);
    }

    public void delete(int id) {
        int userId = AuthorizedUser.id();
        LOG.info("delete meal {} for User {}", id, userId);
        service.delete(id, userId);
    }

    public List<MealWithExceed> getAll() {
        int userId = AuthorizedUser.id();
        LOG.info("getAll for User {}", userId);
        return MealsUtil.getWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay());
    }

    public Meal create(Meal meal) {
        int userId = AuthorizedUser.id();
        checkNew(meal);
        LOG.info("create {} for User {}", meal, userId);
        return service.save(meal, userId);
    }

    public void update(Meal meal, int id) {
        int userId = AuthorizedUser.id();
        checkIdConsistent(meal, id);
        LOG.info("update {} for User {}", meal, userId);
        service.update(meal, userId);
    }

    public List<MealWithExceed> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = AuthorizedUser.id();
        LOG.info("getBetween dates({} - {}) time({} - {}) for User {}", startDate, endDate, startTime, endTime, userId);

        return MealsUtil.getFilteredWithExceeded(
                service.getBetweenDates(
                        startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                        endDate != null ? endDate : DateTimeUtil.MAX_DATE, userId),
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay()
        );
    }

    protected int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}

