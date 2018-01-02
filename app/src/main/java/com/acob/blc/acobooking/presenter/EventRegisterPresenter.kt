package com.acob.blc.acobooking.presenter

import android.util.Log
import com.acob.blc.acobooking.KEY_APP_USER_NAME
import com.acob.blc.acobooking.data.dao.OBEventDao
import com.acob.blc.acobooking.data.dao.OBEventWithRegisterDao
import com.acob.blc.acobooking.data.model.OBEvent
import com.acob.blc.acobooking.data.model.OBEventWithRegister
import com.acob.blc.acobooking.data.model.OBRegister
import com.phily.andr.acobooking.data.LocalStorage
import com.phily.andr.acobooking.message.MessageProcessor
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
class EventRegisterPresenter @Inject constructor() : BasePresenter() {
    val TAG = "event register presenter"
    var  viewEvent:EventRegisterViewEvent ? = null
    var events = ArrayList<OBEventWithRegister>()
    val compositeDisposable = CompositeDisposable()

    @Inject lateinit var dao: OBEventWithRegisterDao
    @Inject lateinit var localStorage : LocalStorage

    fun onCreate(p: EventRegisterViewEvent) {
        viewEvent = p
        getEventList()

    }
    fun getEventList(){
        compositeDisposable.add(dao.getAllEventsAndReg()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    events.clear()
                    events.addAll(it)

                    viewEvent?.showEvents(events)

                }))
       // viewEvent?.showEvents(events)

    }

}