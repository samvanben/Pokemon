import axios from 'axios'

const http = axios.create( {
    baseURL: 'http://localhost:9000'
})

export default {

    // list is where we put the methods
    // to talk to the backend
    getAllPokemon() {
        return http.get('/pokemon');
    },
    // getPokemonById(id){
    //     return http.get(`/pokemon/${id}`);
    // },
    getPokemonByName(name){
        return http.get(`/pokemon/${name}`);
    },
    saveFavorite(pokemon){
        return http.post('/pokemon', pokemon);
    },
    getNewPokemon(start, end){
        return http.get(`/pokemon/${start}/${end}`);
    },
    getRandomPokemon(){
        return http.get(`/pokemon/${Math.floor(Math.random() * 151) + 1}`)
    }
}

