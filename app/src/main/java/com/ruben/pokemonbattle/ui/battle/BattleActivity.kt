package com.ruben.pokemonbattle.ui.battle

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import com.ruben.pokemonbattle.databinding.BattleActivityBinding
import com.ruben.pokemonbattle.local.model.Pokemon
import com.ruben.pokemonbattle.ui.selection.SelectionActivity
import com.ruben.pokemonbattle.ui.winner.WinnerActivity

class BattleActivity : AppCompatActivity() {

    private val battleViewModel : BattleActivityViewModel by viewModels()
    private val battleBinding : BattleActivityBinding by lazy { BattleActivityBinding.inflate(layoutInflater)}
    private lateinit var btnStartBattle : Button
    private lateinit var containerPokemon1 : ConstraintLayout
    private lateinit var containerPokemon2 : ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(battleBinding.root)
        setupViews()
        observe()
    }

    private fun observe() {
        battleViewModel.firstPlayer.observe(this){ showPokemon(it,1) }
        battleViewModel.secondPlayer.observe(this) {showPokemon(it,2)}
    }


    private val pokemonSelectionCall =
            registerForActivityResult(StartActivityForResult()){
                result: ActivityResult ->
                if(result.resultCode == Activity.RESULT_OK && result.data != null) {
                    val pokemon: Pokemon? = result.data?.getParcelableExtra(EXTRA_POKEMON)
                    val container : Int? = result.data?.getIntExtra(EXTRA_CONTAINER, 0)
                    if(pokemon != null && container != null){
                        if(container == 1){
                            battleViewModel.changeFirstPlayer(pokemon)
                        } else if(container == 2) {
                            battleViewModel.changeSecondPlayer(pokemon)
                        }
                    }
                }
            }


    private fun showPokemon(p: Pokemon, container: Int) {
        if(container == 1){
            battleBinding.imgPlayer1.setImageResource(p.photo)
            battleBinding.txtPlayer1.text = p.name
        } else if(container == 2) {
            battleBinding.imgPlayer2.setImageResource(p.photo)
            battleBinding.txtPlayer2.text = p.name
        }

    }

    private fun init(){
        btnStartBattle = battleBinding.btnStartBattle
        containerPokemon1 = battleBinding.containerPlayer1
        containerPokemon2 = battleBinding.containerPlayer2
        battleBinding.imgPlayer1.setImageResource(battleViewModel.firstPlayer.value?.photo!!)
        battleBinding.txtPlayer1.text = battleViewModel.secondPlayer.value?.name
        battleBinding.imgPlayer2.setImageResource(battleViewModel.secondPlayer.value?.photo!!)
        battleBinding.txtPlayer2.text = battleViewModel.secondPlayer.value?.name
    }

    private fun setupViews() {

        init()
        btnStartBattle.setOnClickListener{navigateToWinnerActivity(battleViewModel.winner())}
        containerPokemon1.setOnClickListener{navigateToSelectionActivity(battleViewModel.firstPlayer.value, 1)}
        containerPokemon2.setOnClickListener{navigateToSelectionActivity(battleViewModel.secondPlayer.value, 2)}
    }

    private fun navigateToSelectionActivity(p: Pokemon?, container: Int) {
        println(container)
        val intent = SelectionActivity.newIntent(this, p, container)
        pokemonSelectionCall.launch(intent)
    }

    private fun navigateToWinnerActivity(p: Pokemon) {
        val intent = WinnerActivity.newIntent(this, p)
        startActivity(intent)
    }

    companion object{
        const val EXTRA_POKEMON = "EXTRA_POKEMON"
        const val EXTRA_CONTAINER = "EXTRA_CONTAINER"
        fun newIntent(context: Context, pokemon: Pokemon, container: Int?): Intent{
            return Intent(context, BattleActivity::class.java)
                .putExtras(bundleOf(EXTRA_POKEMON to pokemon, EXTRA_CONTAINER to container))
        }
    }
}