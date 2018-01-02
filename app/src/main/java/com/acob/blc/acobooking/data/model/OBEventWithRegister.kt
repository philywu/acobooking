package com.acob.blc.acobooking.data.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Relation
import com.acob.blc.acobooking.data.dao.BaseDao

/**
 * Created by wugang00 on 29/12/2017.
 */

class OBEventWithRegister{
    @Embedded public var event:OBEvent? = null
    @Relation(parentColumn = "event_id", entityColumn = "event_id")
    var register : List<OBRegister> = ArrayList<OBRegister>()

}