package com.ruben.pokemonbattle.local.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemon(
        val id: Long,
        var name: String,
        private val strength: Double,
        val photo: Int): Parcelable{

    fun stronger(p: Pokemon?): Pokemon{ // Función que devuelve el pokemón más fuerte, comparando dos pokemons
        return if(strength - p!!.strength >= 0){
            this
        } else {
            p
        }
    }
}