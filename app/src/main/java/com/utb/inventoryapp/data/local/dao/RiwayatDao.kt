package com.utb.inventoryapp.data.local.dao

import androidx.room.*
import com.utb.inventoryapp.data.local.entity.Riwayat
import kotlinx.coroutines.flow.Flow

@Dao
interface RiwayatDao {
    @Query("SELECT * FROM riwayat ORDER BY tanggal DESC")
    fun getAll(): Flow<List<Riwayat>>

    @Query("SELECT * FROM riwayat WHERE userId = :userId ORDER BY tanggal DESC")
    fun getByUser(userId: Long): Flow<List<Riwayat>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Riwayat): Long

    @Query("SELECT COUNT(*) FROM riwayat")
    suspend fun count(): Int
}
