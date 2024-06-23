package com.example.mvvmtupix.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvvmtupix.model.Movie

@Dao
interface TupixDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movie: Movie)

    @Query("select * from movies")
    suspend fun getAllFavouriteMovies(): List<Movie>

    @Query("select exists (select 1 from movies where id =:movie_id )")
    suspend fun isExisted(movie_id: Int): Boolean

    @Query("delete from Movies where id= :movie_id")
    suspend fun deleteMovies(movie_id: Int)

    @Delete
    suspend fun deleteMovies(movie: Movie)
}
