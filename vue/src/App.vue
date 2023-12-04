<template>
  <div id="app">
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=VT323&display=swap"
      rel="stylesheet"
    />
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'pokemon' }">List all Pokémon</router-link
      >&nbsp;|&nbsp;
      <router-link
        v-bind:to="{ name: 'logout' }"
        v-if="$store.state.token != ''"
        >Logout</router-link
      >
      <div id="search">
        <input
          type="text"
          v-on:keyup.enter="searchPokemon"
          v-model="searchQuery"
          placeholder="Search for a Pokémon!"
        />
        &nbsp;
        <button @click="searchPokemon">SEARCH</button>
      </div>
    </div>
    <router-view />
  </div>
</template>

<script>
export default {
  name: "nav",
  data() {
    return {
      searchQuery: "",
    };
  },
  methods: {
    searchPokemon() {
      this.$router.push(`/detail/${this.searchQuery}`);
      window.location.reload();
    },
  },
};
</script>

<style>
#app {
  text-align: center;
}
body {
  font-family: "VT323", monospace;
  background-color: #3888f0;
}
button {
  background: #f6f1d9;
  color: #f6f1d9;
  font-size: 20px;
  -webkit-text-stroke-width: 1px;
  -webkit-text-stroke-color: #27302d;
  border-style: solid;
  border-radius: 5px;
  border-color: #9f9f96;
  border-width: 2px;
  border-bottom-width: 6px;
  outline-color: #27302d;
  outline-style: solid;
  outline-width: 3px;
}
#search {
  margin-top: 10px;
}
</style>
