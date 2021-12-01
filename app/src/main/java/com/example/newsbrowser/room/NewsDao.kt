package com.example.newsbrowser.room

import androidx.room.Dao
import androidx.room.Query
import com.example.newsbrowser.model.db.ArticleDbModel
import io.reactivex.rxjava3.core.Flowable

@Dao
interface NewsDao {

    @Query("SELECT * FROM articles WHERE `isFavourite` == 1")
    fun loadFavourites(): Flowable<ArticleDbModel>
}
