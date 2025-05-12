package com.htwberlin.webtech_projekt.Model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Exercise> exercise;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Workout() {
    }

    public Workout(String name, List<Exercise> exercise) {
        this.name = name;
        this.exercise = exercise;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Exercise> getExercise() {
        return exercise;
    }

    public void setExercise(List<Exercise> exercise) {
        this.exercise = exercise;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "name='" + name + '\'' +
                ", exercise=" + exercise +
                ", user=" + (user != null ? user.getUsername() : "null") +
                '}';
    }
}
