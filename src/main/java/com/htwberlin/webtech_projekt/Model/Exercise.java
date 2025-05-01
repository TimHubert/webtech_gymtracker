package com.htwberlin.webtech_projekt.Model;
import java.util.ArrayList;
import java.util.List;

public class Exercise {
    private String name;
    private String description = "Keine Beschreibung vorhanden";
    private String equipment;
    private String targetMuscleGroup;

    public Exercise() {
    }

    public Exercise(String name, String equipment, String targetMuscleGroup) {
        this.name = name;
        this.equipment = equipment;
        this.targetMuscleGroup = targetMuscleGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTargetMuscleGroup() {
        return targetMuscleGroup;
    }

    public void setTargetMuscleGroup(String targetMuscleGroup) {
        this.targetMuscleGroup = targetMuscleGroup;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
