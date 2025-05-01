package com.htwberlin.webtech_projekt.Service;

import com.htwberlin.webtech_projekt.Model.Workout;
import com.htwberlin.webtech_projekt.Model.WorkoutWithWeights;
import org.springframework.stereotype.Service;

@Service
public class WorkoutService {

    public static WorkoutWithWeights save(WorkoutWithWeights workout) {
        return workout;
    }
}