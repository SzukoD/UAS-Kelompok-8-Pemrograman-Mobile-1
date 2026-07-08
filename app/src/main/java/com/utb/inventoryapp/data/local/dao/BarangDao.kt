package com.utb.inventoryapp.data.local.dao

import androidx.room.*
import com.utb.inventoryapp.data.local.entity.Barang
import kotlinx.coroutines.flow.Flow

@Dao
interface BarangDao {
    @Query("SELECT * FROM barang ORDER BY nama ASC")
    fun getAll(): Flow<List<Barang>>

    @Query("SELECT * FROM barang WHERE nama LIKE '%' || :query || '%' OR kode LIKE '%' || :query || '%' ORDER BY nama ASC")
    fun search(query: String): Flow<List<Barang>>

    @Query("SELECT * FROM barang WHERE id = :id")
    suspend fun getById(id: Long): Barang?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Barang): Long

    @Update
    suspend fun update(item: Barang)

    @Delete
    suspend fun delete(item: Barang)

    @Query("UPDATE barang SET stok = stok - :jumlah WHERE id = :id")
    suspend fun kurangiStok(id: Long, jumlah: Int)

    @Query("UPDATE barang SET stok = stok + :jumlah WHERE id = :id")
    suspend fun tambahStok(id: Long, jumlah: Int)

    @Query("SELECT COUNT(*) FROM barang")
    suspend fun count(): Int

    @Query("SELECT COUNT(*) FROM barang WHERE kondisi = 'RUSAK'")
    fun countRusak(): Flow<Int>

    @Query("SELECT COUNT(*) FROM barang WHERE kondisi = 'HILANG'")
    fun countHilang(): Flow<Int>
}
