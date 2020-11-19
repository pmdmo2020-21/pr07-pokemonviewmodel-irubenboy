package es.iessaladillo.pedrojoya.intents.data.local

import es.iessaladillo.pedrojoya.intents.R
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon
import kotlin.random.Random

object Database : DataSource{
    private var pokemons = listOf<Pokemon>() // Create to empty list

    init { // Create all pokemons to the list
        val p1 = Pokemon(1, "Pikachu", 10.0, R.drawable.pikachu)
        val p2 = Pokemon(2, "Bulbasur", 8.0, R.drawable.bulbasur)
        val p3 = Pokemon(3, "Cubone", 7.5, R.drawable.cubone)
        val p4 = Pokemon(4, "Feebas",6.5, R.drawable.feebas)
        val p5 = Pokemon(5, "Giratina", 8.5, R.drawable.giratina)
        val p6 = Pokemon(6, "Girados" , 9.6, R.drawable.gyarados)
        pokemons = listOf(p1, p2, p3, p4, p5, p6) // add pokemons to list
    }
    override fun getRandomPokemon(): Pokemon { // Get a random pokemon
        return pokemons[Random.nextInt(0, pokemons.size-1)]
    }

    override fun getAllPokemons(): List<Pokemon> { // Returnt the list of pokemon
        return pokemons
    }

    override fun getPokemonById(id: Long): Pokemon? { // Return the pokemon with that id
        lateinit var p: Pokemon
        pokemons.forEach{
            if(it.id == id){
                p = it
            }
        }
        return p
    }

}