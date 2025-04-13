package com.htwberlin.webtech_projekt.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.Date;

import java.util.HashMap;
import java.util.List;

public class Workout {
        private String name;
        private Date date;
        private List<Exercise> exercise;
        private List<WeightsAndReps> weights;

        public Workout(String name, Date date, List<Exercise> exercise, List<WeightsAndReps> weights) {
            this.name = name;
            this.date  = date;
            this.exercise = exercise;
            this.weights = weights;
        }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Exercise> getExercise() {
        return exercise;
    }

    public void setExercise(List<Exercise> exercise) {
        this.exercise = exercise;
    }

    public List<WeightsAndReps> getWeights() {
        return weights;
    }

    public void setWeights(List<WeightsAndReps> weights) {
        this.weights = weights;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", exercise=" + exercise +
                ", weights=" + weights +
                '}';
    }
}


