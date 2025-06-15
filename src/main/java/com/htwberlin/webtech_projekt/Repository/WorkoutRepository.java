package com.htwberlin.webtech_projekt.Repository;

import com.htwberlin.webtech_projekt.Model.User;
import com.htwberlin.webtech_projekt.Model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    
    List<Workout> findByUser(User user);
    
    List<Workout> findByUserId(Long userId);
}