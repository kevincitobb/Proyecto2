package com.kevs.proyecto2.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevs.proyecto2.R
import com.kevs.proyecto2.databinding.ListElementBinding
import com.kevs.proyecto2.model.Movie
import com.kevs.proyecto2.view.activities.MainActivity


class MoviesAdapter(private val context: Context, val movies: ArrayList<Movie>): RecyclerView.Adapter<MoviesAdapter.ViewHolder>(){

    private val layoutInflater = LayoutInflater.from(context)

    class ViewHolder(view: ListElementBinding): RecyclerView.ViewHolder(view.root){
        val tvTitle = view.tvTitle
        val tvGenre = view.tvGenre
        val tvDirector = view.tvDeveloper
        val image = view.imageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListElementBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvTitle.text = movies[position].title
        holder.tvGenre.text = movies[position].year.toString()
        holder.tvDirector.text = movies[position].director
        when(movies[position].genre){
            "Terror" -> {
                holder.image.setImageResource(R.drawable.terror)
            }
            "Acción/Aventura" -> {
                holder.image.setImageResource(R.drawable.accion)
            }
            "Comedia" -> {
                holder.image.setImageResource(R.drawable.comedia)
            }
            "Documental" -> {
                holder.image.setImageResource(R.drawable.documental)
            }
            else ->{
                holder.image.setImageResource(R.drawable.action)
            }
        }


        //Para los clicks de cada elemento viewholder

        holder.itemView.setOnClickListener {
            //Manejar el click
            if(context is MainActivity) context.selectedMovie(movies[position])
        }

    }

    override fun getItemCount(): Int {
        return movies.size
    }

}