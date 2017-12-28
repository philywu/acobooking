package com.acob.blc.acobooking.presenter

import com.acob.blc.acobooking.data.model.OBEvent
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by wugang00 on 15/12/2017.
 */
interface EventDetailViewEvent :BaseViewEvent {

    val keyEventName : String
        get() = "EVENT_NAME"
    val keyEventDesc : String
        get() = "EVENT_DESC"
    val keyStartD : String
        get() = "START_D"
    val keyEndD : String
        get() = "END_D"
    val keyStartT : String
        get() = "START_T"
    val keyEndT : String
        get() = "END_T"

    val keyDeadlineD : String
        get() = "DEADLINE_D"
    val keyDeadlineT : String
        get() = "DEADLINE_T"


}