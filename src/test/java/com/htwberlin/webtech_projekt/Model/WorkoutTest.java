package com.htwberlin.webtech_projekt.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkoutTest {

    private Workout workout;
    private List<Exercise> exercises;
    private User user;

    @BeforeEach
    void setUp() {
        workout = new Workout();
        exercises = new ArrayList<>();
        exercises.add(new Exercise("Push-up", "Bodyweight", "Chest"));
        exercises.add(new Exercise("Squat", "Bodyweight", "Legs"));
        
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
    }

    @Test
    void testDefaultConstructor() {
        // When
        Workout newWorkout = new Workout();

        // Then
        assertNotNull(newWorkout);
        assertNull(newWorkout.getId());
        assertNull(newWorkout.getName());
        assertNull(newWorkout.getExercise());
        assertFalse(newWorkout.getShow());
        assertNull(newWorkout.getUser());
    }

    @Test
    void testConstructorWithoutUser() {
        // When
        Workout newWorkout = new Workout("Push Day", exercises, true);

        // Then
        assertNotNull(newWorkout);
        assertEquals("Push Day", newWorkout.getName());
        assertEquals(exercises, newWorkout.getExercise());
        assertTrue(newWorkout.getShow());
        assertNull(newWorkout.getUser());
    }

    @Test
    void testConstructorWithUser() {
        // When
        Workout newWorkout = new Workout("Pull Day", exercises, false, user);

        // Then
        assertNotNull(newWorkout);
        assertEquals("Pull Day", newWorkout.getName());
        assertEquals(exercises, newWorkout.getExercise());
        assertFalse(newWorkout.getShow());
        assertEquals(user, newWorkout.getUser());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Long id = 1L;
        String name = "Leg Day";
        boolean show = true;

        // When
        workout.setId(id);
        workout.setName(name);
        workout.setExercise(exercises);
        workout.setShow(show);
        workout.setUser(user);

        // Then
        assertEquals(id, workout.getId());
        assertEquals(name, workout.getName());
        assertEquals(exercises, workout.getExercise());
        assertTrue(workout.getShow());
        assertEquals(user, workout.getUser());
    }

    @Test
    void testToString() {
        // Given
        workout.setId(1L);
        workout.setName("Test Workout");
        workout.setExercise(exercises);
        workout.setShow(true);
        workout.setUser(user);

        // When
        String result = workout.toString();

        // Then
        assertNotNull(result);
        assertTrue(result.contains("Test Workout"));
        assertTrue(result.contains("true"));
        assertTrue(result.contains("testuser"));
    }

    @Test
    void testToStringWithNullUser() {
        // Given
        workout.setId(1L);
        workout.setName("Test Workout");
        workout.setExercise(exercises);
        workout.setShow(true);
        workout.setUser(null);

        // When
        String result = workout.toString();

        // Then
        assertNotNull(result);
        assertTrue(result.contains("Test Workout"));
        assertTrue(result.contains("null"));
    }

    @Test
    void testExerciseList() {
        // Given
        List<Exercise> newExercises = new ArrayList<>();
        newExercises.add(new Exercise("Deadlift", "Barbell", "Back"));

        // When
        workout.setExercise(newExercises);

        // Then
        assertEquals(1, workout.getExercise().size());
        assertEquals("Deadlift", workout.getExercise().get(0).getName());
    }

    @Test
    void testEmptyExerciseList() {
        // Given
        List<Exercise> emptyList = new ArrayList<>();

        // When
        workout.setExercise(emptyList);

        // Then
        assertNotNull(workout.getExercise());
        assertTrue(workout.getExercise().isEmpty());
    }
}
