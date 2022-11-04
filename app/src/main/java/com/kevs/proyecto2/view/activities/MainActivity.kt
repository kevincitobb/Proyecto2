package com.kevs.proyecto2.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevs.proyecto2.databinding.ActivityMainBinding
import com.kevs.proyecto2.db.DbMovies
import com.kevs.proyecto2.model.Movie
import com.kevs.proyecto2.view.adapters.MoviesAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listMovies: ArrayList<Movie>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dbMovie = DbMovies(this)

        listMovies = dbMovie.getMovie()

        val MoviesAdapter = MoviesAdapter(this,listMovies)

        binding.rvGames.layoutManager = LinearLayoutManager(this)
        binding.rvGames.adapter = MoviesAdapter

        if(listMovies.size == 0) binding.tvSinRegistros.visibility = View.VISIBLE
        else binding.tvSinRegistros.visibility = View.INVISIBLE

    }

    fun click(view: View){
        startActivity(Intent(this,InsertActivity::class.java))
        finish()
    }
    fun selectedMovie(movie: Movie){
        val intent = Intent(this,DetailActivity::class.java)
        intent.putExtra("ID",movie.id)
        startActivity(intent)
        finish()
    }



}