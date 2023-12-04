<template>
  <div>
    <ul class="border">
      <li v-for="pokemon in pokemonArray" v-bind:key="pokemon.id">
        <router-link
          v-bind:to="{
            name: 'detail',
            params: {
              id: pokemon.id,
              name: pokemon.name,
              url: pokemon.url,
            },
          }"
          tag="img"
          :src="pokemon.sprites.front_default"
          id="image"
        >
          {{ pokemon.name }}
        </router-link>
        <router-link
          v-bind:to="{
            name: 'detail',
            params: {
              id: pokemon.id,
              name: pokemon.name,
              url: pokemon.url,
            },
          }"
        >
          {{ pokemon.name }}
        </router-link>
      </li>
    </ul>
    <button v-if="$store.state.startingVal != '0'" @click="getLastPokemon">
      PREVIOUS
    </button>
    &nbsp; &nbsp;
    <button @click="getNextPokemon">NEXT</button>
  </div>
</template>

<script>
import pokemonService from "../services/PokemonService";
export default {
  name: "pokemon-list",
  // set up a data to hold array of pokemon
  data() {
    return {
      pokemonArray: [],
    };
  },
  methods: {
    getNextPokemon() {
      let start = this.$store.state.startingVal;
      let end = this.$store.state.endingVal;
      start += 20;
      end += 20;
      pokemonService.getNewPokemon(start, end).then((response) => {
        this.pokemonArray = response.data;
        let values = {
          startingVal: start,
          endingVal: end,
        };
        this.$store.commit("GET_NEXT_PREVIOUS", values);
      });
    },
    getLastPokemon() {
      let start = this.$store.state.startingVal;
      let end = this.$store.state.endingVal;
      start -= 20;
      end -= 20;
      pokemonService.getNewPokemon(start, end).then((response) => {
        this.pokemonArray = response.data;
        let values = {
          startingVal: start,
          endingVal: end,
        };
        this.$store.commit("GET_NEXT_PREVIOUS", values);
      });
    },
  },
  created() {
    // use a service to query the backend
    pokemonService
      .getAllPokemon() // this returns a promise
      .then((response) => {
        // console.log(response);
        this.pokemonArray = response.data;
      });
  },
};
</script>

<style scoped>
#image:hover {
  cursor: pointer;
}
ul > li {
  list-style-type: none;
}
ul {
  padding-top: 10px;
  margin-right: 150px;
  margin-left: 150px;
  display: flex;
  flex-wrap: wrap;
  flex-direction: row;
  justify-content: center;
  background-image: url(../../public/Images/pokemon_pc_background.png);
  background-color: #7dce82;
  border-style: outset;
  border-color: #c8f7c2;
  border-width: 8px;
  border-radius: 5px;
  outline-style: solid;
  outline-color: #27302d;
  outline-width: 5px;
}
li {
  width: 20vw;
}
li a {
  display: block;
  color: #5b544a;
  text-align: center;
  padding: 16px;
  text-decoration: none;

  margin-left: 100px;
  margin-right: 100px;
  margin-top: 7px;
  margin-bottom: 17px;
  padding-top: 1px;
  padding-bottom: 1px;
  border-style: solid;
  border-color: #a6a7a1;
  border-radius: 5px;
  outline-style: solid;
  outline-color: #5b544a;
  background: #f8f0d9;
}
#image{
  outline-style: solid;
  outline-color: #5c5a52;
  outline-width: 3px;
  border-style: solid;
  border-color: #9f9f96;
  border-radius: 5px;
  border-width: 3px;
  background-color: #f8f0d9;
}
</style>