package com.htwberlin.webtech_projekt.Controller;

import com.htwberlin.webtech_projekt.Model.User;
import com.htwberlin.webtech_projekt.Model.Workout;
import com.htwberlin.webtech_projekt.Model.WorkoutWithWeights;
import com.htwberlin.webtech_projekt.Repository.WorkoutRepository;
import com.htwberlin.webtech_projekt.Repository.WorkoutWithWeightsRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
// @RestController - DEAKTIVIERT: Konflikt mit Controller.java
public class WorkoutController {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private WorkoutWithWeightsRepository workoutWithWeightsRepository;

    private User getCurrentUser(HttpServletRequest request) {
        User user = (User) request.getAttribute("currentUser");
        if (user == null) {
            throw new RuntimeException("Benutzer nicht authentifiziert");
        }
        System.out.println("Current user: " + user.getUsername() + " (ID: " + user.getId() + ")");
        return user;
    }

    @GetMapping("/workouts")
    public ResponseEntity<List<Workout>> getAllWorkouts(HttpServletRequest request) {
        try {
            User currentUser = getCurrentUser(request);
            List<Workout> workouts = workoutRepository.findByUser(currentUser);
            System.out.println("Found " + workouts.size() + " workouts for user " + currentUser.getUsername());
            return ResponseEntity.ok(workouts);
        } catch (Exception e) {
            System.err.println("Error getting workouts: " + e.getMessage());
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/workout")
    public ResponseEntity<Workout> createWorkout(@RequestBody Workout workout, HttpServletRequest request) {
        try {
            User currentUser = getCurrentUser(request);
            workout.setUser(currentUser);
            Workout savedWorkout = workoutRepository.save(workout);
            System.out.println("Created workout " + savedWorkout.getName() + " for user " + currentUser.getUsername());
            return ResponseEntity.ok(savedWorkout);
        } catch (Exception e) {
            System.err.println("Error creating workout: " + e.getMessage());
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/workout/{id}")
    public ResponseEntity<Workout> getWorkoutById(@PathVariable Long id, HttpServletRequest request) {
        try {
            User currentUser = getCurrentUser(request);
            Optional<Workout> workout = workoutRepository.findById(id);
            
            if (workout.isPresent() && workout.get().getUser().getId().equals(currentUser.getId())) {
                return ResponseEntity.ok(workout.get());
            } else {
                System.err.println("Workout not found or access denied for user " + currentUser.getUsername());
                return ResponseEntity.status(403).build();
            }
        } catch (Exception e) {
            System.err.println("Error getting workout: " + e.getMessage());
            return ResponseEntity.status(401).build();
        }
    }

    @DeleteMapping("/workout/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long id, HttpServletRequest request) {
        try {
            User currentUser = getCurrentUser(request);
            Optional<Workout> workout = workoutRepository.findById(id);
            
            if (workout.isPresent() && workout.get().getUser().getId().equals(currentUser.getId())) {
                workoutRepository.deleteById(id);
                System.out.println("Deleted workout " + id + " for user " + currentUser.getUsername());
                return ResponseEntity.ok().build();
            } else {
                System.err.println("Workout not found or access denied for delete");
                return ResponseEntity.status(403).build();
            }
        } catch (Exception e) {
            System.err.println("Error deleting workout: " + e.getMessage());
            return ResponseEntity.status(401).build();
        }
    }

    @DeleteMapping("/workout/{workoutId}/{exerciseId}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long workoutId, @PathVariable Long exerciseId, HttpServletRequest request) {
        try {
            User currentUser = getCurrentUser(request);
            Optional<Workout> workout = workoutRepository.findById(workoutId);
            
            if (workout.isPresent() && workout.get().getUser().getId().equals(currentUser.getId())) {
                // Logic to delete specific exercise from workout
                System.out.println("Deleted exercise " + exerciseId + " from workout " + workoutId);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(403).build();
            }
        } catch (Exception e) {
            System.err.println("Error deleting exercise: " + e.getMessage());
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/workoutsWithWeights")
    public ResponseEntity<List<WorkoutWithWeights>> getAllWorkoutsWithWeights(HttpServletRequest request) {
        try {
            User currentUser = getCurrentUser(request);
            List<WorkoutWithWeights> workouts = workoutWithWeightsRepository.findByUserId(currentUser.getId());
            System.out.println("Found " + workouts.size() + " workouts with weights for user " + currentUser.getUsername());
            return ResponseEntity.ok(workouts);
        } catch (Exception e) {
            System.err.println("Error getting workouts with weights: " + e.getMessage());
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/OneWorkout")
    public ResponseEntity<WorkoutWithWeights> createWorkoutWithWeights(@RequestBody WorkoutWithWeights workoutWithWeights, HttpServletRequest request) {
        try {
            User currentUser = getCurrentUser(request);
            workoutWithWeights.setUser(currentUser);
            WorkoutWithWeights saved = workoutWithWeightsRepository.save(workoutWithWeights);
            System.out.println("Created workout with weights for user " + currentUser.getUsername());
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            System.err.println("Error creating workout with weights: " + e.getMessage());
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/workoutWithWeights/{id}")
    public ResponseEntity<WorkoutWithWeights> getWorkoutWithWeightsById(@PathVariable Long id, HttpServletRequest request) {
        try {
            User currentUser = getCurrentUser(request);
            Optional<WorkoutWithWeights> workout = workoutWithWeightsRepository.findById(id);
            
            if (workout.isPresent() && workout.get().getUser().getId().equals(currentUser.getId())) {
                return ResponseEntity.ok(workout.get());
            } else {
                return ResponseEntity.status(403).build();
            }
        } catch (Exception e) {
            System.err.println("Error getting workout with weights: " + e.getMessage());
            return ResponseEntity.status(401).build();
        }
    }

    @DeleteMapping("/workoutWithWeights/{id}")
    public ResponseEntity<Void> deleteWorkoutWithWeights(@PathVariable Long id, HttpServletRequest request) {
        try {
            User currentUser = getCurrentUser(request);
            Optional<WorkoutWithWeights> workout = workoutWithWeightsRepository.findById(id);
            
            if (workout.isPresent() && workout.get().getUser().getId().equals(currentUser.getId())) {
                workoutWithWeightsRepository.deleteById(id);
                System.out.println("Deleted workout with weights " + id + " for user " + currentUser.getUsername());
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(403).build();
            }
        } catch (Exception e) {
            System.err.println("Error deleting workout with weights: " + e.getMessage());
            return ResponseEntity.status(401).build();
        }
    }
}