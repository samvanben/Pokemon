<template>
  <div id="app">
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'pokemon' }">List all Pokémon</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
      <div id = "search">
        <input type="text" v-on:keyup.enter="searchPokemon" v-model="searchQuery" placeholder="Search for a Pokémon!"> &nbsp;
        <button @click="searchPokemon">Search</button>
      </div>
    </div>
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'nav',
  data() {
        return {
            searchQuery: '',
        }
    },
    methods:{
      searchPokemon(){
        this.$router.push(`/detail/${this.searchQuery}`);
        window.location.reload();
      }
    }
}
</script>

<style>
#app {
  text-align: center;
}
</style>
