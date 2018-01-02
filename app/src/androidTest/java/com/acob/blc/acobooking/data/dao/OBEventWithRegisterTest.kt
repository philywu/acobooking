package com.acob.blc.acobooking.data.dao

import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.acob.blc.acobooking.TestUtil.DBUtil
import com.acob.blc.acobooking.data.AppDB
import com.acob.blc.acobooking.data.model.OBEventWithRegister
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by wugang00 on 1/01/2018.
 */
@RunWith(AndroidJUnit4::class)
open class OBEventWithRegisterTest {
    private lateinit var appDB: AppDB
    private lateinit var dao: OBEventWithRegisterDao

    @Before
    fun initDb() {
        appDB = DBUtil.getTestAppDB()
        dao = appDB.obEventWithRegisterDao()
    }

    @After
    fun closeDb() {
        appDB.close()
    }


    @Test
    fun testGetAllEventsAndReg() {
        var evt = DBUtil.createEvent(appDB,"test1")
        var userList = listOf("phily","luke","mason")
        var regList = DBUtil.ceateRegister(appDB,userList,evt.evtId)
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(dao.getAllEventsAndReg()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("Tag",""+it.size)
                    var ewr = it as OBEventWithRegister

                    assertEquals(ewr.event?.name,"test1")
                    assertEquals(ewr.register.size,3)

                }))

    }
}