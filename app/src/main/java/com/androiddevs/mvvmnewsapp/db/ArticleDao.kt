package com.androiddevs.mvvmnewsapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.androiddevs.mvvmnewsapp.model.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long

    @Query("SELECT * FROM articles")
    //LiveData prinsip seperti public subscribe, dimana ketika fragment subscribe, maka ketika ada perubahan data di database, the
    // live data will notify the obserevers,so fragment bisa update, ini sangat cocok dikombinasikan dengan viewmodel, karena viewmodel
    // tidak bersifat recreated ketika device diputar, sehingga mantul dengan livedata
    fun getAllArticle(): LiveData<List<Article>>

    @Delete()
    suspend fun deleteArticle(article: Article)
}