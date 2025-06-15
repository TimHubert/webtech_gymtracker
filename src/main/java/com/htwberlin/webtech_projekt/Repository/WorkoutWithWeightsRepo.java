package com.htwberlin.webtech_projekt.Repository;

import com.htwberlin.webtech_projekt.Model.User;
import com.htwberlin.webtech_projekt.Model.WorkoutWithWeights;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutWithWeightsRepo extends JpaRepository<WorkoutWithWeights, Long> {
    List<WorkoutWithWeights> findByUser(User user);
    List<WorkoutWithWeights> findByUserId(Long userId);
}