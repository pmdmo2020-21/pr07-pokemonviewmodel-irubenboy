package com.ruben.pokemonbattle.ui.selection

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.ruben.pokemonbattle.local.Database
import com.ruben.pokemonbattle.databinding.SelectionActivityBinding
import com.ruben.pokemonbattle.local.model.Pokemon
import com.ruben.pokemonbattle.ui.battle.BattleActivity

class SelectionActivity : AppCompatActivity() {

    private val selectionViewModel: SelectionActivityViewModel by viewModels()
    private val selectionBinding: SelectionActivityBinding by lazy {SelectionActivityBinding.inflate(layoutInflater)}
    private val db = Database
    private val pokemons = db.getAllPokemons().sortedBy { it.name }
    private lateinit var imgPokemons: List<ImageView>
    private lateinit var rdbPokemons: List<RadioButton>
    private var container: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(selectionBinding.root)
        getData()
        setupViews()
        observerPokemon()
    }

    private fun observerPokemon() {
        selectionViewModel.selectedPokemon.observe(this){showRadioBtnPokemon(it)}
    }

    private fun showRadioBtnPokemon(p: Pokemon?) {
        var pokemon: Pokemon
        rdbPokemons.forEach {
            pokemon = it.tag as Pokemon
            if(p != pokemon){
                it.isChecked = false
            } else {
                if(!it.isChecked){
                    it.isChecked = true
                }
            }
        }
    }

    private fun setupViews() {
        init()
        for(i in rdbPokemons.indices){
            rdbPokemons[i].setOnClickListener { view:View -> changeRadioButton(view) }
        }
    }

    private fun changeRadioButton(view: View) {
        val button = view as RadioButton

        selectionViewModel.changeSelectedPlayer(button.tag as Pokemon)
    }


    private fun getData() {
        if (intent == null || !intent.hasExtra(EXTRA_POKEMON)) {
            throw RuntimeException(
                    "SelectionActivity needs to receive id as extras")
        }
        val pokemon: Pokemon? = intent.getParcelableExtra(EXTRA_POKEMON)
        container = intent.getIntExtra(EXTRA_CONTAINER,0)
        if (pokemon != null){
            selectionViewModel.changeSelectedPlayer(pokemon)
        }
    }

    private fun init(){
        imgPokemons = listOf(selectionBinding.imgPokemon1, selectionBinding.imgPokemon2, selectionBinding.imgPokemon3,
                selectionBinding.imgPokemon4, selectionBinding.imgPokemon5, selectionBinding.imgPokemon6)
        rdbPokemons = listOf(selectionBinding.rdbPokemon1, selectionBinding.rdbPokemon2, selectionBinding.rdbPokemon3,
                selectionBinding.rdbPokemon4, selectionBinding.rdbPokemon5, selectionBinding.rdbPokemon6)

        for (i in pokemons.indices){

            imgPokemons[i].setImageResource(pokemons[i].photo)
            rdbPokemons[i].text = pokemons[i].name
            rdbPokemons[i].tag = pokemons[i];
        }
    }

    override fun onBackPressed() {
        setActivityResult()
        super.onBackPressed()
    }

    private fun setActivityResult() {
        val pokemon : Pokemon? = selectionViewModel.selectedPokemon.value
        if(pokemon != null){
            println(pokemon)
            println(container)
            val result = BattleActivity.newIntent(this, pokemon, container)
            setResult(RESULT_OK, result)
        }
    }

    companion object{
        const val EXTRA_POKEMON = "EXTRA_POKEMON"
        const val EXTRA_CONTAINER = "EXTRA_CONTAINER"

        fun newIntent(context: Context, p: Pokemon?, container: Int) =
                Intent(context, SelectionActivity::class.java)
                        .putExtras(bundleOf(EXTRA_POKEMON to p,
                                            EXTRA_CONTAINER to container))
    }

}