package com.acob.blc.acobooking.TestUtil

import com.acob.blc.acobooking.data.AppDB
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import com.acob.blc.acobooking.data.model.OBEvent
import com.acob.blc.acobooking.data.model.OBRegister
import java.util.*


/**
 * Created by wugang00 on 30/12/2017.
 */

class DBUtil {
    companion object {
        @JvmStatic
        fun getTestAppDB():AppDB {
            val context = InstrumentationRegistry.getTargetContext()
            return Room.inMemoryDatabaseBuilder(context, AppDB::class.java).build()
        }

        @JvmStatic
        fun createEvent(appDB:AppDB,eventName:String):OBEvent {
            var eventDao = appDB.obEventDao()
            var eventId = UUID.randomUUID().toString()
            var evt = OBEvent(eventId,eventName,"", Date(), Date(), Date(),"phily","New", Date())
            eventDao?.insertEvent(evt)
            return evt
        }
        @JvmStatic
        fun ceateRegister(appDB: AppDB, userList: List<String>, evtId: String): List<OBRegister> {
            var regDao = appDB.obRegisterDao()
            var regList = arrayListOf<OBRegister>()
            val status= "pending"
            for (user in userList){
                var reg = OBRegister(evtId,user,"pending",Date())
                regDao.insert(reg)
                regList.add(reg)
            }
            return regList
        }
    }

}