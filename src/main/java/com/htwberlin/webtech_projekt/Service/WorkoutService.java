package com.htwberlin.webtech_projekt.Service;

import com.htwberlin.webtech_projekt.Model.WorkoutWithWeights;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.htwberlin.webtech_projekt.Repository.WorkoutWithWeightsRepo;
import com.htwberlin.webtech_projekt.Repository.WorkoutRepo;
import com.htwberlin.webtech_projekt.Model.Workout;

@Service
public class WorkoutService {

    private final WorkoutRepo repository;

    public WorkoutService(WorkoutRepo repository) {
        this.repository = repository;
    }

    public Workout save(Workout workout) {
        return repository.save(workout);
    }

    public Workout findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}