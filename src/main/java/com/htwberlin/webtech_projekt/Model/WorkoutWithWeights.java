package com.htwberlin.webtech_projekt.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;


@Entity
public class WorkoutWithWeights {
    private LocalDate date;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<WeightsAndReps> weights;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Workout workout;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore  // Verhindert JSON-Serialisierung der User-Referenz
    private User user;

    public WorkoutWithWeights() {
    }

    public WorkoutWithWeights(Workout workout, LocalDate date, List<WeightsAndReps> weights) {
        this.workout = workout;
        this.date = date;
        this.weights = weights;
    }

    public WorkoutWithWeights(Workout workout, LocalDate date, List<WeightsAndReps> weights, User user) {
        this.workout = workout;
        this.date = date;
        this.weights = weights;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<WeightsAndReps> getWeights() {
        return weights;
    }

    public void setWeights(List<WeightsAndReps> weights) {
        this.weights = weights;
    }

    public Workout getWorkout() {
        return this.workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "WorkoutWithWeights{" +
                "date=" + date +
                ", weights=" + weights +
                ", user=" + (user != null ? user.getUsername() : "null") +
                '}';
    }
}
