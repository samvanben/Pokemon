<template>
  <div class="home">
    <h1>Random Pokémon Generator</h1>
    <pokemon-detail  />
  </div>
</template>

<script>
import PokemonDetail from '../components/PokemonDetail.vue'
export default {
  name: "home",
  components: {
    PokemonDetail
  }
};
</script>
