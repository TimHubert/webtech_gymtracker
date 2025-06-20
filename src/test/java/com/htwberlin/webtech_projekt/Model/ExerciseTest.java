package com.htwberlin.webtech_projekt.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseTest {

    private Exercise exercise;

    @BeforeEach
    void setUp() {
        exercise = new Exercise();
    }

    @Test
    void testDefaultConstructor() {
        // When
        Exercise newExercise = new Exercise();

        // Then
        assertNotNull(newExercise);
        assertNull(newExercise.getId());
        assertNull(newExercise.getName());
        assertEquals("Keine Beschreibung vorhanden", newExercise.getDescription());
        assertNull(newExercise.getEquipment());
        assertNull(newExercise.getTargetMuscleGroup());
    }

    @Test
    void testParameterizedConstructor() {
        // When
        Exercise newExercise = new Exercise("Bench Press", "Barbell", "Chest");

        // Then
        assertNotNull(newExercise);
        assertEquals("Bench Press", newExercise.getName());
        assertEquals("Barbell", newExercise.getEquipment());
        assertEquals("Chest", newExercise.getTargetMuscleGroup());
        assertEquals("Keine Beschreibung vorhanden", newExercise.getDescription());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Long id = 1L;
        String name = "Squats";
        String description = "A lower body exercise";
        String equipment = "Barbell";
        String targetMuscleGroup = "Legs";

        // When
        exercise.setId(id);
        exercise.setName(name);
        exercise.setDescription(description);
        exercise.setEquipment(equipment);
        exercise.setTargetMuscleGroup(targetMuscleGroup);

        // Then
        assertEquals(id, exercise.getId());
        assertEquals(name, exercise.getName());
        assertEquals(description, exercise.getDescription());
        assertEquals(equipment, exercise.getEquipment());
        assertEquals(targetMuscleGroup, exercise.getTargetMuscleGroup());
    }

    @Test
    void testToString() {
        // Given
        exercise.setName("Push-up");
        exercise.setEquipment("Bodyweight");
        exercise.setTargetMuscleGroup("Chest");

        // When
        String result = exercise.toString();

        // Then
        assertNotNull(result);
        // toString() ruft super.toString() auf, daher nur prüfen ob nicht null/leer
        assertFalse(result.isEmpty());
    }

    @Test
    void testDescriptionDefault() {
        // When
        Exercise newExercise = new Exercise("Test", "Test Equipment", "Test Muscle");

        // Then
        assertEquals("Keine Beschreibung vorhanden", newExercise.getDescription());
    }

    @Test
    void testDescriptionOverride() {
        // Given
        exercise.setDescription("Custom description");

        // When
        String description = exercise.getDescription();

        // Then
        assertEquals("Custom description", description);
    }
}