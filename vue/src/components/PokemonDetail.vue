<template>
  <div>
    <div class="detail">
      <h2 id="pokedex" v-show="showPokemon">{{ "Pokédex: " + "#" + pokemon.id }}</h2>
      <h2 id="name">{{ pokemon.name }}</h2>
      <!-- <p>Height: {{ pokemon.height }}</p> -->
      <div id="image">
      <img v-bind:src="spriteImgFront" /> &nbsp;
      <img :src="spriteImgBack" />
      </div>
      </div>
    <div>
      <router-link to="/pokemon" tag="button" v-if="this.$route.path != '/'"
        >BACK TO LIST</router-link
      >
    </div>
  </div>
</template>

<script>
import PokemonService from "../services/PokemonService";
export default {
  name: "pokemon-detail",
  props: {
    id: Number,
    String,
    name: String,
    // rand: Number,
  },
  data() {
    return {
      pokemonTeam: [],
      pokemon: {},
      spriteImgFront: "",
      spriteImgBack: "",
      showPokemon: false,
    };
  },
  methods: {
    savePokemon() {
      let pokemonToSave = {
        id: this.id,
        name: this.name,
        url: this.$route.params.url,
      };
      PokemonService.saveFavorite(pokemonToSave).then(() => {
        alert(`${this.name} has been saved to Favorites `);
        this.$router.push({ name: "pokemon" });
        // console.log(response);
      });
    },
  },
  created() {
    PokemonService.getPokemonByName(this.name).then((response) => {
      this.pokemon = response.data;
      if(response.status == 404){this.$router.push({name: "NotFound"})}
      this.spriteImgFront = response.data.sprites.front_default;
      this.spriteImgBack = response.data.sprites.back_default;
      // makes the first letter of the name capital
      this.pokemon.name =
        this.pokemon.name.charAt(0).toUpperCase() + this.pokemon.name.slice(1);
      this.showPokemon = true;
    }).catch(error => {console.log(error); this.$router.push({name: "NotFound"})});
  },
};
</script>

<style scoped>
h2{
  background: #f8f0d9;
  outline-style: solid;
  border-radius: 5px;
  color: #9f9f96;
  border-width: 5px;
}
#name{
  color: #5c5a52;
  border-style: solid;
  border-color: #9f9f96;
  border-radius: 5px;
  border-width: 3px;
  padding-bottom: 1px;
  text-shadow: 1px 1.5px 0px #acb5ac;
}
#pokedex{
  color: #5c5a52;
  border-style: solid;
  border-color: #9f9f96;
  border-radius: 5px;
  border-width: 3px;
  padding-bottom: 1px;
  text-shadow: 1px 1.5px 0px #acb5ac;
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
.detail{
  margin-left: 43vw;
  margin-right: 43vw;
  /* background: #f8f0d9; */
  border-radius: 5px;
}
button{
  margin-top: 15px;
}
</style>