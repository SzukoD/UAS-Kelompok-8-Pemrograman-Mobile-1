package com.utb.inventoryapp.data.local.dao

import androidx.room.*
import com.utb.inventoryapp.data.local.entity.BarangHilang
import kotlinx.coroutines.flow.Flow

@Dao
interface BarangHilangDao {
    @Query("SELECT * FROM barang_hilang ORDER BY tanggal DESC")
    fun getAll(): Flow<List<BarangHilang>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: BarangHilang): Long

    @Delete
    suspend fun delete(item: BarangHilang)

    @Query("SELECT COUNT(*) FROM barang_hilang")
    suspend fun count(): Int
}
