package com.acob.blc.acobooking.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Created by wugang00 on 13/12/2017.
 */
@Entity(tableName = "tbl_obevent")
data class OBEvent(
        @ColumnInfo(name = "event_id") var evtId: String,
        @ColumnInfo(name = "event_name") var name: String,
        @ColumnInfo(name = "event_desc") var description: String,
        @ColumnInfo(name = "event_start_t") var startTime: Date,
        @ColumnInfo(name = "event_end_t") var endTime: Date,
        @ColumnInfo(name = "book_deadline_t") var deadline: Date,
        @ColumnInfo(name = "event_owner") var owner: String,
        @ColumnInfo(name = "event_status") var status: String,
        @ColumnInfo(name = "event_create_t") var createTime: Date

) {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}