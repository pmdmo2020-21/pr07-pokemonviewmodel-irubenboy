package es.iessaladillo.pedrojoya.intents.data.local.model


class Pokemon(
        val id: Long,
        var name: String,
        private val strength: Double,
        val photo: Int){

    fun stronger(p: Pokemon): Pokemon{ // Función que devuelve el pokemón más fuerte, comparando dos pokemons
        return if(strength - p.strength >= 0){
            this
        } else {
            p
        }
    }
}