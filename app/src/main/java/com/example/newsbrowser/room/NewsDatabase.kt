package com.example.newsbrowser.room

import androidx.room.Database
import com.example.newsbrowser.model.db.ArticleDbModel

@Database(entities = arrayOf(ArticleDbModel::class), version = 1)
abstract class NewsDatabase {
    abstract fun newsDao(): NewsDao
}