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
class MainPresenter @Inject constructor() {

    @Inject lateinit var mqttManager: MqttManager
    @Inject lateinit var msgProcessor : MessageProcessor
    @Inject lateinit var localStorage : LocalStorage

    public val TAG = "MQTT Presnter"
    var URL = ""

    //private String userName = "sammy";
    private var userName: String? = null

    //private String password = "password";
    private var password: String? = null

    private var clientId = ""

    var mqttTopicEvent =""
    var mqttQos = 0
    var clientRandom = 0

    val compositeDisposable = CompositeDisposable()


    var presentation: MainViewEvent? = null



    fun onCreate(myEventPresentation: MainViewEvent) {
        presentation = myEventPresentation

        //set a random number
        clientRandom = Random().nextInt(1000)
        //read setting
        mqttTopicEvent =  localStorage.readMessage(KEY_MQTT_TOPIC_EVENT)
        val qosString = localStorage.readMessage(KEY_MQTT_QOS)

        mqttQos = when (qosString) {
            "" -> 0
            else -> Integer.parseInt(qosString)
        }
        val serverString = localStorage.readMessage(KEY_MQTT_SERVER)
        val portString = localStorage.readMessage(KEY_MQTT_PORT)
        if (!"".equals(serverString) && !"".equals(portString)) {
            URL = "tcp://" + localStorage.readMessage(KEY_MQTT_SERVER) + ":" + localStorage.readMessage(KEY_MQTT_PORT)
            clientId = "acob_client_" + localStorage.readMessage(KEY_APP_USER_NAME)+"_"+ clientRandom
        }


    }

    fun onDestroy() {
        compositeDisposable.dispose()
        mqttManager.release()
        presentation = null
    }
/*
    fun mqttPublish(topic:String,msg:String,qos:Integer){
        if (mqttManager.isConnected()) {
            mqttManager.publish(topic,qos.toInt(),msg.toByteArray())
        }
    }
    */
    fun <T: Any> mqttPublish(topic:String,msgObj:T, qos:Int){
        if (mqttManager.isConnected()) {
           // mqttManager.publish(topic,qos.toInt(),msg.toByteArray())

            //var jsonStr = gson.toJson(msgObj)
            val jsonStr = msgProcessor.getJsonString(msgObj)
            Log.i(TAG,"json: " + jsonStr)
           mqttManager.publish(topic,qos,jsonStr?.toByteArray())

        }
    }
    fun mqttSubscribe(topic:String,qos:Int){
        if (mqttManager.isConnected()) {
            mqttManager.subscribe(topic,qos)
        }
    }
    fun onConnect(value:String) {

        if (!"".equals(URL) && !mqttManager.isConnected()) {
        //localStorage.writeMessage(KEY_APP_USER_NAME,"Luke")
        //localStorage.writeMessage(KEY_MQTT_TOPIC_EVENT,"acobooking/event1")
        //userName = localStorage.readMessage(KEY_APP_USER_NAME)

        //Log.d(TAG,"try to connect " + value + " by user: "+ userName)

        //get mqtt connect information

            val add = compositeDisposable.add(Observable.fromCallable(
                    {
                        Log.d(TAG, "trying connect to " + URL + " with " + clientId)
                        val b = mqttManager?.creatConnect(URL, userName, password, clientId)
                        Log.d(TAG, "isConnected: " + b)

                        //subscribe
                        mqttSubscribe(mqttTopicEvent,mqttQos)

                        "call end"
                    })
                    .delay(2, TimeUnit.SECONDS)
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

        } else {
            Log.d(TAG,"already connected")
        }
    }


}