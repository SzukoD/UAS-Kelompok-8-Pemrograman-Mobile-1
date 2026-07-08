package com.utb.inventoryapp.data.local.dao

import androidx.room.*
import com.utb.inventoryapp.data.local.entity.JenisBarang
import kotlinx.coroutines.flow.Flow

@Dao
interface JenisBarangDao {
    @Query("SELECT * FROM jenis_barang ORDER BY nama ASC")
    fun getAll(): Flow<List<JenisBarang>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: JenisBarang): Long

    @Update
    suspend fun update(item: JenisBarang)

    @Delete
    suspend fun delete(item: JenisBarang)

    @Query("SELECT COUNT(*) FROM jenis_barang")
    suspend fun count(): Int
}
