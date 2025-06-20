package com.htwberlin.webtech_projekt.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeightsAndRepsTest {

    private WeightsAndReps weightsAndReps;

    @BeforeEach
    void setUp() {
        weightsAndReps = new WeightsAndReps();
    }

    @Test
    void testDefaultConstructor() {
        // When
        WeightsAndReps newWeightsAndReps = new WeightsAndReps();

        // Then
        assertNotNull(newWeightsAndReps);
        assertNull(newWeightsAndReps.getWeights());
        assertNull(newWeightsAndReps.getReps());
    }

    @Test
    void testParameterizedConstructor() {
        // Given
        List<Integer> reps = Arrays.asList(10, 8, 6);
        List<Double> weights = Arrays.asList(80.5, 85.0, 90.0);

        // When
        WeightsAndReps newWeightsAndReps = new WeightsAndReps(reps, weights);

        // Then
        assertNotNull(newWeightsAndReps);
        assertEquals(reps, newWeightsAndReps.getReps());
        assertEquals(weights, newWeightsAndReps.getWeights());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        List<Integer> reps = Arrays.asList(12, 10, 8);
        List<Double> weights = Arrays.asList(100.0, 105.0, 110.0);

        // When
        weightsAndReps.setReps(reps);
        weightsAndReps.setWeights(weights);

        // Then
        assertEquals(reps, weightsAndReps.getReps());
        assertEquals(weights, weightsAndReps.getWeights());
    }

    @Test
    void testEmptyLists() {
        // Given
        List<Integer> emptyReps = new ArrayList<>();
        List<Double> emptyWeights = new ArrayList<>();

        // When
        weightsAndReps.setReps(emptyReps);
        weightsAndReps.setWeights(emptyWeights);

        // Then
        assertNotNull(weightsAndReps.getReps());
        assertNotNull(weightsAndReps.getWeights());
        assertTrue(weightsAndReps.getReps().isEmpty());
        assertTrue(weightsAndReps.getWeights().isEmpty());
    }

    @Test
    void testSingleValues() {
        // Given
        List<Integer> singleRep = Arrays.asList(15);
        List<Double> singleWeight = Arrays.asList(75.5);

        // When
        weightsAndReps.setReps(singleRep);
        weightsAndReps.setWeights(singleWeight);

        // Then
        assertEquals(1, weightsAndReps.getReps().size());
        assertEquals(1, weightsAndReps.getWeights().size());
        assertEquals(15, weightsAndReps.getReps().get(0));
        assertEquals(75.5, weightsAndReps.getWeights().get(0));
    }

    @Test
    void testMultipleSets() {
        // Given
        List<Integer> multipleReps = Arrays.asList(12, 10, 8, 6);
        List<Double> multipleWeights = Arrays.asList(60.0, 65.0, 70.0, 75.0);

        // When
        weightsAndReps.setReps(multipleReps);
        weightsAndReps.setWeights(multipleWeights);

        // Then
        assertEquals(4, weightsAndReps.getReps().size());
        assertEquals(4, weightsAndReps.getWeights().size());
        assertEquals(12, weightsAndReps.getReps().get(0));
        assertEquals(60.0, weightsAndReps.getWeights().get(0));
        assertEquals(6, weightsAndReps.getReps().get(3));
        assertEquals(75.0, weightsAndReps.getWeights().get(3));
    }

    @Test
    void testZeroValues() {
        // Given
        List<Integer> zeroReps = Arrays.asList(0, 0);
        List<Double> zeroWeights = Arrays.asList(0.0, 0.0);

        // When
        weightsAndReps.setReps(zeroReps);
        weightsAndReps.setWeights(zeroWeights);

        // Then
        assertEquals(0, weightsAndReps.getReps().get(0));
        assertEquals(0.0, weightsAndReps.getWeights().get(0));
    }

    @Test
    void testNullLists() {
        // When
        weightsAndReps.setReps(null);
        weightsAndReps.setWeights(null);

        // Then
        assertNull(weightsAndReps.getReps());
        assertNull(weightsAndReps.getWeights());
    }

    @Test
    void testDecimalWeights() {
        // Given
        List<Double> decimalWeights = Arrays.asList(67.5, 82.25, 95.75);
        List<Integer> reps = Arrays.asList(10, 8, 6);

        // When
        weightsAndReps.setWeights(decimalWeights);
        weightsAndReps.setReps(reps);

        // Then
        assertEquals(67.5, weightsAndReps.getWeights().get(0), 0.01);
        assertEquals(82.25, weightsAndReps.getWeights().get(1), 0.01);
        assertEquals(95.75, weightsAndReps.getWeights().get(2), 0.01);
    }

    @Test
    void testToString() {
        // Given
        List<Integer> reps = Arrays.asList(10, 8);
        List<Double> weights = Arrays.asList(80.0, 85.0);
        weightsAndReps.setReps(reps);
        weightsAndReps.setWeights(weights);

        // When
        String result = weightsAndReps.toString();

        // Then
        assertNotNull(result);
        assertTrue(result.contains("WeightsAndReps"));
        assertTrue(result.contains("reps="));
        assertTrue(result.contains("weights="));
    }
}
