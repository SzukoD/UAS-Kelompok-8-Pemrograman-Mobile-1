package com.utb.inventoryapp.data.local.dao

import androidx.room.*
import com.utb.inventoryapp.data.local.entity.Peminjaman
import kotlinx.coroutines.flow.Flow

@Dao
interface PeminjamanDao {
    @Query("SELECT * FROM peminjaman ORDER BY tanggalPinjam DESC")
    fun getAll(): Flow<List<Peminjaman>>

    @Query("SELECT * FROM peminjaman WHERE picUserId = :userId ORDER BY tanggalPinjam DESC")
    fun getByUser(userId: Long): Flow<List<Peminjaman>>

    @Query("SELECT * FROM peminjaman WHERE status = :status ORDER BY tanggalPinjam DESC")
    fun getByStatus(status: String): Flow<List<Peminjaman>>

    @Query("SELECT * FROM peminjaman WHERE id = :id")
    suspend fun getById(id: Long): Peminjaman?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Peminjaman): Long

    @Update
    suspend fun update(item: Peminjaman)

    @Delete
    suspend fun delete(item: Peminjaman)

    @Query("SELECT COUNT(*) FROM peminjaman WHERE status = 'PENDING'")
    fun countPending(): Flow<Int>

    @Query("SELECT COUNT(*) FROM peminjaman")
    suspend fun count(): Int
}
