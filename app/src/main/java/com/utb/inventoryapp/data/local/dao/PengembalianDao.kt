package com.utb.inventoryapp.data.local.dao

import androidx.room.*
import com.utb.inventoryapp.data.local.entity.Pengembalian
import kotlinx.coroutines.flow.Flow

@Dao
interface PengembalianDao {
    @Query("SELECT * FROM pengembalian ORDER BY tanggalKembali DESC")
    fun getAll(): Flow<List<Pengembalian>>

    @Query("SELECT * FROM pengembalian WHERE peminjamanId = :peminjamanId LIMIT 1")
    suspend fun getByPeminjaman(peminjamanId: Long): Pengembalian?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Pengembalian): Long

    @Update
    suspend fun update(item: Pengembalian)

    @Delete
    suspend fun delete(item: Pengembalian)

    @Query("SELECT COUNT(*) FROM pengembalian")
    suspend fun count(): Int
}
