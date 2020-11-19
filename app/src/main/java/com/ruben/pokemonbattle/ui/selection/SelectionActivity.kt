package es.iessaladillo.pedrojoya.intents.ui.selection

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import es.iessaladillo.pedrojoya.intents.data.local.Database
import es.iessaladillo.pedrojoya.intents.databinding.SelectionActivityBinding

class SelectionActivity : AppCompatActivity() {

    private lateinit var selectionBinding: SelectionActivityBinding
    private var id: Long = 0
    private var container: Int  = 0
    private val db = Database
    private val pokemons = db.getAllPokemons().sortedBy { it.name }
    private lateinit var imgPokemons: List<ImageView>
    private lateinit var rdbPokemons: List<RadioButton>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectionBinding = SelectionActivityBinding.inflate(layoutInflater)
        setContentView(selectionBinding.root)
        getData()
        setupViews()
    }

    private fun setupViews() {
        init()
        for(i in rdbPokemons.indices){
            rdbPokemons[i].setOnClickListener { rdbClick(rdbPokemons[i], i) }
        }
    }

    private fun rdbClick(rdb: RadioButton, index: Int) {
        rdbPokemons.forEach {
            it.isChecked = false
        }
        rdb.isChecked = true
        id = pokemons[index].id
    }

    private fun getData() {
        if (intent == null || !intent.hasExtra(EXTRA_ID)) {
            throw RuntimeException(
                    "SelectionActivity needs to receive id as extras")
        }
        id = intent.getLongExtra(EXTRA_ID, 0)
        container = intent.getIntExtra(EXTRA_CONTAINER, 0)
    }

    private fun init(){
        imgPokemons = listOf(selectionBinding.imgPokemon1, selectionBinding.imgPokemon2, selectionBinding.imgPokemon3,
                selectionBinding.imgPokemon4, selectionBinding.imgPokemon5, selectionBinding.imgPokemon6)
        rdbPokemons = listOf(selectionBinding.rdbPokemon1, selectionBinding.rdbPokemon2, selectionBinding.rdbPokemon3,
                selectionBinding.rdbPokemon4, selectionBinding.rdbPokemon5, selectionBinding.rdbPokemon6)

        for (i in pokemons.indices){

            imgPokemons[i].setImageResource(pokemons[i].photo)
            rdbPokemons[i].text = pokemons[i].name

            if(id == pokemons[i].id){
                rdbPokemons[i].isChecked = true
            }
        }
    }

    override fun onBackPressed() {
        setActivityResult(id)
        finish()
        super.onBackPressed()
    }

    private fun setActivityResult(id: Long) {
        val result = Intent().putExtras(bundleOf(EXTRA_ID to id, EXTRA_CONTAINER to container))
        setResult(RESULT_OK, result)
    }

    companion object{
        const val EXTRA_ID = "EXTRA_ID"
        const val EXTRA_CONTAINER = "EXTRA_CONTAINER"

        fun newIntent(context: Context, id: Long, container: Int) =
                Intent(context, SelectionActivity::class.java)
                        .putExtras(bundleOf(EXTRA_ID to id,
                                            EXTRA_CONTAINER to container))
    }

}