package com.ruben.pokemonbattle.ui.selection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ruben.pokemonbattle.local.Database
import com.ruben.pokemonbattle.local.model.Pokemon

class SelectionActivityViewModel : ViewModel(){
    private val _selectedPlayer : MutableLiveData<Pokemon> = MutableLiveData(Database.getRandomPokemon())
    val selectedPokemon: LiveData<Pokemon>
        get() = _selectedPlayer

    fun changeSelectedPlayer(p: Pokemon){
        _selectedPlayer.value = p
    }
}