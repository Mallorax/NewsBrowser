package com.example.newsbrowser.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsbrowser.model.db.ArticleDbModel

@Database(entities = arrayOf(ArticleDbModel::class), version = 1)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao
}