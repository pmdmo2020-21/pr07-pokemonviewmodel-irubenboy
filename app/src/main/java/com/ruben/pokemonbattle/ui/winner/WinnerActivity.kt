package com.ruben.pokemonbattle.ui.winner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.ruben.pokemonbattle.local.Database
import com.ruben.pokemonbattle.local.model.Pokemon
import com.ruben.pokemonbattle.databinding.WinnerActivityBinding

class WinnerActivity : AppCompatActivity() {

    private lateinit var winnerBinding: WinnerActivityBinding
    private var id: Long = 0
    private val db = Database
    private lateinit var winner: Pokemon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        winnerBinding = WinnerActivityBinding.inflate(layoutInflater)
        setContentView(winnerBinding.root)
        getData()
        setupViews()
    }

    private fun setupViews() {
        init()
    }

    private fun init(){
        winner = db.getPokemonById(id)!!
        winnerBinding.imgWinner.setImageResource(winner.photo)
        winnerBinding.txtWinner.text = winner.name
    }

    private fun getData() {
        if (intent == null || !intent.hasExtra(EXTRA_ID)) {
            throw RuntimeException(
                    "WinnerActivity needs to receive id as extras")
        }
        id = intent.getLongExtra(EXTRA_ID, 0)
    }

    companion object{
        const val EXTRA_ID = "EXTRA ID"

        fun newIntent(context: Context, id: Long) =
            Intent(context, WinnerActivity::class.java)
                    .putExtras(bundleOf(EXTRA_ID to id))
    }
}