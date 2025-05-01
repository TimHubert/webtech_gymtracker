package com.htwberlin.webtech_projekt.Controller;
import com.htwberlin.webtech_projekt.Model.Workout;
import com.htwberlin.webtech_projekt.Model.WorkoutWithWeights;
import com.htwberlin.webtech_projekt.Service.WorkoutServiceWithWeights;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import com.htwberlin.webtech_projekt.Model.WeightsAndReps;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.htwberlin.webtech_projekt.Service.WorkoutService;
import java.time.LocalDate;



import java.util.*;

import com.htwberlin.webtech_projekt.Model.Exercise;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class Controller {

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/workout")
    public ResponseEntity<Workout> getWorkout() {
        LocalDate date = LocalDate.of(2024,2,2);
        List<Exercise> exercise = new ArrayList<>() ;
        exercise.add(new Exercise("Bench Press", "Bench", "Chest"));
        exercise.add(new Exercise("Triceps Extensions", "Cable Tower", "Triceps"));
        exercise.add(new Exercise("Shoulder Press", "Machine", "Shoulders"));

        List<WeightsAndReps> weights = new ArrayList<>();
        weights.add(new WeightsAndReps(new ArrayList<>(Arrays.asList(8, 6, 5)), new ArrayList<>(Arrays.asList(60.0, 60.0, 60.0))));
        weights.add(new WeightsAndReps(new ArrayList<>(Arrays.asList(5, 5, 5)), new ArrayList<>(Arrays.asList(60.0, 55.0, 55.0))));
        weights.add(new WeightsAndReps(new ArrayList<>(Arrays.asList(7, 6, 4)), new ArrayList<>(Arrays.asList(50.0, 55.0, 60.0))));
        Workout workout = new Workout("Push", exercise);
        return ResponseEntity.ok(workout);
    }

    @PostMapping("/workout")
    public Workout createWorkout(@RequestBody  Workout workout) {
        return WorkoutService.save(workout);
    }


    @GetMapping("/OneWorkout")
    public ResponseEntity<WorkoutWithWeights> getWorkoutWithWeights() {
        LocalDate date = LocalDate.of(2024,2,2);
        List<Exercise> exercise = new ArrayList<>() ;
        exercise.add(new Exercise("Bench Press", "Bench", "Chest"));
        exercise.add(new Exercise("Triceps Extensions", "Cable Tower", "Triceps"));
        exercise.add(new Exercise("Shoulder Press", "Machine", "Shoulders"));

        List<WeightsAndReps> weights = new ArrayList<>();
        weights.add(new WeightsAndReps(new ArrayList<>(Arrays.asList(8, 6, 5)), new ArrayList<>(Arrays.asList(60.0, 60.0, 60.0))));
        weights.add(new WeightsAndReps(new ArrayList<>(Arrays.asList(5, 5, 5)), new ArrayList<>(Arrays.asList(60.0, 55.0, 55.0))));
        weights.add(new WeightsAndReps(new ArrayList<>(Arrays.asList(7, 6, 4)), new ArrayList<>(Arrays.asList(50.0, 55.0, 60.0))));
        Workout workout = new Workout("Push", exercise);
        WorkoutWithWeights WorkoutWithWeights = new WorkoutWithWeights(workout, date, weights);
        return ResponseEntity.ok(WorkoutWithWeights);
    }

    @PostMapping("/OneWorkout")
    public WorkoutWithWeights createWorkoutWithWeights(@RequestBody  WorkoutWithWeights WorkoutWithWeights) {
        return WorkoutServiceWithWeights.save(WorkoutWithWeights);
    }

}

