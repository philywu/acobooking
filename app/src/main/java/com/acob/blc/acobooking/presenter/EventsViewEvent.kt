package com.acob.blc.acobooking.presenter

import com.acob.blc.acobooking.data.model.OBEvent
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by wugang00 on 15/12/2017.
 */
interface EventsViewEvent :BaseViewEvent {

    val keyEventName : String
        get() = "EVENT_NAME"
    val keyEventDesc : String
        get() = "EVENT_DESC"
    val keyStartD : String
        get() = "START_D"
    val keyStartT : String
        get() = "START_T"
    val keyDeadlineD : String
        get() = "DEADLINE_D"
    val keyDeadlineT : String
        get() = "DEADLINE_T"

    fun showEvents(events: List<OBEvent>)

    fun taskAddedAt(position: Int)

    fun scrollTo(position: Int)

    fun eventRegister(evtId:String)
    fun eventDelete(evtId:String)
}