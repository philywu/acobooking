package com.acob.blc.acobooking.presenter

import android.util.Log
import com.acob.blc.acobooking.KEY_APP_USER_NAME
import com.acob.blc.acobooking.KEY_MQTT_QOS
import com.acob.blc.acobooking.KEY_MQTT_SERVER
import com.acob.blc.acobooking.KEY_MQTT_TOPIC_EVENT
import com.acob.blc.acobooking.data.dao.OBEventDao
import com.acob.blc.acobooking.data.model.OBEvent
import com.phily.andr.acobooking.data.LocalStorage
import com.phily.andr.acobooking.message.MessageProcessor
import com.phily.andr.acobooking.message.MqttManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * Created by wugang00 on 15/12/2017.
 */
class EventsPresenter @Inject constructor() : BasePresenter() {
    val TAG = "event list presenter"
    var  viewEvent:EventsViewEvent ? = null
    var events = ArrayList<OBEvent>()
    val compositeDisposable = CompositeDisposable()

    @Inject lateinit var eventDao: OBEventDao
    @Inject lateinit var localStorage : LocalStorage
    fun onCreate(p: EventsViewEvent) {
        viewEvent = p
        getEventList()
    }
    fun getEventList(){
        compositeDisposable.add(eventDao.getAllEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    events.clear()
                    events.addAll(it)
                    (events.size - 1).takeIf { it >= 0 }?.let {
                        //viewEvent?.taskAddedAt(it)
                        //viewEvent?.scrollTo(it)
                    }
                    viewEvent?.showEvents(events)

                }))
       // viewEvent?.showEvents(events)

    }
    fun deleteEvent(evtId:String) {
        compositeDisposable.add(
                Observable.fromCallable(
                        {
                            var evt = eventDao.findEventByEventId(evtId)
                            Log.d(TAG,evt.toString())
                            eventDao.deleteEvent(evt)
                            evt
                        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableObserver<OBEvent>() {

                            override fun onNext(evt: OBEvent) {
                               events.remove(evt)
                                viewEvent?.showEvents(events)
                                Log.d(TAG, "deleted" + evt.evtId)
                            }

                            override fun onError(e: Throwable) {
                                e.printStackTrace()
                            }

                            override fun onComplete() {
                                Log.d(TAG, "delete done")

                            }
                        }))
    }
}