package com.techelevator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.model.Pokemon;
import com.techelevator.model.PokemonDetail;
import com.techelevator.model.Results;
import com.techelevator.model.Sprite;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service // connects through SpringBoot so that
// Spring knows that this is the class to run
// when wwe create a PokeAPIService object
public class RestPokeAPIService implements PokeAPIService{

    RestTemplate rt = new RestTemplate(); // talking to an external API
    private final String API_URL = "https://pokeapi.co/api/v2/pokemon/";

    @Override
    public List<Pokemon> getPokemon() {
        String response = rt.getForObject(API_URL, String.class);
//        System.out.println(response);
        ObjectMapper mapper = new ObjectMapper(); // create an object of this class
        List<Pokemon> pokemonList = new ArrayList<>(); // programming to the interface

        try { // object mapper is a checked exception
            JsonNode jsonNode = mapper.readTree(response); // turns the string response into a tree with nodes for us to grab
            JsonNode root = jsonNode.path("results");
            for (int i = 0; i < root.size(); i++) {
                // root is "results" array
                // path(i) 0 -- what array element
                // path("name") -- node called name
                String name = root.path(i).path("name").asText();
                String url = root.path(i).path("url").asText();
                int id = calculateId(url);
                // this assigned sprites based on id but broke the rest of code
                Sprite sprite = new Sprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/" + id + ".png", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + id + ".png");

                // create a pokemon object and set the values
                Pokemon temp = new Pokemon();
                temp.setId(id);
                temp.setName(name);
                temp.setUrl(url);
                // this temp was used to assign sprite on list page but broke rest of code
                temp.setSprite(sprite);
                pokemonList.add(temp);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return pokemonList;
    }

    public int calculateId(String url){
        int pokemonIndex = url.indexOf("pokemon"); // returns the index of the p in pokemon
        // Should return "pokemon/XX/"
        String pokemonString = url.substring(pokemonIndex);
        int slashIndex = pokemonString.indexOf("/");
        String number = pokemonString.substring(slashIndex + 1, pokemonString.length() - 1);
//                System.out.println(number);

        int id = Integer.parseInt(number);
        return id;
    }

//    @Override
//    public PokemonDetail getPokemonDetailById(int id) {
//        PokemonDetail pokemonDetail = rt.getForObject(API_URL + id, PokemonDetail.class);
////        System.out.println(pokemonDetail);
//        return pokemonDetail;
//    }

    @Override
    public PokemonDetail getPokemonDetailByName(String name) {
        PokemonDetail pokemonDetail = new PokemonDetail();
        try {
            pokemonDetail = rt.getForObject(API_URL + name, PokemonDetail.class);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
//        PokemonDetail pokemonDetail = rt.getForObject(API_URL + name, PokemonDetail.class);
        return pokemonDetail;
    }

    @Override
    public List<Pokemon> getMorePokemon(int startVal, int endVal) {
        Results rs = rt.getForObject(API_URL + "?offset=" + startVal + "&limit=20", Results.class);

        List<Pokemon> list = rs.getResults();
        for (Pokemon item: list){
            int id = calculateId(item.getUrl());
            item.setId(id);
            item.setSprite(new Sprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/" + id + ".png", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + id + ".png"));

        }
        return list;
    }

}
