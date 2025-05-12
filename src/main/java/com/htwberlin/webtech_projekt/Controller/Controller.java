package com.htwberlin.webtech_projekt.Controller;

import com.htwberlin.webtech_projekt.Model.Workout;
import com.htwberlin.webtech_projekt.Model.WorkoutWithWeights;
import com.htwberlin.webtech_projekt.Repository.WorkoutRepo;
import com.htwberlin.webtech_projekt.Repository.WorkoutWithWeightsRepo;
import com.htwberlin.webtech_projekt.Service.WorkoutServiceWithWeights;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.htwberlin.webtech_projekt.Model.WeightsAndReps;
import com.htwberlin.webtech_projekt.Service.WorkoutService;
import com.htwberlin.webtech_projekt.Service.WorkoutServiceWithWeights;


import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;


import java.util.*;

import com.htwberlin.webtech_projekt.Model.Exercise;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class Controller {

    @Autowired
    private WorkoutRepo workoutRepository;

    @Autowired
    private WorkoutWithWeightsRepo workoutWithWeightsRepository;

    @Autowired
    private WorkoutServiceWithWeights workoutServiceWithWeights;

    @Autowired
    private WorkoutService workoutService;


    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/workout")
    public ResponseEntity<Workout> getWorkout() {
        LocalDate date = LocalDate.of(2024, 2, 2);
        List<Exercise> exercise = new ArrayList<>();
        exercise.add(new Exercise("Bench Press", "Bench", "Chest"));
        exercise.add(new Exercise("Triceps Extensions", "Cable Tower", "Triceps"));
        exercise.add(new Exercise("Shoulder Press", "Machine", "Shoulders"));
        Workout workout = new Workout("Push", exercise);
        return ResponseEntity.ok(workout);
    }

    @PostMapping("/workout")
    public ResponseEntity<?> createWorkout(@RequestBody Workout workout) {
        if (workout.getName() == null || workout.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Name des Workouts ist erforderlich");
        }
        if (workout.getExercise() == null || workout.getExercise().isEmpty()) {
            return ResponseEntity.badRequest().body("Übungen sind erforderlich");
        }
        return ResponseEntity.ok(workoutService.save(workout));
    }
/*
    @PostMapping("/workout")
    public Workout createWorkout(@RequestBody Workout workout) {
        return workoutService.save(workout);
    }*/


    @GetMapping("/OneWorkout")
    public ResponseEntity<WorkoutWithWeights> getWorkoutWithWeights() {
        LocalDate date = LocalDate.of(2024, 2, 2);
        List<Exercise> exercise = new ArrayList<>();
        exercise.add(new Exercise("Bench Press", "Bench", "Chest"));
        exercise.add(new Exercise("Triceps Extensions", "Cable Tower", "Triceps"));
        exercise.add(new Exercise("Shoulder Press", "Machine", "Shoulders"));

        List<WeightsAndReps> weights = new ArrayList<>();

        weights.add(new WeightsAndReps(new ArrayList<>(Arrays.asList(8, 5, 4)), new ArrayList<>(Arrays.asList(60.0, 60.0, 60.0))));
        weights.add(new WeightsAndReps(new ArrayList<>(Arrays.asList(5, 5, 5)), new ArrayList<>(Arrays.asList(60.0, 55.0, 55.0))));
        weights.add(new WeightsAndReps(new ArrayList<>(Arrays.asList(7, 6, 4)), new ArrayList<>(Arrays.asList(50.0, 55.0, 60.0))));
        Workout workout = new Workout("Push", exercise);
        WorkoutWithWeights WorkoutWithWeights = new WorkoutWithWeights(workout, date, weights);
        return ResponseEntity.ok(WorkoutWithWeights);
    }

    @PostMapping("/OneWorkout")
    public ResponseEntity<?> createWorkoutWithWeights(@RequestBody WorkoutWithWeights workoutWithWeights) {
        try {
            Workout workout = workoutWithWeights.getWorkout();
            if (workout == null || workout.getName() == null || workout.getName().isEmpty()) {
                return ResponseEntity.badRequest().body("Workout-Name ist erforderlich");
            }
            if (workoutWithWeights.getWeights() == null || workoutWithWeights.getWeights().isEmpty()) {
                return ResponseEntity.badRequest().body("Gewichte und Wiederholungen sind erforderlich");
            }
            if (workout.getId() == null) {
                workout = workoutRepository.save(workout);
            }
            WorkoutWithWeights savedWorkout = workoutServiceWithWeights.save(workoutWithWeights);
            return ResponseEntity.status(201).body(savedWorkout);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Fehler beim Speichern des Workouts");
        }
    }

    @GetMapping("/workouts")
    public ResponseEntity<?> getAllWorkouts() {
        try {
            List<Workout> workouts = workoutRepository.findAll();
            System.out.println("Workouts aus der DB: " + workouts);
            return ResponseEntity.ok(workouts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Fehler beim Abrufen der Workouts");
        }
    }


    @GetMapping("/workoutsWithWeights")
    public ResponseEntity<?> getAllWorkoutsWithWeights() {
        try {
            List<WorkoutWithWeights> workouts = workoutWithWeightsRepository.findAll();
            System.out.println("Workouts aus der DB: " + workouts);
            return ResponseEntity.ok(workouts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Fehler beim Abrufen der Workouts");
        }
    }

    @DeleteMapping("/workout/{id}")
    public ResponseEntity<?> deleteWorkout(@PathVariable Long id) {
        if (!workoutRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Workout mit ID " + id + " nicht gefunden");
        } else {
            workoutService.deleteById(id);
        }
        return ResponseEntity.ok("Workout mit ID " + id + " wurde gelöscht");
    }

    @DeleteMapping("/workout/{id}/{exId}")
    public ResponseEntity<?> deleteExercise(@PathVariable Long id, @PathVariable Long exId) {
        if (!workoutRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Workout with ID " + id + " not found");
        }

        Workout workout = workoutService.findById(id);
        if (workout == null) {
            return ResponseEntity.status(404).body("Workout with ID " + id + " not found");
        }

        List<Exercise> exercises = workout.getExercise();
        if (exercises != null && exercises.removeIf(exercise -> exercise.getId().equals(exId))) {
            workoutService.save(workout); // Save the updated workout
        } else {
            return ResponseEntity.status(404).body("Exercise with ID " + exId + " not found in workout");
        }

        if (exercises == null || exercises.isEmpty()) {
            workoutService.deleteById(id); // Delete the workout if no exercises remain
            return ResponseEntity.ok("Workout with ID " + id + " and its exercises were deleted");
        }

        return ResponseEntity.ok("Exercise with ID " + exId + " was deleted from workout with ID " + id);
    }

    @GetMapping("/workout/{Id}")
    public ResponseEntity<?> getAWorkout(@PathVariable("Id") Long Id) {
        Workout workout = workoutService.findById(Id);
        return ResponseEntity.ok(workout);
    }

    @GetMapping("/workoutWithWeights/{id}")
    public ResponseEntity<?> getWorkoutWithWeightsById(@PathVariable Long id) {
        try {
            WorkoutWithWeights workout = workoutServiceWithWeights.findByIdWithWeights(id);
            if (workout == null) {
                return ResponseEntity.status(404).body("Workout with ID " + id + " not found");
            }
            return ResponseEntity.ok(workout);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error retrieving the workout");
        }
    }

}

