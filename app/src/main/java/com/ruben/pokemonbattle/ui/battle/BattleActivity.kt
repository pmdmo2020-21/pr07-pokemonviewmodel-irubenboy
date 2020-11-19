package com.ruben.pokemonbattle.ui.battle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.ruben.pokemonbattle.databinding.BattleActivityBinding
import com.ruben.pokemonbattle.local.Database
import com.ruben.pokemonbattle.local.model.Pokemon
import com.ruben.pokemonbattle.ui.selection.SelectionActivity
import com.ruben.pokemonbattle.ui.selection.SelectionActivity.Companion.EXTRA_CONTAINER
import com.ruben.pokemonbattle.ui.selection.SelectionActivity.Companion.EXTRA_ID
import com.ruben.pokemonbattle.ui.winner.WinnerActivity

class BattleActivity : AppCompatActivity() {

    private lateinit var battleBinding : BattleActivityBinding
    private lateinit var btnStartBattle : Button
    private lateinit var containerPokemon1 : ConstraintLayout
    private lateinit var containerPokemon2 : ConstraintLayout
    private val db = Database
    private var pokemons = db.getAllPokemons()
    private lateinit var pokemon1 : Pokemon
    private lateinit var pokemon2  : Pokemon
    private lateinit var pokemonWinner : Pokemon
    private var id: Long = 0
    private var container: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        battleBinding = BattleActivityBinding.inflate(layoutInflater)
        setContentView(battleBinding.root)
        setupViews()
    }


    private val pokemonSelectionCall =
            registerForActivityResult(StartActivityForResult()){
                result: ActivityResult ->
                if(result.resultCode == Activity.RESULT_OK && result.data != null){
                    extractResult(result.data!!)
                    showPokemon(container)
                }
            }

    private fun extractResult(data: Intent) {
        if(!data.hasExtra(EXTRA_ID)){
            throw RuntimeException("SelectionActivity must receive in result intent")
        }
        id = data.getLongExtra(EXTRA_ID, 0)
        container = data.getIntExtra(EXTRA_CONTAINER, 0)
    }

    private fun showPokemon(container: Int) {
        lateinit var p : Pokemon
        pokemons.forEach {
            if(it.id == id){
                p = it
            }
        }

        if(container == 1){
            battleBinding.imgPlayer1.setImageResource(p.photo)
            battleBinding.txtPlayer1.text = p.name
            pokemonWinner = p.stronger(pokemon2)
        } else if(container == 2) {
            battleBinding.imgPlayer2.setImageResource(p.photo)
            battleBinding.txtPlayer2.text = p.name
            pokemonWinner = p.stronger(pokemon1)
        }

    }

    private fun init(){
        pokemon1 = db.getRandomPokemon()
        pokemon2 = db.getRandomPokemon()
        btnStartBattle = battleBinding.btnStartBattle
        containerPokemon1 = battleBinding.containerPlayer1
        containerPokemon2 = battleBinding.containerPlayer2
        battleBinding.imgPlayer1.setImageResource(pokemon1.photo)
        battleBinding.txtPlayer1.text = pokemon1.name
        battleBinding.imgPlayer2.setImageResource(pokemon2.photo)
        battleBinding.txtPlayer2.text = pokemon2.name
        pokemonWinner = pokemon1.stronger(pokemon2)
    }

    private fun setupViews() {

        init()
        btnStartBattle.setOnClickListener{navigateToWinnerActivity(pokemonWinner.id)}
        containerPokemon1.setOnClickListener{navigateToSelectionActivity(pokemon1.id, 1)}
        containerPokemon2.setOnClickListener{navigateToSelectionActivity(pokemon2.id, 2)}
    }

    private fun navigateToSelectionActivity(id: Long, container: Int) {
        val intent = SelectionActivity.newIntent(this, id, container)

        pokemonSelectionCall.launch(intent)
    }

    private fun navigateToWinnerActivity(id: Long) {
        val intent = WinnerActivity.newIntent(this, id)
        startActivity(intent)
    }

}