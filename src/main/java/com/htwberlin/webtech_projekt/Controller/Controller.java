package com.htwberlin.webtech_projekt.Controller;
import com.htwberlin.webtech_projekt.Model.Workout;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import com.htwberlin.webtech_projekt.Model.WeightsAndReps;

import java.util.*;

import com.htwberlin.webtech_projekt.Model.Exercise;

@RestController
public class Controller {

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/workout")
    public ResponseEntity<Workout> getWorkout() {
        Date date = new Date(1, 1, 2023);
        List<Exercise> exercise = new ArrayList<>() ;
        exercise.add(new Exercise("Bench Press", "Bench", "Chest"));
        exercise.add(new Exercise("Triceps Extensions", "Cable Tower", "Triceps"));
        exercise.add(new Exercise("Shoulder Press", "Machine", "Shoulders"));

        List<WeightsAndReps> weights = new ArrayList<>();
        weights.add(new WeightsAndReps(exercise.get(0), new Integer[]{8, 7, 6}, new Double[]{60.0, 60.0, 57.5}));
        weights.add(new WeightsAndReps(exercise.get(1), new Integer[]{7, 6, 6}, new Double[]{27.5, 30.0, 30.0}));
        weights.add(new WeightsAndReps(exercise.get(2), new Integer[]{5, 4, 4}, new Double[]{40.0, 40.0, 40.0}));

        Workout workout = new Workout("Push", date, exercise, weights);
        return ResponseEntity.ok(workout);
    }
}

