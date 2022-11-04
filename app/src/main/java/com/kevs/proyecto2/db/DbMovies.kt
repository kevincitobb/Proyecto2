package com.kevs.proyecto2.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.kevs.proyecto2.model.Movie

/**
 * Creado por Amaury Perea Matsumura el 21/10/22
 */
open class DbMovies(private val context: Context):DbHelper(context) {

    //Aquí se van a implementar las operaciones CRUD (Create, Read, Update and Delete)

    public fun insertMovie(title: String, year: Number, director: String, genre:String): Long{
        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        var id: Long = 0

        try{
            val values = ContentValues()

            values.put("title", title)
            values.put("year", year.toString())
            values.put("director", director)
            values.put("genre", genre)

            id = db.insert("movies", null, values)

        }catch(e: Exception){
            //Manejo de excepción
        }finally {
            db.close()
        }

        return id
    }

    public fun getMovie(): ArrayList<Movie>{
        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        var listMovies = ArrayList<Movie>()
        var movieTmp: Movie? = null
        var cursorMovies: Cursor? = null

        cursorMovies = db.rawQuery("SELECT * FROM movies", null)

        if(cursorMovies.moveToFirst()){
            do{
                movieTmp = Movie(cursorMovies.getInt(0), cursorMovies.getString(1), cursorMovies.getString(2), cursorMovies.getString(3), cursorMovies.getString(4))
                listMovies.add(movieTmp)
            }while(cursorMovies.moveToNext())
        }

        cursorMovies.close()

        return listMovies
    }

    fun getMovie(id: Int): Movie?{
        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        var Movie: Movie? = null

        var cursorMovies: Cursor? = null

        cursorMovies = db.rawQuery("SELECT * FROM movies WHERE id = $id LIMIT 1", null)

        if(cursorMovies.moveToFirst()){
            Movie = Movie(cursorMovies.getInt(0), cursorMovies.getString(1), cursorMovies.getString(2), cursorMovies.getString(3), cursorMovies.getString(4))
        }

        cursorMovies.close()

        return Movie
    }

    fun updateMovie(id: Int, title: String, year: Number, director: String, genre: String): Boolean{
        var banderaCorrecto = false

        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        try{
            db.execSQL("UPDATE movies SET title = '$title', year = '${year.toString()}', director = '$director',genre = '$genre' WHERE id = $id")
            banderaCorrecto = true
        }catch(e: Exception){
            //Manejo de la excepción
        }finally {
            db.close()
        }

        return banderaCorrecto
    }

    fun deleteMovie(id: Int): Boolean{
        var banderaCorrecto = false

        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        try{
            db.execSQL("DELETE FROM movies WHERE id = $id")
            banderaCorrecto = true
        }catch(e: Exception){

        }finally {
            db.close()
        }

        return banderaCorrecto
    }

}