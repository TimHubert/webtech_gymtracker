package com.htwberlin.webtech_projekt.Model;

import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;


@Entity
public class WorkoutWithWeights extends Workout {
    private LocalDate date;

    @OneToMany(cascade = CascadeType.ALL)
    private List<WeightsAndReps> weights;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    public WorkoutWithWeights() {
    }

    public WorkoutWithWeights(Workout workout, LocalDate date, List<WeightsAndReps> weights) {
        super(workout.getName(), workout.getExercise());
        this.date = date;
        this.weights = weights;
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

    @Override
    public String toString() {
        return "WorkoutWithWeights{" +
                "date=" + date +
                ", weights=" + weights +
                '}';
    }

}
