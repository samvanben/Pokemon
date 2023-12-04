<template>
  <div class="home">
    <h1>Random Pokémon Team Generator</h1>
    <div :class="displayBackground ? 'list' : ''">
      <div class="random" v-for="pokemon in pokemonTeam" :key="pokemon.id">
        <h2 v-show="showPokemon">{{ "Pokédex: " + "#" + pokemon.id }}</h2>
        <h2 class="name">{{ pokemon.name }}</h2>
        <!-- <p>Height: {{ pokemon.height }}</p> -->
        <img v-bind:src="pokemon.sprites.front_default" /> &nbsp;
        <!-- <img :src="pokemon.sprites.back_default" /> -->
      </div>
    </div>
    <button
      v-if="this.pokemonTeam.length < 6"
      v-on:click="getRandomPokemonTeam"
    >
      Random Pokémon!
    </button>
  </div>
</template>

<script>
import PokemonService from "../services/PokemonService";
export default {
  name: "home",
  components: {},
  data() {
    return {
      pokemonTeam: [],
      pokemon: {},
      displayBackground: false,
    };
  },
  methods: {
    getRandomPokemonTeam() {
      PokemonService.getRandomPokemon().then((response) => {
        let pokemon = response.data;
        let firstLetter = pokemon.name.charAt(0).toUpperCase();
        let restWord = pokemon.name.slice(1);
        pokemon.name = firstLetter + restWord;
        this.pokemonTeam.push(pokemon);
        this.toggleBackground();
        // re-runs method until there are 6 total pokemon
        if (this.pokemonTeam.length < 6) {
          this.getRandomPokemonTeam();
        }
      });
    },
    toggleBackground(){
      this.displayBackground = true;
    },
  },
};
</script>
<style scoped>
h1{
background: #f8f0d9;
  outline-style: solid;
  border-radius: 5px;
  color: #5c5a52;
  border-width: 5px;
  margin-left: 750px;
  margin-right: 750px;
  text-shadow: 1px 1.5px 0px #acb5ac;
  padding-bottom: 5px;
  padding-top: 1px;
}
.list{
  display: flex;
  justify-content: space-evenly;
  background: #f8f0d9;
  border-style: solid;
  border-color: #a6a7a1;
  border-radius: 5px;
  outline-style: solid;
  outline-color: #5b544a;
  margin-left: 150px;
  margin-right: 150px;
}
.name{
  color: #5c5a52;
  text-shadow: 1px 1.5px 0px #acb5ac;
}
</style>