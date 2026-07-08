package com.utb.inventoryapp.data.local.dao

import androidx.room.*
import com.utb.inventoryapp.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user ORDER BY namaLengkap ASC")
    fun getAll(): Flow<List<UserEntity>>

    @Query("SELECT * FROM user WHERE username = :username AND password = :password LIMIT 1")
    suspend fun login(username: String, password: String): UserEntity?

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getById(id: Long): UserEntity?

    @Query("SELECT * FROM user WHERE role = :role ORDER BY namaLengkap ASC")
    fun getByRole(role: String): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: UserEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<UserEntity>)

    @Update
    suspend fun update(item: UserEntity)

    @Delete
    suspend fun delete(item: UserEntity)

    @Query("SELECT COUNT(*) FROM user")
    suspend fun count(): Int
}
