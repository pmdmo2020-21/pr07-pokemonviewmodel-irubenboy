package com.ruben.pokemonbattle.ui.winner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.ruben.pokemonbattle.local.Database
import com.ruben.pokemonbattle.local.model.Pokemon
import com.ruben.pokemonbattle.databinding.WinnerActivityBinding

class WinnerActivity : AppCompatActivity() {

    private val winnerViewModel: WinnerActivityViewModel by viewModels()
    private val winnerBinding: WinnerActivityBinding by lazy{WinnerActivityBinding.inflate(layoutInflater)}
    private val db = Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(winnerBinding.root)
        getData()
        observerWinner()
    }

    private fun observerWinner() {
        winnerViewModel.winnerPokemon.observe(this) {showWinner(it)}
    }

    private fun showWinner(p: Pokemon) {
        winnerBinding.imgWinner.setImageResource(p.photo)
        winnerBinding.txtWinner.text = p.name
    }

    private fun getData() {
        if (intent == null || !intent.hasExtra(EXTRA_POKEMON)) {
            throw RuntimeException(
                    "WinnerActivity needs to receive id as extras")
        }
        val pokemon: Pokemon? = intent.getParcelableExtra(EXTRA_POKEMON)

        if(pokemon != null){
            winnerViewModel.changeWinnerPlayer(pokemon)
        }
    }

    companion object{
        private const val EXTRA_POKEMON = "EXTRA POKEMON"

        fun newIntent(context: Context, pokemon: Pokemon) =
            Intent(context, WinnerActivity::class.java)
                    .putExtras(bundleOf(EXTRA_POKEMON to pokemon))
    }
}