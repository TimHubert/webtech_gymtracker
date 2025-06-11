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
        Workout workout = new Workout("Push", exercise, true);
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
        Workout workout = new Workout("Push", exercise, true);
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

    @PutMapping("/OneWorkout/{id}")
    public ResponseEntity<?> updateWorkoutWithWeights(
            @PathVariable Long id,
            @RequestBody WorkoutWithWeights workoutWithWeights) {
        try {
            // Check if the WorkoutWithWeights exists
            Optional<WorkoutWithWeights> existingWorkoutWithWeights = workoutWithWeightsRepository.findById(id);
            if (existingWorkoutWithWeights.isEmpty()) {
                return ResponseEntity.status(404).body("WorkoutWithWeights with ID " + id + " not found");
            }

            // Update the associated Workout
            Workout existingWorkout = existingWorkoutWithWeights.get().getWorkout();
            Workout updatedWorkout = workoutWithWeights.getWorkout();
            if (updatedWorkout != null) {
                existingWorkout.setName(updatedWorkout.getName());
                existingWorkout.setExercise(updatedWorkout.getExercise());
                workoutRepository.save(existingWorkout);
            }

            // Update the WorkoutWithWeights entity
            WorkoutWithWeights existingEntity = existingWorkoutWithWeights.get();
            existingEntity.setDate(workoutWithWeights.getDate());
            existingEntity.setWeights(workoutWithWeights.getWeights());
            existingEntity.setWorkout(existingWorkout);

            WorkoutWithWeights updatedEntity = workoutWithWeightsRepository.save(existingEntity);

            return ResponseEntity.ok(updatedEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error updating the workout");
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

    @DeleteMapping("/workoutWithWeights/{id}")
    public ResponseEntity<?> deleteWorkoutWithWeightsById(@PathVariable Long id) {
        try {
            WorkoutWithWeights workout = workoutServiceWithWeights.findByIdWithWeights(id);
            if (workout == null) {
                return ResponseEntity.status(404).body("Workout with ID " + id + " not found");
            }
            workoutServiceWithWeights.deleteById(id);
            return ResponseEntity.ok(workout );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error retrieving the workout");
        }
    }

    @PutMapping("/workout/{id}")
    public ResponseEntity<?> updateWorkout(@PathVariable Long id, @RequestBody Workout workout) {
        if (!workoutRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Workout with ID " + id + " not found");
        }
        Workout existingWorkout = workoutRepository.findById(id).orElse(null);
        if (existingWorkout == null) {
            return ResponseEntity.status(404).body("Workout with ID " + id + " not found");
        }
        existingWorkout.setShow(workout.getShow());
        workoutRepository.save(existingWorkout);

        return ResponseEntity.ok(existingWorkout);
    }


    @GetMapping("/api/stats/summary")
    public ResponseEntity<Map<String, Object>> getStatsSummary() {
        try {
            List<WorkoutWithWeights> allWorkouts = workoutWithWeightsRepository.findAll();

            Map<String, Object> stats = new HashMap<>();

            // Total Workouts
            stats.put("totalWorkouts", allWorkouts.size());

            // Current Streak (vereinfacht)
            stats.put("currentStreak", Math.min(allWorkouts.size() / 2, 5));

            // Total Volume
            double totalVolume = calculateTotalVolume(allWorkouts);
            stats.put("totalVolume", Math.round(totalVolume / 1000)); // in k

            // Favorite Exercise
            stats.put("favoriteExercise", getFavoriteExercise(allWorkouts));
            stats.put("favoriteExerciseShort", getFavoriteExerciseShort(allWorkouts));

            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/api/stats/weekly")
    public ResponseEntity<Map<String, Object>> getWeeklyStats() {
        try {
            List<WorkoutWithWeights> allWorkouts = workoutWithWeightsRepository.findAll();

            Map<String, Object> weekStats = new HashMap<>();
            weekStats.put("workoutsThisWeek", Math.min(allWorkouts.size(), 3));
            weekStats.put("volumeThisWeek", "2.8");
            weekStats.put("bestLift", "85kg");
            weekStats.put("progress", "+5kg");

            return ResponseEntity.ok(weekStats);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/api/stats/frequency")
    public ResponseEntity<List<Integer>> getWorkoutFrequency() {
        try {
            List<WorkoutWithWeights> allWorkouts = workoutWithWeightsRepository.findAll();

            // Einfache Logik: Verteile Workouts auf Woche
            List<Integer> frequency = Arrays.asList(
                    allWorkouts.size() > 0 ? 1 : 0,  // Mo
                    0,  // Di
                    allWorkouts.size() > 1 ? 1 : 0,  // Mi
                    allWorkouts.size() > 2 ? 1 : 0,  // Do
                    0,  // Fr
                    allWorkouts.size() > 3 ? 1 : 0,  // Sa
                    0   // So
            );

            return ResponseEntity.ok(frequency);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Arrays.asList(1, 0, 1, 1, 0, 1, 0));
        }
    }

    @GetMapping("/api/stats/progression")
    public ResponseEntity<List<Double>> getWeightProgression() {
        try {
            List<WorkoutWithWeights> allWorkouts = workoutWithWeightsRepository.findAll();

            if (allWorkouts.isEmpty()) {
                return ResponseEntity.ok(Arrays.asList(60.0, 65.0, 70.0, 75.0, 80.0, 85.0));
            }

            // Einfache Progression basierend auf Anzahl Workouts
            List<Double> progression = new ArrayList<>();
            double baseWeight = 60.0;

            for (int i = 0; i < 6; i++) {
                if (i < allWorkouts.size()) {
                    progression.add(baseWeight + (i * 5.0));
                } else {
                    progression.add(baseWeight + (i * 5.0));
                }
            }

            return ResponseEntity.ok(progression);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Arrays.asList(60.0, 65.0, 70.0, 75.0, 80.0, 85.0));
        }
    }

    @GetMapping("/api/stats/distribution")
    public ResponseEntity<Map<String, Integer>> getExerciseDistribution() {
        try {
            List<WorkoutWithWeights> allWorkouts = workoutWithWeightsRepository.findAll();

            Map<String, Integer> distribution = new HashMap<>();

            // Sammle alle Übungen
            for (WorkoutWithWeights workout : allWorkouts) {
                if (workout.getWorkout() != null && workout.getWorkout().getExercise() != null) {
                    for (Exercise exercise : workout.getWorkout().getExercise()) {
                        String exerciseName = exercise.getName();
                        distribution.put(exerciseName, distribution.getOrDefault(exerciseName, 0) + 1);
                    }
                }
            }

            // Falls keine Daten, return Dummy
            if (distribution.isEmpty()) {
                distribution.put("Bankdrücken", 25);
                distribution.put("Kniebeugen", 20);
                distribution.put("Kreuzheben", 18);
                distribution.put("Schulterdrücken", 15);
                distribution.put("Klimmzüge", 12);
            }

            return ResponseEntity.ok(distribution);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Integer> fallback = new HashMap<>();
            fallback.put("Bankdrücken", 25);
            fallback.put("Kniebeugen", 20);
            fallback.put("Kreuzheben", 18);
            fallback.put("Schulterdrücken", 15);
            fallback.put("Klimmzüge", 12);
            return ResponseEntity.ok(fallback);
        }
    }

    @GetMapping("/api/stats/volume")
    public ResponseEntity<List<Double>> getWeeklyVolume() {
        try {
            List<WorkoutWithWeights> allWorkouts = workoutWithWeightsRepository.findAll();

            // Einfache Volume-Verteilung
            List<Double> volumes = Arrays.asList(2.5, 2.8, 3.2, 2.9, 3.5, 3.8);

            return ResponseEntity.ok(volumes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(Arrays.asList(2.5, 2.8, 3.2, 2.9, 3.5, 3.8));
        }
    }

    // Helper Methods für Stats
    private double calculateTotalVolume(List<WorkoutWithWeights> workouts) {
        double totalVolume = 0;

        for (WorkoutWithWeights workout : workouts) {
            if (workout.getWeights() != null) {
                for (WeightsAndReps weightSet : workout.getWeights()) {
                    if (weightSet.getWeights() != null && weightSet.getReps() != null) {
                        for (int i = 0; i < weightSet.getWeights().size() && i < weightSet.getReps().size(); i++) {
                            totalVolume += weightSet.getWeights().get(i) * weightSet.getReps().get(i);
                        }
                    }
                }
            }
        }

        return totalVolume;
    }

    private String getFavoriteExercise(List<WorkoutWithWeights> workouts) {
        Map<String, Integer> exerciseCount = new HashMap<>();

        for (WorkoutWithWeights workout : workouts) {
            if (workout.getWorkout() != null && workout.getWorkout().getExercise() != null) {
                for (Exercise exercise : workout.getWorkout().getExercise()) {
                    String name = exercise.getName();
                    exerciseCount.put(name, exerciseCount.getOrDefault(name, 0) + 1);
                }
            }
        }

        return exerciseCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Bankdrücken");
    }

    private String getFavoriteExerciseShort(List<WorkoutWithWeights> workouts) {
        String favorite = getFavoriteExercise(workouts);
        if (favorite.toLowerCase().contains("bench") || favorite.toLowerCase().contains("bankdrücken")) return "Bench";
        if (favorite.toLowerCase().contains("squat") || favorite.toLowerCase().contains("kniebeugen")) return "Squat";
        if (favorite.toLowerCase().contains("deadlift") || favorite.toLowerCase().contains("kreuzheben")) return "Deadlift";
        return favorite.length() > 8 ? favorite.substring(0, 8) : favorite;
    }


}

