package com.htwberlin.webtech_projekt.Model;


public class WeightsAndReps {

    private Exercise exercise;
    private Integer[] reps;
    private Double[] weights;

    public WeightsAndReps(Exercise exercise, Integer[] reps, Double[] weights) {
        this.exercise = exercise;
        this.reps = reps;
        this.weights = weights;

    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Integer[] getReps() {
        return reps;
    }

    public void setReps(Integer[] reps) {
        this.reps = reps;
    }

    public Double[] getWeights() {
        return weights;
    }

    public void setWeights(Double[] weights) {
        this.weights = weights;
    }

    @Override
    public String toString() {
        return "WeightsAndReps{" +
                ", reps=" + reps +
                ", weights=" + weights +
                '}';
    }
}
