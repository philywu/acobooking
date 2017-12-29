package com.acob.blc.acobooking.presenter

import android.util.Log
import com.acob.blc.acobooking.*
import com.phily.andr.acobooking.data.LocalStorage
import com.phily.andr.acobooking.message.MessageProcessor
import com.phily.andr.acobooking.message.MqttManager

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import io.reactivex.observers.DisposableObserver
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * Created by wugang00 on 5/12/2017.
 */
class MainPresenter @Inject constructor() : BasePresenter()  {


    @Inject lateinit var msgProcessor : MessageProcessor
    @Inject lateinit var localStorage : LocalStorage

    public val TAG = "MainPresnter"


    val compositeDisposable = CompositeDisposable()


    var presentation: MainViewEvent? = null



    fun onCreate(myEventPresentation: MainViewEvent) {
        presentation = myEventPresentation




    }

    fun onDestroy() {
        compositeDisposable.dispose()
        msgProcessor.releaseConnection()
        presentation = null
    }

    fun onConnect(value:String) {

       // if (!"".equals(URL) && !mqttManager.isConnected()) {
        //localStorage.writeMessage(KEY_APP_USER_NAME,"Luke")
        //localStorage.writeMessage(KEY_MQTT_TOPIC_EVENT,"acobooking/event1")
        //userName = localStorage.readMessage(KEY_APP_USER_NAME)

        //Log.d(TAG,"try to connect " + value + " by user: "+ userName)

        //get mqtt connect information

            val add = compositeDisposable.add(Observable.fromCallable(
                    {

                        val b = msgProcessor.creatConnect()
                        Log.d(TAG, "isConnected: " + b)

                        //subscribe
                       msgProcessor.messageSubscribe(msgProcessor.msgTopicEvent,msgProcessor.msgQos)

                        "Connected to MQTT Server"
                    })
                    .delay(1, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<String>() {

                        override fun onNext(s: String) {
                            presentation?.notifMainView(s)
                            Log.d(TAG, s)
                        }

                        override fun onError(e: Throwable) {
                            e.printStackTrace()
                        }

                        override fun onComplete() {
                            Log.d(TAG, "done")

                        }
                    })
            )

    }


}