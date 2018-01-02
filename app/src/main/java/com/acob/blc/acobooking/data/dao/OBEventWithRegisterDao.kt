package com.acob.blc.acobooking.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.acob.blc.acobooking.data.model.OBEvent
import com.acob.blc.acobooking.data.model.OBEventWithRegister
import io.reactivex.Flowable

/**
 * Created by wugang00 on 29/12/2017.
 */

// extend BaseDao , no insert/update/delete action
@Dao
interface OBEventWithRegisterDao :BaseDao {
    @Query("SELECT * from tbl_obevent")
    fun getAllEventsAndReg(): Flowable<List<OBEventWithRegister>>
}