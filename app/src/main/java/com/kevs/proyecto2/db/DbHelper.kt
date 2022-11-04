package com.kevs.proyecto2.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Creado por Amaury Perea Matsumura el 21/10/22
 */
open class DbHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "DbMovies.db"
        private const val TABLE_MOVIES = "movies"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_MOVIES (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL,year INT NOT NULL, director TEXT NOT NULL, genre TEXT NOT NULL)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE $TABLE_MOVIES")
        onCreate(db)
    }
}