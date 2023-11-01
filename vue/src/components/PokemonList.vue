<template>
  <div>
    <ul>
        <li v-for="pokemon in pokemonArray" v-bind:key="pokemon.id">
            <router-link 
                v-bind:to="{
                    name: 'detail',
                    params: {
                    id: pokemon.id,
                    name: pokemon.name,
                    url: pokemon.url
                    },
                }" 
            >
                {{ pokemon.name }}
            </router-link>
        </li>
    </ul>
    <button v-if="$store.state.startingVal != '0'" @click="getLastPokemon">Previous</button>   &nbsp; &nbsp;
    <button @click="getNextPokemon">Next</button>
  </div>
</template>

<script>
import pokemonService from '../services/PokemonService';
export default {
    name: 'pokemon-list',
    // set up a data to hold array of pokemon
    data() {
        return {
            pokemonArray : []
        }
    },
    methods: {
        getNextPokemon(){
            let start = this.$store.state.startingVal;
            let end = this.$store.state.endingVal;
            start += 20;
            end += 20;
            pokemonService.getNewPokemon(start, end).then (response => {
                this.pokemonArray = response.data;
                let values = {
                    startingVal: start,
                    endingVal: end
                };
                this.$store.commit("GET_NEXT_PREVIOUS", values);
            })
        },
        getLastPokemon(){
            let start = this.$store.state.startingVal;
            let end = this.$store.state.endingVal;
            start -= 20;
            end -= 20;
            pokemonService.getNewPokemon(start, end).then (response => {
                this.pokemonArray = response.data;
                let values = {
                    startingVal: start,
                    endingVal: end
                };
                this.$store.commit("GET_NEXT_PREVIOUS", values);
            })
        }
    },
    created() {
        // use a service to query the backend
        pokemonService.getAllPokemon() // this returns a promise
            .then (response => {
                // console.log(response);
                this.pokemonArray = response.data;
                this.pokemonArray = this.pokemon.name.charAt(0).toUpperCase() 
                + this.pokemon.name.slice(1);
            })
    }
}
</script>

<style>
    ul > li {
        list-style-type: none;
    }
</style>