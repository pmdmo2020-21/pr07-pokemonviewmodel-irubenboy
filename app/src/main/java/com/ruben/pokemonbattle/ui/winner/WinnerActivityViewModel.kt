package com.ruben.pokemonbattle.ui.winner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ruben.pokemonbattle.local.Database
import com.ruben.pokemonbattle.local.model.Pokemon

class WinnerActivityViewModel : ViewModel(){

    private val _winnerPlayer: MutableLiveData<Pokemon> = MutableLiveData(Database.getRandomPokemon())
    val winnerPokemon: LiveData<Pokemon>
        get() = _winnerPlayer

    fun changeWinnerPlayer(p: Pokemon){
        _winnerPlayer.value = p
    }
}