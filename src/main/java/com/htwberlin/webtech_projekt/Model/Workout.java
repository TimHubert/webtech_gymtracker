package com.htwberlin.webtech_projekt.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import java.util.List;

@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

        @OneToMany(cascade = CascadeType.ALL)
        private List<Exercise> exercise;

        public Workout() {
        }

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


