package com.utb.inventoryapp.data.local.dao

import androidx.room.*
import com.utb.inventoryapp.data.local.entity.Lokasi
import kotlinx.coroutines.flow.Flow

@Dao
interface LokasiDao {
    @Query("SELECT * FROM lokasi ORDER BY nama ASC")
    fun getAll(): Flow<List<Lokasi>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Lokasi): Long

    @Update
    suspend fun update(item: Lokasi)

    @Delete
    suspend fun delete(item: Lokasi)

    @Query("SELECT COUNT(*) FROM lokasi")
    suspend fun count(): Int
}
