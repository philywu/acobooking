package com.acob.blc.acobooking.presenter

import com.acob.blc.acobooking.data.model.OBEventWithRegister

/**
 * Created by wugang00 on 1/01/2018.
 */
interface EventRegisterViewEvent :BaseViewEvent {
    fun showEvents(events: List<OBEventWithRegister>) {}

}