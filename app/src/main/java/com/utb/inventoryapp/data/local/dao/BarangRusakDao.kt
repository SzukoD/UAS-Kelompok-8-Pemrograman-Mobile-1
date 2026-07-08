package com.utb.inventoryapp.data.local.dao

import androidx.room.*
import com.utb.inventoryapp.data.local.entity.BarangRusak
import kotlinx.coroutines.flow.Flow

@Dao
interface BarangRusakDao {
    @Query("SELECT * FROM barang_rusak ORDER BY tanggal DESC")
    fun getAll(): Flow<List<BarangRusak>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: BarangRusak): Long

    @Delete
    suspend fun delete(item: BarangRusak)

    @Query("SELECT COUNT(*) FROM barang_rusak")
    suspend fun count(): Int
}
