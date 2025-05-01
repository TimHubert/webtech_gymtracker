package com.htwberlin.webtech_projekt.Repository;

import com.htwberlin.webtech_projekt.Model.WorkoutWithWeights;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutWithWeightsRepo extends JpaRepository<WorkoutWithWeights, Long> {
}