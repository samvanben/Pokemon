<template>
  <div>
    <h2 v-show="showPokemon">{{"Pokédex: " + "#" + pokemon.id }}</h2>
    <h2>{{ pokemon.name }}</h2>
    <!-- <p>Height: {{ pokemon.height }}</p> -->
    <img v-bind:src="spriteImgFront" /> &nbsp;
    <img :src="spriteImgBack" />
    <div>
        <!-- <button v-on:click="savePokemon">Save as Favorite?</button> &nbsp; | &nbsp; -->
        <button v-if="this.$route.path == '/'" v-on:click="getRandomPokemon" >Random Pokémon!</button>
        <router-link to="/pokemon" tag="button" v-if="this.$route.path != '/'">Back to List</router-link>
    </div>
  </div>
</template>

<script>
import PokemonService from '../services/PokemonService';
export default {
    name: 'pokemon-detail',
    props: {
        id: Number, 
        String,
        name: String,
        // rand: Number,
    },
    data() {
        return {
            pokemon: {},
            spriteImgFront: "",
            spriteImgBack: "",
            showPokemon: false,
        }
    },
    methods:{
        savePokemon(){
            let pokemonToSave = {
                id: this.id,
                name: this.name,
                url: this.$route.params.url,
            };
            PokemonService.saveFavorite(pokemonToSave).then(() => {
                alert(`${this.name} has been saved to Favorites `);
                this.$router.push({name: "pokemon"});
                // console.log(response);
            });
        },
        getRandomPokemon(){
            PokemonService.getRandomPokemon().then(response => {
            this.pokemon = response.data;
            this.spriteImgFront = response.data.sprites.front_default;
            this.spriteImgBack = response.data.sprites.back_default;
            this.showPokemon = true;
            let firstLetter = this.pokemon.name.charAt(0).toUpperCase();
            let restWord = this.pokemon.name.slice(1)
            this.pokemon.name = firstLetter + restWord;
            });
        }
    },
    created() {
        PokemonService.getPokemonByName(this.name)
        .then(response => {
            this.pokemon = response.data;
            this.spriteImgFront = response.data.sprites.front_default;
            this.spriteImgBack = response.data.sprites.back_default;
            this.pokemon.name = this.pokemon.name.charAt(0).toUpperCase() 
                + this.pokemon.name.slice(1);
            this.showPokemon = true;
        })
    }
};
</script>

<style>

</style>