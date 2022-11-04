package com.kevs.proyecto2.view.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.kevs.proyecto2.R
import com.kevs.proyecto2.databinding.ActivityDetailBinding
import com.kevs.proyecto2.db.DbMovies
import com.kevs.proyecto2.model.Movie

class DetailActivity : AppCompatActivity() , AdapterView.OnItemSelectedListener{

    private lateinit var binding: ActivityDetailBinding
    private var genres = ""
    private lateinit var dbMovies: DbMovies
    var Movie: Movie? =null
    var id=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinner: Spinner = binding.spGenreDetail
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

        val myAdap = binding.spGenreDetail.adapter

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

                    //desactivar teclado
                    tietTitle.inputType = InputType.TYPE_NULL
                    tietGenre.inputType = InputType.TYPE_NULL
                    tietDirector.inputType = InputType.TYPE_NULL

                }
            }
        }
        spinner.setEnabled(false);
    }

    override fun onBackPressed(){
        super.onBackPressed()
        startActivity(Intent(this,MainActivity::class.java))
    }

    fun clickEdit(view: View) {
        val intent = Intent(this,EditActivity::class.java)
        intent.putExtra("ID",id)
        startActivity(intent)
        finish()
    }
    fun clickDelete(view: View) {
        AlertDialog.Builder(this)
            .setTitle(resources.getString(R.string.confirmacion))
            .setMessage(resources.getString(R.string.seguro_eliminar,Movie?.genre))
            .setPositiveButton(resources.getString(R.string.aceptar), DialogInterface.OnClickListener{
                    dialog,wich ->
                if(dbMovies.deleteMovie(id)){
                    Toast.makeText(this@DetailActivity,resources.getString(R.string.exito), Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@DetailActivity,MainActivity::class.java))
                }else{
                    Toast.makeText(this@DetailActivity,resources.getString(R.string.fallo), Toast.LENGTH_SHORT).show()
                }
            })
            .setNegativeButton(resources.getString(R.string.cancelar),DialogInterface.OnClickListener{
                    dialog,wich->
                dialog.dismiss()
            })
            .show()
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
                binding.ivGenre.visibility = View.GONE
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        return
    }
}
