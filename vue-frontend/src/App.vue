<template>
  <div id="app">
    <div class="logo-container">
      <img src="@/assets/logo.png" alt="Logo" class="logo" />
    </div>
    <header class="header">
      <nav class="nav-container">
        <router-link to="/">Home</router-link>
        <router-link to="/tracker">Tracker</router-link>
        <router-link to="/stats">Stats</router-link>
      </nav>
    </header>
    <div class="container-wrapper">
      <div class="container">
        <main class="main-content">
          <router-view />
        </main>
      </div>
    </div>
  </div>
</template>

<style>
.logo-container {
  position: fixed;
  top: 20px;
  left: 0;
  width: 100%;
  display: flex;
  justify-content: center;
  z-index: 1001;
}

.logo {
  height: 60px;
  width: auto;
}

.header {
  position: fixed;
  padding-top: 80px;
  top: 0;
  left: 0;
  width: 100%;
  z-index: 1000;
}

.nav-container {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0.3rem;
  padding: 1rem;
  width: 40%;
  margin: 0 auto;
}

nav a {
  color: white;
  text-align: center;
  text-decoration: none;
  font-weight: bold;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  width: 100%;
  transition: background-color 0.3s;
  background-color: rgba(255, 255, 255, 0.1);
}

nav a:hover {
  background-color: rgb(255, 111, 0);
}

nav a.router-link-exact-active {
  background-color: rgb(255, 111, 0);
}

.container-wrapper {
  position: fixed;
  top: 150px;
  left: 10%;
  right: 10%;
  width: auto;
  height: calc(100vh);
  padding-bottom: 40px;
  overflow: auto;
  box-shadow: 0 0 20px rgba(13, 13, 13, 0.9);
  border-radius: 30px;
  z-index: 900;
}

.container {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  width: 80%;
  max-width: 1300px;
  min-height: 150vh;
  height: auto;
  margin-left: auto;
  margin-right: auto;
  padding: 0;
  box-sizing: border-box;
  text-align: center;
}

.main-content {
  width: 100%;
  height: 100%;
  padding: 2rem;
  margin: 0 auto;
  margin-bottom: 30px;
  border-radius: 10px;
  overflow-y: visible;
}

body {
  overflow-y: auto;
}
</style>
