package com.htwberlin.webtech_projekt.Service;

import com.htwberlin.webtech_projekt.Model.WorkoutWithWeights;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public WorkoutWithWeights findByIdWithWeights(Long id) {
        WorkoutWithWeights workout = repository.findById(id).orElse(null);
        if (workout != null && workout.getWeights() != null) {
            workout.getWeights().size(); // Force initialization of the lazy collection
        }
        return workout;
    }

    public WorkoutWithWeights findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}