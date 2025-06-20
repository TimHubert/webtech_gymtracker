package com.htwberlin.webtech_projekt.Service;

import com.htwberlin.webtech_projekt.Model.WorkoutWithWeights;
import com.htwberlin.webtech_projekt.Model.WeightsAndReps;
import com.htwberlin.webtech_projekt.Repository.WorkoutWithWeightsRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkoutServiceWithWeightsTest {

    @Mock
    private WorkoutWithWeightsRepo workoutWithWeightsRepo;

    @InjectMocks
    private WorkoutServiceWithWeights workoutServiceWithWeights;

    private WorkoutWithWeights workoutWithWeights;

    @BeforeEach
    void setUp() {
        workoutWithWeights = new WorkoutWithWeights();
        workoutWithWeights.setId(1L);
        workoutWithWeights.setDate(LocalDate.now());
        
        // Add weights list to avoid NullPointerException
        List<WeightsAndReps> weights = new ArrayList<>();
        weights.add(new WeightsAndReps(Arrays.asList(10, 8, 6), Arrays.asList(80.0, 85.0, 90.0)));
        workoutWithWeights.setWeights(weights);
    }

    @Test
    void testSave() {
        // Given
        when(workoutWithWeightsRepo.save(any(WorkoutWithWeights.class))).thenReturn(workoutWithWeights);

        // When
        WorkoutWithWeights result = workoutServiceWithWeights.save(workoutWithWeights);

        // Then
        assertNotNull(result);
        assertEquals(LocalDate.now(), result.getDate());
        verify(workoutWithWeightsRepo).save(workoutWithWeights);
    }

    @Test
    void testFindById_Found() {
        // Given
        when(workoutWithWeightsRepo.findById(1L)).thenReturn(Optional.of(workoutWithWeights));

        // When
        WorkoutWithWeights result = workoutServiceWithWeights.findById(1L);

        // Then
        assertNotNull(result);
        assertEquals(LocalDate.now(), result.getDate());
        verify(workoutWithWeightsRepo).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        // Given
        when(workoutWithWeightsRepo.findById(999L)).thenReturn(Optional.empty());

        // When
        WorkoutWithWeights result = workoutServiceWithWeights.findById(999L);

        // Then
        assertNull(result);
        verify(workoutWithWeightsRepo).findById(999L);
    }

    @Test
    void testFindByIdWithWeights_Found() {
        // Given
        when(workoutWithWeightsRepo.findById(1L)).thenReturn(Optional.of(workoutWithWeights));

        // When
        WorkoutWithWeights result = workoutServiceWithWeights.findByIdWithWeights(1L);

        // Then
        assertNotNull(result);
        assertEquals(LocalDate.now(), result.getDate());
        verify(workoutWithWeightsRepo).findById(1L);
    }

    @Test
    void testFindByIdWithWeights_NotFound() {
        // Given
        when(workoutWithWeightsRepo.findById(999L)).thenReturn(Optional.empty());

        // When
        WorkoutWithWeights result = workoutServiceWithWeights.findByIdWithWeights(999L);

        // Then
        assertNull(result);
        verify(workoutWithWeightsRepo).findById(999L);
    }

    @Test
    void testDeleteById() {
        // Given
        Long workoutId = 1L;

        // When
        workoutServiceWithWeights.deleteById(workoutId);

        // Then
        verify(workoutWithWeightsRepo).deleteById(workoutId);
    }
}
