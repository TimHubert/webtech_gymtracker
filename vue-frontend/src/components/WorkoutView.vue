<template>
  <div>
    <h1>Workout: {{ workout.name || 'Lädt...' }}</h1>
    <p v-if="workout.date"><strong>Datum:</strong> {{ workout.date }}</p>

    <h2 v-if="workout.exercises && workout.exercises.length">Übungen</h2>
    <ul v-if="workout.exercises && workout.exercises.length">
      <li v-for="exercise in workout.exercises" :key="exercise.name">
        {{ exercise.name }} – {{ exercise.equipment }} – {{ exercise.targetMuscleGroup }}
      </li>
    </ul>

    <div v-else>
      <p>Keine Übungen gefunden.</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const workout = ref({
  name: '',
  date: '',
  exercises: []
})

onMounted(() => {
  fetch('http://localhost:8080/workout')
    .then(response => response.json())
    .then(data => {
      console.log('Workout-Daten:', data);
      workout.value = {
        name: data.name,
        date: data.date,
        exercises: data.exercise
      }
    })
    .catch(error => console.error('Fehler beim Abrufen des Workouts:', error))
})
</script>
