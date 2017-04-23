package ru.javawebinar.topjava.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * GKislin
 * 11.01.2015.
 */



@NamedQueries({
        @NamedQuery(name = "Meal.DELETE", query = "DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:user_id"),
        @NamedQuery(name = "Meal.BEETWENE", query = "SELECT m FROM Meal AS m WHERE m.user.id=:user_id AND m.dateTime BETWEEN :startDate AND :endDate ORDER BY m.dateTime desc"),
        @NamedQuery(name = "Meal.ALL_SORTED", query = "SELECT m FROM Meal AS m WHERE m.user.id=:user_id ORDER BY m.dateTime desc "),
        @NamedQuery(name = "Meal.SAVE", query = "UPDATE Meal AS m SET m.user.id=:userId"),
        @NamedQuery(name = "Meal.UPDATE", query = "UPDATE Meal m SET m.dateTime=:dateTime, m.description=:description, m.calories=:calories WHERE m.id =:id AND m.user.id=:userId"),
        @NamedQuery(name = "Meal.FIND", query = "SELECT m FROM Meal AS m WHERE m.id=:id AND m.user.id=:userId"),
})

@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "meals_unique_user_datetime_idx")} )
public class Meal extends BaseEntity {

//    public static final String DELETE = "Meal.DELETE";
//    public static final String BEETWENE = "Meal.BEETWENE";
//    public static final String ALL_SORTED = "Meal.ALL_SORTED";

    @NotNull
    @Column(name = "date_time", columnDefinition = "timestamp default now()", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "calories", nullable = false, columnDefinition = "int default 1000")
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}