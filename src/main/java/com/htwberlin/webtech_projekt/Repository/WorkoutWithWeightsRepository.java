package com.htwberlin.webtech_projekt.Repository;

import com.htwberlin.webtech_projekt.Model.WorkoutWithWeights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutWithWeightsRepository extends JpaRepository<WorkoutWithWeights, Long> {
    
    List<WorkoutWithWeights> findByUserId(Long userId);
}