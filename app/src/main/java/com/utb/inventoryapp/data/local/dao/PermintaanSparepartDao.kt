package com.utb.inventoryapp.data.local.dao

import androidx.room.*
import com.utb.inventoryapp.data.local.entity.PermintaanSparepart
import kotlinx.coroutines.flow.Flow

@Dao
interface PermintaanSparepartDao {
    @Query("SELECT * FROM permintaan_sparepart ORDER BY tanggal DESC")
    fun getAll(): Flow<List<PermintaanSparepart>>

    @Query("SELECT * FROM permintaan_sparepart WHERE picUserId = :userId ORDER BY tanggal DESC")
    fun getByUser(userId: Long): Flow<List<PermintaanSparepart>>

    @Query("SELECT * FROM permintaan_sparepart WHERE status = :status ORDER BY tanggal DESC")
    fun getByStatus(status: String): Flow<List<PermintaanSparepart>>

    @Query("SELECT * FROM permintaan_sparepart WHERE id = :id")
    suspend fun getById(id: Long): PermintaanSparepart?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: PermintaanSparepart): Long

    @Update
    suspend fun update(item: PermintaanSparepart)

    @Delete
    suspend fun delete(item: PermintaanSparepart)

    @Query("SELECT COUNT(*) FROM permintaan_sparepart WHERE status = 'PENDING'")
    fun countPending(): Flow<Int>

    @Query("SELECT COUNT(*) FROM permintaan_sparepart")
    suspend fun count(): Int
}
