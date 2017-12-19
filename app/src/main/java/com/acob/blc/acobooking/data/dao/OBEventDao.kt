package com.acob.blc.acobooking.data.dao

import android.arch.persistence.room.*
import com.acob.blc.acobooking.data.model.OBEvent
import io.reactivex.Flowable

/**
 * Created by wugang00 on 13/12/2017.
 */
@Dao
interface OBEventDao:BaseDao{

    @Query("select * from tbl_obevent")
    fun getAllEvents(): Flowable<List<OBEvent>>

    @Query("select * from tbl_obevent where id = :id")
    fun findEventById(id: Long): OBEvent

    @Query("select * from tbl_obevent where event_id = :evtId")
    fun findEventByEventId(evtId: String): OBEvent

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvent(evt: OBEvent)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateEvent(evt: OBEvent)

    @Delete
    fun deleteEvent(evt: OBEvent)
}