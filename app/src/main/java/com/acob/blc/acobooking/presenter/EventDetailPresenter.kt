package com.acob.blc.acobooking.presenter

import android.util.Log
import com.acob.blc.acobooking.KEY_APP_USER_NAME
import com.acob.blc.acobooking.data.model.OBEvent
import com.phily.andr.acobooking.data.LocalStorage
import com.phily.andr.acobooking.message.MessageProcessor
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

/**
 * Created by wugang00 on 15/12/2017.
 */
class EventDetailPresenter @Inject constructor() : BasePresenter() {
    val TAG = "detail presenter"
    var  viewEvent:EventDetailViewEvent ? = null
    val compositeDisposable = CompositeDisposable()


    @Inject lateinit var msgProcessor : MessageProcessor
    @Inject lateinit var localStorage : LocalStorage
    fun onCreate(p: EventDetailViewEvent) {
        viewEvent = p

    }

    fun doAdd(eventMap: HashMap<String, String>) {
        Log.d(TAG, "====== Start adding event========")

        var name = eventMap.get(viewEvent?.keyEventName)!!
        var desc =  eventMap.get(viewEvent?.keyEventDesc)!!
        var startDt = dateParse(eventMap.get(viewEvent?.keyStartD) + " " + eventMap.get(viewEvent?.keyStartT))
        var endDt = dateParse(eventMap.get(viewEvent?.keyEndD) + " " + eventMap.get(viewEvent?.keyEndT))
        var deadlineDt = dateParse(eventMap.get(viewEvent?.keyDeadlineD) + " " + eventMap.get(viewEvent?.keyDeadlineT))
        var owner = localStorage.readMessage(KEY_APP_USER_NAME)//to-do
        var status = "NEW"
        var eventId = UUID.randomUUID().toString()
        var obEvent = OBEvent(eventId,name,desc,startDt,endDt,deadlineDt,owner,status, Date())
        Log.d(TAG,obEvent.toString())
        msgProcessor.messagePublish(msgProcessor.msgTopicEvent,obEvent,msgProcessor.msgQos)

    }

}