package com.htwberlin.webtech_projekt.Service;

import com.htwberlin.webtech_projekt.Model.WorkoutWithWeights;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.htwberlin.webtech_projekt.Repository.WorkoutWithWeightsRepo;

@Service
public class WorkoutServiceWithWeights {

    private final WorkoutWithWeightsRepo repository;

    public WorkoutServiceWithWeights(WorkoutWithWeightsRepo repository) {
        this.repository = repository;
    }

    public WorkoutWithWeights save(WorkoutWithWeights workout) {
        return repository.save(workout);
    }

    public WorkoutWithWeights findById(Long id) {
        return repository.findById(id).orElse(null);
    }
}