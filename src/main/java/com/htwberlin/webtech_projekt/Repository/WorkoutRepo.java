package com.htwberlin.webtech_projekt.Repository;

import com.htwberlin.webtech_projekt.Model.WorkoutWithWeights;
import org.springframework.data.jpa.repository.JpaRepository;
import com.htwberlin.webtech_projekt.Model.Workout;

public interface WorkoutRepo extends JpaRepository<Workout, Long> {
}