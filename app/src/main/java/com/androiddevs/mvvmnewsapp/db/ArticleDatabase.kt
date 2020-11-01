package com.androiddevs.mvvmnewsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.androiddevs.mvvmnewsapp.model.Article

@Database(
    entities = [Article::class],
    version = 1
)

@TypeConverters(Converters::class)
abstract  class ArticleDatabase : RoomDatabase(){
    abstract fun getArticleDao(): ArticleDao

    companion object {
        //Volatile -> Other threads can immediatly see that when a thread changes their instances
        @Volatile
        private var instance: ArticleDatabase? = null
        private val LOCK = Any()

        //Basiclly, ketika create instances database, fun invoke akan dipanggil, nah disini bakal check apakah sudah ada instance, bila null maka
        //akan synchornized(LOCK) createDatabase instance


        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                ArticleDatabase::class.java,
                "article_db.db"
            ).build()
    }
}