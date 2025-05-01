package com.htwberlin.webtech_projekt.Model;
import java.time.LocalDate;
import java.util.List;




public class WorkoutWithWeights extends Workout {
    private LocalDate date;
    private List<WeightsAndReps> weights;

    public WorkoutWithWeights() {
    }

    public WorkoutWithWeights(Workout workout, LocalDate date , List<WeightsAndReps> weights) {
        super(workout.getName(), workout.getExercise());
        this.date = date;
        this.weights = weights;
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
