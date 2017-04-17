package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

//3 Реализовать через Spring JDBC Template JdbcMealRepositoryImpl
//
//        3.1. сделать каждый метод за один SQL запрос
//        3.2. userId в класс Meal вставлять НЕ надо (для UI и REST это лишние данные, userId это id залогиненного пользователя)
//        3.3. JbdcTemplate работает через сеттеры. Вместе с конструктором по умолчанию их нужно добавить в Meal
//        3.4. Cписок еды должен быть отсортирован (тогда мы его сможем сравнивать с тестовыми данными). Кроме того это требуется для UI и API: последняя еда наверху.
//
//        4 Проверить работу MealServlet, запустив приложение

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {

    // Нужно было создать свой ROW_MAPPER
    private static final RowMapper<Meal> ROW_MAPPER = new RowMapper<Meal>() {
        @Override
        public Meal mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Meal(rs.getInt("meal_id"), rs.getTimestamp("dateTime").toLocalDateTime(),
                    rs.getString("description"), rs.getInt("calories"));
        }
    } ;

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcMealRepositoryImpl (DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("meals")
                .usingGeneratedKeyColumns("meal_id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("dateTime", meal.getDateTime())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories())
                .addValue("user_id", userId);

        if (meal.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(map);
            meal.setId(newKey.intValue());
        }
            namedParameterJdbcTemplate.update(
                    "UPDATE meals SET dateTime=:dateTime, description=:description, " +
                            "calories=:calories, user_id=:user_id WHERE meal_id=:id AND user_id=:user_id", map);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE meal_id = ? AND user_id = ?", id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = jdbcTemplate.query("SELECT * FROM meals WHERE meal_id=? AND user_id = ?", ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id = ? ORDER BY datetime", ROW_MAPPER, userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id = ? AND (datetime BETWEEN ? AND ?) ORDER BY datetime ", ROW_MAPPER, userId, startDate, endDate);
    }
}
