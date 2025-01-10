package com.finalproject.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import com.finalproject.data.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 3, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}
