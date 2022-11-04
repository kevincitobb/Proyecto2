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
import com.kevs.proyecto2.databinding.ActivityInsertBinding
import com.kevs.proyecto2.db.DbMovies


class InsertActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityInsertBinding
    private var genres = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val spinner:Spinner = binding.spGenre
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
            spinner.setSelection(adapter.getPosition("Terror"))
        }

    }

    fun click(view: View) {
        val dbMovies = DbMovies(this)
        with(binding){
            when{
                tietTitle.text.toString().isEmpty() -> {
                    tietTitle.error = resources.getString(R.string.vacio)

                    Toast.makeText(this@InsertActivity, resources.getString(R.string.llenar), Toast.LENGTH_SHORT).show()
                }
                tietGenre.text.toString().isEmpty() -> {
                    tietGenre.error = resources.getString(R.string.vacio)
                    Toast.makeText(this@InsertActivity, resources.getString(R.string.llenar), Toast.LENGTH_SHORT).show()
                }
                tietDirector.text.toString().isEmpty() -> {
                    tietDirector.error = resources.getString(R.string.vacio)
                    Toast.makeText(this@InsertActivity, resources.getString(R.string.llenar), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    //Realizamos la inserción
                    val id = dbMovies.insertMovie( tietTitle.text.toString(), tietGenre.text.toString().toInt(), tietDirector.text.toString(),spGenre.selectedItem as String)

                    if(id>0){
                        Toast.makeText(this@InsertActivity, resources.getString(R.string.exito), Toast.LENGTH_SHORT).show()
                        tietTitle.setText("")
                        tietGenre.setText("")
                        tietDirector.setText("")
                        tietTitle.requestFocus()
                    }else{
                        Toast.makeText(this@InsertActivity, resources.getString(R.string.fallo), Toast.LENGTH_SHORT).show()
                    }
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

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }
}
fun clickDelete(view: View) {}
fun clickEdit(view: View) {}
fun clickUpdate(view: View) {}
