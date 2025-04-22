<template>
  <div class="gym-tracker">
    <h1>🏋️ Gym Tracker</h1>

    
    <form @submit.prevent="übungHinzufügen">
      <input v-model="neueÜbung.name" placeholder="Übung" required />
      <input v-model.number="neueÜbung.wdh" type="number" placeholder="Wiederholungen" required />
      <input v-model.number="neueÜbung.gewicht" type="number" placeholder="Gewicht (kg)" required />
      <button type="submit">Hinzufügen</button>
    </form>

    <ul>
      <li v-for="(übung, index) in übungen" :key="index">
        {{ übung.name }} – {{ übung.wdh }} Wdh mit {{ übung.gewicht }} kg
        <button @click="löschen(index)">❌</button>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'GymTracker',
  data() {
    return {
      neueÜbung: {
        name: '',
        wdh: null,
        gewicht: null,
      },
      übungen: [],
    }
  },
  methods: {
    übungHinzufügen() {
      if (this.neueÜbung.name && this.neueÜbung.wdh && this.neueÜbung.gewicht) {
        this.übungen.push({ ...this.neueÜbung })
        this.neueÜbung = { name: '', wdh: null, gewicht: null }
      }
    },
    löschen(index) {
      this.übungen.splice(index, 1)
    },
  },
}
</script>

<style scoped>
.gym-tracker {
  padding: 1.5rem;
  font-family: sans-serif;
}
input {
  margin: 0.5rem 0.5rem 0.5rem 0;
}
button {
  margin-left: 0.5rem;
}
</style>
