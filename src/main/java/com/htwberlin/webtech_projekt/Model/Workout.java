package com.htwberlin.webtech_projekt.Model;

import java.util.List;

public class Workout {
        private String name;
        private List<Exercise> exercise;

        public Workout(String name, List<Exercise> exercise) {
            this.name = name;
            this.exercise = exercise;
        }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Exercise> getExercise() {
        return exercise;
    }

    public void setExercise(List<Exercise> exercise) {
        this.exercise = exercise;
    }


    @Override
    public String toString() {
        return "Workout{" +
                "name='" + name + '\'' +
                ", exercise=" + exercise +
                '}';
    }
}


