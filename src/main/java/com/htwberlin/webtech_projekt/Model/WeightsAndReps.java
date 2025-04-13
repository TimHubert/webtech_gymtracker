package com.htwberlin.webtech_projekt.Model;


import java.util.List;

public class WeightsAndReps {

    private Exercise exercise;
    private List<Integer> reps;
    private List<Double> weights;

    public WeightsAndReps(Exercise exercise, List<Integer> reps, List<Double> weights) {
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

    public List<Integer> getReps() {
        return reps;
    }

    public void setReps(List<Integer> reps) {
        this.reps = reps;
    }

    public List<Double> getWeights() {
        return weights;
    }

    public void setWeights(List<Double> weights) {
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
