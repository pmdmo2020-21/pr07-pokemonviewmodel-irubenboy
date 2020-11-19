package com.ruben.pokemonbattle.local

import com.ruben.pokemonbattle.local.model.Pokemon

// IMPORTANTE: NO TOCAR
interface DataSource {
    fun getRandomPokemon(): Pokemon
    fun getAllPokemons(): List<Pokemon>
    fun getPokemonById(id: Long): Pokemon?
}