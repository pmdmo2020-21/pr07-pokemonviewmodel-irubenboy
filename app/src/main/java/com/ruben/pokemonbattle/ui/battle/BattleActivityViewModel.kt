package com.ruben.pokemonbattle.ui.battle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ruben.pokemonbattle.local.Database
import com.ruben.pokemonbattle.local.model.Pokemon

const val STATE_FIRST_PLAYER = "STATE_FIRST_PLAYER"
const val STATE_SECOND_PLAYER = "STATE_SECOND_PLAYER"

class BattleActivityViewModel (savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _firstPlayer: MutableLiveData<Pokemon> =
        savedStateHandle.getLiveData(STATE_FIRST_PLAYER, Database.getRandomPokemon())
    val firstPlayer: LiveData<Pokemon>
        get() = _firstPlayer

    private val _secondPlayer: MutableLiveData<Pokemon> =
        savedStateHandle.getLiveData(STATE_SECOND_PLAYER, Database.getRandomPokemon())
    val secondPlayer: LiveData<Pokemon>
        get() = _secondPlayer

    fun changeFirstPlayer(p: Pokemon){
        _firstPlayer.value = p
    }

    fun changeSecondPlayer(p: Pokemon){
        _secondPlayer.value = p
    }

    fun winner(): Pokemon {
        val p1 = firstPlayer.value ?: throw IllegalAccessError()
        val p2 = firstPlayer.value ?: throw IllegalAccessError()
        return p1.stronger(p2)
    }
}


