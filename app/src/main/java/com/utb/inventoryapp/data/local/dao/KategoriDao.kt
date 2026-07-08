package com.utb.inventoryapp.data.local.dao

import androidx.room.*
import com.utb.inventoryapp.data.local.entity.Kategori
import kotlinx.coroutines.flow.Flow

@Dao
interface KategoriDao {
    @Query("SELECT * FROM kategori ORDER BY nama ASC")
    fun getAll(): Flow<List<Kategori>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Kategori): Long

    @Update
    suspend fun update(item: Kategori)

    @Delete
    suspend fun delete(item: Kategori)

    @Query("SELECT COUNT(*) FROM kategori")
    suspend fun count(): Int
}
