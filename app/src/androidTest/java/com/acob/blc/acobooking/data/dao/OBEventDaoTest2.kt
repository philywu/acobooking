package com.acob.blc.acobooking.data.dao

import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.acob.blc.acobooking.TestUtil.DBUtil
import com.acob.blc.acobooking.data.AppDB
import com.acob.blc.acobooking.data.model.OBEvent
import junit.framework.Assert.*
import junit.framework.AssertionFailedError
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Created by wugang00 on 1/01/2018.
 */
@RunWith(AndroidJUnit4::class)
open class OBEventDaoTest2 {
    private lateinit var appDB: AppDB
    private lateinit var dao: OBEventDao

    @Before
    fun initDb() {
        appDB = DBUtil.getTestAppDB()
        dao = appDB.obEventDao()
    }

    @After
    fun closeDb() {
        appDB.close()
    }
    @Test
    fun insertTest(){

        var evt = OBEvent("0001","test1","", Date(), Date(), Date(),"phily","New", Date())

        dao?.insertEvent(evt)

        var evt1 = dao?.findEventByEventId("0001")
        //Log.d("DB Test",evt1.name)
        //assert( evt1.name==="test2")
        //assert(false)
        assertNotNull(evt1)
        assertEquals(evt1.name,"test1")



    }
}