package com.acob.blc.acobooking.data.dao

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Update
import com.acob.blc.acobooking.data.model.OBEvent

/**
 * Created by wugang00 on 13/12/2017.
 */
interface OBDao<T>:BaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(t: T)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(t: T)

    @Delete
    fun delete(t: T)
}