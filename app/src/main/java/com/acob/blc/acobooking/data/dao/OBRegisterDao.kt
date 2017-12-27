package com.acob.blc.acobooking.data.dao

import android.arch.persistence.room.*
import com.acob.blc.acobooking.data.model.OBRegister
import io.reactivex.Flowable

/**
 * Created by wugang00 on 26/12/2017.
 */
@Dao
interface OBRegisterDao:BaseDao<OBRegister>{

    @Query("select * from tbl_obregister")
    fun getAllRegister(): Flowable<List<OBRegister>>

    @Query("select * from tbl_obregister where id = :id")
    fun findRegisterById(id: Long): OBRegister

    @Query("select * from tbl_obregister where event_id = :evtId and user_name= :userName")
    fun findRegisterByEventUser(evtId: String, userName:String): OBRegister

}