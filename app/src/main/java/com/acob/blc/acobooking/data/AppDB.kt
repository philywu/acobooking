package com.acob.blc.acobooking.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.acob.blc.acobooking.data.dao.OBEventDao
import com.acob.blc.acobooking.data.dao.OBRegisterDao
import com.acob.blc.acobooking.data.model.OBEvent
import com.acob.blc.acobooking.data.model.OBRegister
import com.acob.blc.acobooking.data.util.DateConverter


/**
 * Created by wugang00 on 11/12/2017.
 */


@Database(entities = arrayOf(
        OBEvent::class,
        OBRegister::class
), version = 2, exportSchema = false)

@TypeConverters( DateConverter::class)
abstract class AppDB : RoomDatabase() {

    abstract fun obEventDao(): OBEventDao
    abstract fun obRegisterDao(): OBRegisterDao
}

