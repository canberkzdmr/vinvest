package com.canberkozdemir.vinvest.service

import androidx.room.*

interface BaseDAO<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<T>): List<Long>

    @Insert
    suspend fun insert(entity: T)

    @Update
    fun update(obj: T)

    @Update
    fun update(vararg objs: T)

    @Update
    fun update(obj: List<T>)

    @Delete
    fun delete(obj: T)

    @Delete
    fun delete(vararg objs: T)

    @Delete
    fun delete(obj: List<T>)
}