package com.htwberlin.webtech_projekt.Controller;

import com.htwberlin.webtech_projekt.Model.User;
import com.htwberlin.webtech_projekt.Model.Workout;
import com.htwberlin.webtech_projekt.Model.WorkoutWithWeights;
import com.htwberlin.webtech_projekt.Repository.WorkoutRepo;
import com.htwberlin.webtech_projekt.Repository.WorkoutWithWeightsRepo;
import com.htwberlin.webtech_projekt.Service.UserService;
import com.htwberlin.webtech_projekt.Service.WorkoutServiceWithWeights;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.htwberlin.webtech_projekt.Model.WeightsAndReps;
import com.htwberlin.webtech_projekt.Service.WorkoutService;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import com.htwberlin.webtech_projekt.Model.Exercise;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/workout")
    public ResponseEntity<Workout> getWorkout() {
        // Demo-Workout für Testzwecke
        LocalDate date = LocalDate.of(2024, 2, 2);
        List<Exercise> exercise = new ArrayList<>();
        exercise.add(new Exercise("Bench Press", "Bench", "Chest"));
        exercise.add(new Exercise("Triceps Extensions", "Cable Tower", "Triceps"));
        exercise.add(new Exercise("Shoulder Press", "Machine", "Shoulders"));
        Workout workout = new Workout("Push", exercise, true);
        return ResponseEntity.ok(workout);
    }

    @PostMapping("/workout")
    public ResponseEntity<?> createWorkout(@RequestBody Workout workout, @RequestHeader("Authorization") String authHeader) {
        try {
            User currentUser = userService.getCurrentUserFromToken(authHeader);
            
            if (workout.getName() == null || workout.getName().isEmpty()) {
                return ResponseEntity.badRequest().body("Name des Workouts ist erforderlich");
            }
            if (workout.getExercise() == null || workout.getExercise().isEmpty()) {
                return ResponseEntity.badRequest().body("Übungen sind erforderlich");
            }
            
            workout.setUser(currentUser);
            return ResponseEntity.ok(workoutService.save(workout));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Authentifizierung fehlgeschlagen");
        }
    }

    @GetMapping("/OneWorkout")
    public ResponseEntity<WorkoutWithWeights> getWorkoutWithWeights() {
        // Demo-Workout für Testzwecke
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
        WorkoutWithWeights workoutWithWeights = new WorkoutWithWeights(workout, date, weights);
        return ResponseEntity.ok(workoutWithWeights);
    }

    @PostMapping("/OneWorkout")
    @Transactional
    public ResponseEntity<?> createWorkoutWithWeights(@RequestBody WorkoutWithWeights workoutWithWeights, @RequestHeader("Authorization") String authHeader) {
        try {
            System.out.println("Creating workout with weights...");
            User currentUser = userService.getCurrentUserFromToken(authHeader);
            System.out.println("Current user: " + currentUser.getUsername() + " (ID: " + currentUser.getId() + ")");
            
            Workout incomingWorkout = workoutWithWeights.getWorkout();
            if (incomingWorkout == null || incomingWorkout.getName() == null || incomingWorkout.getName().isEmpty()) {
                return ResponseEntity.badRequest().body("Workout-Name ist erforderlich");
            }
            if (workoutWithWeights.getWeights() == null || workoutWithWeights.getWeights().isEmpty()) {
                return ResponseEntity.badRequest().body("Gewichte und Wiederholungen sind erforderlich");
            }
            
            // Schritt 1: Workout separat erstellen und speichern
            Workout newWorkout = new Workout();
            newWorkout.setName(incomingWorkout.getName());
            newWorkout.setShow(incomingWorkout.getShow());
            newWorkout.setUser(currentUser);
            
            // Übungen erstellen
            List<Exercise> newExercises = new ArrayList<>();
            if (incomingWorkout.getExercise() != null) {
                for (Exercise oldEx : incomingWorkout.getExercise()) {
                    Exercise newEx = new Exercise(
                        oldEx.getName(),
                        oldEx.getEquipment(),
                        oldEx.getTargetMuscleGroup()
                    );
                    if (oldEx.getDescription() != null) {
                        newEx.setDescription(oldEx.getDescription());
                    }
                    newExercises.add(newEx);
                }
            }
            newWorkout.setExercise(newExercises);
            
            System.out.println("Saving workout first...");
            Workout savedWorkout = workoutRepository.saveAndFlush(newWorkout); // saveAndFlush um sofort zu committen
            System.out.println("Workout saved with ID: " + savedWorkout.getId());
            
            // Schritt 2: WorkoutWithWeights erstellen (nach dem Workout-Commit)
            WorkoutWithWeights newWorkoutWithWeights = new WorkoutWithWeights();
            newWorkoutWithWeights.setUser(currentUser);
            newWorkoutWithWeights.setDate(workoutWithWeights.getDate());
            
            // Gewichte erstellen
            List<WeightsAndReps> newWeights = new ArrayList<>();
            if (workoutWithWeights.getWeights() != null) {
                for (WeightsAndReps oldWeight : workoutWithWeights.getWeights()) {
                    WeightsAndReps newWeight = new WeightsAndReps(
                        new ArrayList<>(oldWeight.getReps()),
                        new ArrayList<>(oldWeight.getWeights())
                    );
                    newWeights.add(newWeight);
                }
            }
            newWorkoutWithWeights.setWeights(newWeights);
            
            // Das bereits gespeicherte Workout zuweisen
            newWorkoutWithWeights.setWorkout(savedWorkout);
            
            System.out.println("Saving workout with weights...");
            WorkoutWithWeights savedWorkoutWithWeights = workoutWithWeightsRepository.saveAndFlush(newWorkoutWithWeights);
            System.out.println("WorkoutWithWeights saved with ID: " + savedWorkoutWithWeights.getId());
            
            return ResponseEntity.status(201).body(savedWorkoutWithWeights);
        } catch (Exception e) {
            System.err.println("Error creating workout with weights: " + e.getClass().getSimpleName() + ": " + e.getMessage());
            e.printStackTrace();
            
            return ResponseEntity.status(500).body("Fehler beim Speichern des Workouts: " + e.getMessage());
        }
    }

    @GetMapping("/workouts")
    public ResponseEntity<?> getAllWorkouts(@RequestHeader("Authorization") String authHeader) {
        try {
            User currentUser = userService.getCurrentUserFromToken(authHeader);
            List<Workout> workouts = workoutRepository.findByUserId(currentUser.getId());
            return ResponseEntity.ok(workouts);
        } catch (Exception e) {
            if (e.getMessage().contains("Authentifizierung") || e.getMessage().contains("Authorization")) {
                return ResponseEntity.status(401).body("Authentifizierung fehlgeschlagen");
            }
            return ResponseEntity.status(500).body("Fehler beim Abrufen der Workouts");
        }
    }

    @GetMapping("/workoutsWithWeights")
    public ResponseEntity<?> getAllWorkoutsWithWeights(@RequestHeader("Authorization") String authHeader) {
        try {
            User currentUser = userService.getCurrentUserFromToken(authHeader);
            System.out.println("Current user: " + currentUser.getUsername() + " (ID: " + currentUser.getId() + ")");
            List<WorkoutWithWeights> workouts = workoutWithWeightsRepository.findByUserId(currentUser.getId());
            System.out.println("Found " + workouts.size() + " workouts with weights for user " + currentUser.getUsername());
            
            // Debug: Ausgabe der Workout-Struktur
            for (WorkoutWithWeights workout : workouts) {
                System.out.println("Workout: " + workout.getId() + " - " + (workout.getWorkout() != null ? workout.getWorkout().getName() : "null workout"));
                if (workout.getWorkout() != null && workout.getWorkout().getExercise() != null) {
                    System.out.println("  Exercises: " + workout.getWorkout().getExercise().size());
                } else {
                    System.out.println("  Exercises: null or empty");
                }
            }
            
            return ResponseEntity.ok(workouts);
        } catch (Exception e) {
            System.err.println("Error getting workouts with weights: " + e.getMessage());
            e.printStackTrace();
            if (e.getMessage().contains("Authentifizierung") || e.getMessage().contains("Authorization")) {
                return ResponseEntity.status(401).body("Authentifizierung fehlgeschlagen");
            }
            return ResponseEntity.status(500).body("Fehler beim Abrufen der Workouts");
        }
    }

    @GetMapping("/workout/{id}")
    public ResponseEntity<?> getAWorkout(@PathVariable("id") Long id, @RequestHeader("Authorization") String authHeader) {
        try {
            User currentUser = userService.getCurrentUserFromToken(authHeader);
            Workout workout = workoutService.findById(id);
            
            if (workout == null) {
                return ResponseEntity.status(404).body("Workout nicht gefunden");
            }
            
            // Prüfen ob das Workout dem aktuellen Benutzer gehört
            if (workout.getUser() == null || !workout.getUser().getId().equals(currentUser.getId())) {
                return ResponseEntity.status(403).body("Zugriff verweigert");
            }
            
            return ResponseEntity.ok(workout);
        } catch (Exception e) {
            if (e.getMessage().contains("Authentifizierung") || e.getMessage().contains("Authorization")) {
                return ResponseEntity.status(401).body("Authentifizierung fehlgeschlagen");
            }
            return ResponseEntity.status(500).body("Fehler beim Abrufen des Workouts");
        }
    }

    @GetMapping("/workoutWithWeights/{id}")
    public ResponseEntity<?> getWorkoutWithWeightsById(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        try {
            User currentUser = userService.getCurrentUserFromToken(authHeader);
            WorkoutWithWeights workout = workoutServiceWithWeights.findByIdWithWeights(id);
            
            if (workout == null) {
                return ResponseEntity.status(404).body("Workout nicht gefunden");
            }
            
            // Prüfen ob das Workout dem aktuellen Benutzer gehört
            if (workout.getUser() == null || !workout.getUser().getId().equals(currentUser.getId())) {
                return ResponseEntity.status(403).body("Zugriff verweigert");
            }
            
            return ResponseEntity.ok(workout);
        } catch (Exception e) {
            if (e.getMessage().contains("Authentifizierung") || e.getMessage().contains("Authorization")) {
                return ResponseEntity.status(401).body("Authentifizierung fehlgeschlagen");
            }
            return ResponseEntity.status(500).body("Fehler beim Abrufen des Workouts");
        }
    }

    @PutMapping("/OneWorkout/{id}")
    public ResponseEntity<?> updateWorkoutWithWeights(@PathVariable Long id, @RequestBody WorkoutWithWeights workoutWithWeights, @RequestHeader("Authorization") String authHeader) {
        try {
            User currentUser = userService.getCurrentUserFromToken(authHeader);
            
            Optional<WorkoutWithWeights> existingWorkoutWithWeightsOpt = workoutWithWeightsRepository.findById(id);
            if (existingWorkoutWithWeightsOpt.isEmpty()) {
                return ResponseEntity.status(404).body("WorkoutWithWeights nicht gefunden");
            }
            
            WorkoutWithWeights existingEntity = existingWorkoutWithWeightsOpt.get();
            
            // Prüfen ob das Workout dem aktuellen Benutzer gehört
            if (existingEntity.getUser() == null || !existingEntity.getUser().getId().equals(currentUser.getId())) {
                return ResponseEntity.status(403).body("Zugriff verweigert");
            }

            existingEntity.setWeights(workoutWithWeights.getWeights());
            existingEntity.setDate(workoutWithWeights.getDate());

            WorkoutWithWeights updatedEntity = workoutWithWeightsRepository.save(existingEntity);
            return ResponseEntity.ok(updatedEntity);
        } catch (Exception e) {
            if (e.getMessage().contains("Authentifizierung") || e.getMessage().contains("Authorization")) {
                return ResponseEntity.status(401).body("Authentifizierung fehlgeschlagen");
            }
            return ResponseEntity.status(500).body("Fehler beim Aktualisieren des Workouts");
        }
    }

    @DeleteMapping("/workout/{id}")
    public ResponseEntity<?> deleteWorkout(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        try {
            User currentUser = userService.getCurrentUserFromToken(authHeader);
            
            if (!workoutRepository.existsById(id)) {
                return ResponseEntity.status(404).body("Workout nicht gefunden");
            }
            
            Workout workout = workoutService.findById(id);
            if (workout.getUser() == null || !workout.getUser().getId().equals(currentUser.getId())) {
                return ResponseEntity.status(403).body("Zugriff verweigert");
            }
            
            workoutService.deleteById(id);
            return ResponseEntity.ok("Workout wurde gelöscht");
        } catch (Exception e) {
            if (e.getMessage().contains("Authentifizierung") || e.getMessage().contains("Authorization")) {
                return ResponseEntity.status(401).body("Authentifizierung fehlgeschlagen");
            }
            return ResponseEntity.status(500).body("Fehler beim Löschen des Workouts");
        }
    }

    @DeleteMapping("/workoutWithWeights/{id}")
    public ResponseEntity<?> deleteWorkoutWithWeightsById(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        try {
            User currentUser = userService.getCurrentUserFromToken(authHeader);
            
            WorkoutWithWeights workout = workoutServiceWithWeights.findByIdWithWeights(id);
            if (workout == null) {
                return ResponseEntity.status(404).body("Workout nicht gefunden");
            }
            
            if (workout.getUser() == null || !workout.getUser().getId().equals(currentUser.getId())) {
                return ResponseEntity.status(403).body("Zugriff verweigert");
            }
            
            workoutServiceWithWeights.deleteById(id);
            return ResponseEntity.ok(workout);
        } catch (Exception e) {
            if (e.getMessage().contains("Authentifizierung") || e.getMessage().contains("Authorization")) {
                return ResponseEntity.status(401).body("Authentifizierung fehlgeschlagen");
            }
            return ResponseEntity.status(500).body("Fehler beim Löschen des Workouts");
        }
    }

    @DeleteMapping("/workout/{id}/{exId}")
    public ResponseEntity<?> deleteExercise(@PathVariable Long id, @PathVariable Long exId, @RequestHeader("Authorization") String authHeader) {
        try {
            User currentUser = userService.getCurrentUserFromToken(authHeader);
            
            if (!workoutRepository.existsById(id)) {
                return ResponseEntity.status(404).body("Workout nicht gefunden");
            }

            Workout workout = workoutService.findById(id);
            if (workout.getUser() == null || !workout.getUser().getId().equals(currentUser.getId())) {
                return ResponseEntity.status(403).body("Zugriff verweigert");
            }

            List<Exercise> exercises = workout.getExercise();
            if (exercises != null && exercises.removeIf(exercise -> exercise.getId().equals(exId))) {
                workoutService.save(workout);
            } else {
                return ResponseEntity.status(404).body("Exercise nicht gefunden");
            }

            if (exercises == null || exercises.isEmpty()) {
                workoutService.deleteById(id);
                return ResponseEntity.ok("Workout und alle Übungen wurden gelöscht");
            }

            return ResponseEntity.ok("Übung wurde aus dem Workout entfernt");
        } catch (Exception e) {
            if (e.getMessage().contains("Authentifizierung") || e.getMessage().contains("Authorization")) {
                return ResponseEntity.status(401).body("Authentifizierung fehlgeschlagen");
            }
            return ResponseEntity.status(500).body("Fehler beim Löschen der Übung");
        }
    }

    @PutMapping("/workout/{id}")
    public ResponseEntity<?> updateWorkout(@PathVariable Long id, @RequestBody Workout workout, @RequestHeader("Authorization") String authHeader) {
        try {
            User currentUser = userService.getCurrentUserFromToken(authHeader);
            
            if (!workoutRepository.existsById(id)) {
                return ResponseEntity.status(404).body("Workout nicht gefunden");
            }
            
            Workout existingWorkout = workoutRepository.findById(id).orElse(null);
            if (existingWorkout == null || existingWorkout.getUser() == null || !existingWorkout.getUser().getId().equals(currentUser.getId())) {
                return ResponseEntity.status(403).body("Zugriff verweigert");
            }
            
            existingWorkout.setShow(workout.getShow());
            workoutRepository.save(existingWorkout);
            return ResponseEntity.ok(existingWorkout);
        } catch (Exception e) {
            if (e.getMessage().contains("Authentifizierung") || e.getMessage().contains("Authorization")) {
                return ResponseEntity.status(401).body("Authentifizierung fehlgeschlagen");
            }
            return ResponseEntity.status(500).body("Fehler beim Aktualisieren des Workouts");
        }
    }
}

