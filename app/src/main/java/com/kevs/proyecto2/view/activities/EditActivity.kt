package com.kevs.proyecto2.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.kevs.proyecto2.R
import com.kevs.proyecto2.databinding.ActivityEditBinding
import com.kevs.proyecto2.db.DbMovies
import com.kevs.proyecto2.model.Movie

class EditActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityEditBinding
    private var genres = ""
    private lateinit var dbMovies : DbMovies
    var Movie: Movie? =null
    var id=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinner: Spinner = binding.spGenreEdit
        spinner.onItemSelectedListener = this
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.genres_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        val bundle = intent.extras
        dbMovies = DbMovies(this)

        if (bundle != null) {
            id=bundle.getInt("ID")
            Movie = dbMovies.getMovie(id)
            Movie?.let{
                with(binding){
                    for (i in 0 until spinner.count) {
                        if (spinner.getItemAtPosition(i).toString().equals(it.genre)) {
                            spinner.setSelection(i)
                            break
                        }
                    }
                    tietTitle.setText(it.title)
                    tietGenre.setText(it.year)
                    tietDirector.setText(it.director)
                }
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        genres = parent.getItemAtPosition(pos).toString()

        when(genres){
            parent.getItemAtPosition(0).toString() -> {
                binding.ivGenre.setImageResource(R.drawable.terror)
            }
            parent.getItemAtPosition(1).toString() ->{
                binding.ivGenre.setImageResource(R.drawable.accion)
            }
            parent.getItemAtPosition(2).toString() -> {
                binding.ivGenre.setImageResource(R.drawable.comedia)
            }
            parent.getItemAtPosition(3).toString() -> {
                binding.ivGenre.setImageResource(R.drawable.documental)
            }
            else ->{
                binding.ivHeader.visibility = View.GONE
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {

    }

    override fun onBackPressed(){
        super.onBackPressed()
        val intent = Intent(this,DetailActivity::class.java)
        intent.putExtra("ID",id)
        startActivity(intent)
        finish()
    }

    fun clickUpdate(view: View) {
        with(binding){
            when{
                tietTitle.text.toString().isEmpty() ->{
                    tietTitle.error = resources.getString(R.string.vacio)
                }
                tietGenre.text.toString().isEmpty() ->{
                    tietGenre.error = resources.getString(R.string.vacio)
                }
                tietDirector.text.toString().isEmpty() ->{
                tietDirector.error = resources.getString(R.string.vacio)
            }
                else ->{
                    val id_update = dbMovies.updateMovie(id, tietTitle.text.toString(),tietGenre.text.toString().toInt(),tietDirector.text.toString(), spGenreEdit.selectedItem as String)
                    if(id_update){
                        Toast.makeText(this@EditActivity,resources.getString(R.string.exito), Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@EditActivity,DetailActivity::class.java)
                        intent.putExtra("ID",id)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this@EditActivity,resources.getString(R.string.fallo), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}