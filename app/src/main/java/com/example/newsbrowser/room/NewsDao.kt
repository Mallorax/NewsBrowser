package com.example.newsbrowser.room

import androidx.room.Dao
import androidx.room.Query

@Dao
interface NewsDao {

    @Query("SELECT * FROM articles WHERE `isFavourite` == 1")
    fun loadFavourites()
}
