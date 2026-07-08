package com.utb.inventoryapp.data.local.dao

import androidx.room.*
import com.utb.inventoryapp.data.local.entity.Satuan
import kotlinx.coroutines.flow.Flow

@Dao
interface SatuanDao {
    @Query("SELECT * FROM satuan ORDER BY nama ASC")
    fun getAll(): Flow<List<Satuan>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Satuan): Long

    @Update
    suspend fun update(item: Satuan)

    @Delete
    suspend fun delete(item: Satuan)

    @Query("SELECT COUNT(*) FROM satuan")
    suspend fun count(): Int
}
