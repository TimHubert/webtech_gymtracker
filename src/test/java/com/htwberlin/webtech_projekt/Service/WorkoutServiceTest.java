package com.htwberlin.webtech_projekt.Service;

import com.htwberlin.webtech_projekt.Model.Workout;
import com.htwberlin.webtech_projekt.Repository.WorkoutRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkoutServiceTest {

    @Mock
    private WorkoutRepo workoutRepo;

    @InjectMocks
    private WorkoutService workoutService;

    private Workout workout;

    @BeforeEach
    void setUp() {
        workout = new Workout();
        workout.setId(1L);
        workout.setName("Test Workout");
        workout.setShow(true);
    }

    @Test
    void testSave() {
        // Given
        when(workoutRepo.save(any(Workout.class))).thenReturn(workout);

        // When
        Workout result = workoutService.save(workout);

        // Then
        assertNotNull(result);
        assertEquals("Test Workout", result.getName());
        assertTrue(result.getShow());
        verify(workoutRepo).save(workout);
    }

    @Test
    void testFindById_Found() {
        // Given
        when(workoutRepo.findById(1L)).thenReturn(Optional.of(workout));

        // When
        Workout result = workoutService.findById(1L);

        // Then
        assertNotNull(result);
        assertEquals("Test Workout", result.getName());
        verify(workoutRepo).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        // Given
        when(workoutRepo.findById(999L)).thenReturn(Optional.empty());

        // When
        Workout result = workoutService.findById(999L);

        // Then
        assertNull(result);
        verify(workoutRepo).findById(999L);
    }

    @Test
    void testDeleteById() {
        // Given
        Long workoutId = 1L;

        // When
        workoutService.deleteById(workoutId);

        // Then
        verify(workoutRepo).deleteById(workoutId);
    }
}
