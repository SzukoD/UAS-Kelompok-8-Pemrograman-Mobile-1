package com.utb.inventoryapp.data.local.dao

import androidx.room.*
import com.utb.inventoryapp.data.local.entity.Supplier
import kotlinx.coroutines.flow.Flow

@Dao
interface SupplierDao {
    @Query("SELECT * FROM supplier ORDER BY nama ASC")
    fun getAll(): Flow<List<Supplier>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Supplier): Long

    @Update
    suspend fun update(item: Supplier)

    @Delete
    suspend fun delete(item: Supplier)

    @Query("SELECT COUNT(*) FROM supplier")
    suspend fun count(): Int
}
