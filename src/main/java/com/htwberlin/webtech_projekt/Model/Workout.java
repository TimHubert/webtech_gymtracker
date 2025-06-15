package com.htwberlin.webtech_projekt.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private boolean show;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore  // Verhindert JSON-Serialisierung der User-Referenz
    private User user;

    public Workout() {
    }

    public Workout(String name, List<Exercise> exercise, boolean show) {
        this.name = name;
        this.exercise = exercise;
        this.show = show;
    }

    public Workout(String name, List<Exercise> exercise, boolean show, User user) {
        this.name = name;
        this.exercise = exercise;
        this.show = show;
        this.user = user;
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

    public boolean getShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", exercise=" + exercise +
                ", show=" + show +
                ", user=" + (user != null ? user.getUsername() : "null") +
                '}';
    }
}


