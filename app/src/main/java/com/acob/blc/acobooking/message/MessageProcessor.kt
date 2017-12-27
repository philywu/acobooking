package com.phily.andr.acobooking.message

import android.content.ContentValues.TAG
import android.util.Log
import com.acob.blc.acobooking.KEY_APP_USER_NAME
import com.acob.blc.acobooking.KEY_MQTT_TOPIC_EVENT
import com.acob.blc.acobooking.data.dao.OBEventDao
import com.acob.blc.acobooking.data.model.OBEvent
import com.acob.blc.acobooking.ui.NotificationHandler
import com.google.gson.Gson
import com.phily.andr.acobooking.data.LocalStorage
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.lang.reflect.Type
import java.util.*
import javax.inject.Inject



/**
 * Created by wugang00 on 12/12/2017.
 */

class MessageProcessor  @Inject constructor(gson: Gson, lStorage: LocalStorage, mqttManager : MqttManager, nHandler: NotificationHandler, eventDao: OBEventDao) {


        var gson = gson
        var localStorage = lStorage
        var eventDao = eventDao
        var nHandler = nHandler
        var mqttManager = mqttManager
        val topicEvent = localStorage.readMessage(KEY_MQTT_TOPIC_EVENT)



        fun processReceivedMessage(topic: String, message: MqttMessage) {
            // var myType = MyMessageType(MyEvent::class.java)

            //var msgWrap = gson.fromJson(message.toString(),MyMessageWrap::class.java)
            val myType = getMessageTypeByTopic(topic)


            var msgWrap: MyMessageWrap<OBEvent>
            msgWrap = gson.fromJson(message.toString(), myType)
            saveToDB(msgWrap.data)
            if (isEventTopic(topic)) {
                subscribeEventRegister(msgWrap.data)
            }
            Log.d(TAG, topic + "==== class" + msgWrap.data)
            nHandler.createEventNotification(msgWrap.data)

        }

        fun saveToDB(event: OBEvent) {
            eventDao.insertEvent(event)
            Log.d(TAG, "Event save to DB")

        }

        fun <T : Any> getJsonString(msgObj: T): String {

            var msgWrap = MyMessageWrap(
                    localStorage.readMessage(KEY_APP_USER_NAME),
                    Date(),
                    msgObj.javaClass.simpleName,
                    msgObj

            )
            var jsonStr = gson.toJson(msgWrap)
            return jsonStr;
        }

        fun getMessageTypeByTopic(topic: String): MyMessageType<*> {


            if (isEventTopic(topic)) {
                return MyMessageType(MyMessageWrap::class.java, arrayOf<Type>(OBEvent::class.java))
            } else {
                return MyMessageType(MyMessageWrap::class.java, arrayOf<Type>(OBEvent::class.java))
            }

        }

        fun isEventTopic(topic: String): Boolean {
            return (topicEvent.length > 0 && topic.equals(topicEvent))
        }

        fun subscribeEventRegister(event: OBEvent) {
            val qos = 1
            val topic = "acobooking/register/" + event.owner + "/" + event.evtId
            if (mqttManager.isConnected()) {
                mqttManager.subscribe(topic, qos)
            }

        }

    fun releaseConnection() {
        mqttManager.release()
    }

    fun <T: Any> mqttPublish(topic:String,msgObj:T, qos:Int){
        if (mqttManager.isConnected()) {
            // mqttManager.publish(topic,qos.toInt(),msg.toByteArray())

            //var jsonStr = gson.toJson(msgObj)
            val jsonStr = getJsonString(msgObj)
            Log.i(TAG,"json: " + jsonStr)
            mqttManager.publish(topic,qos,jsonStr?.toByteArray())

        } else {
            Log.e(TAG,"not connected")
        }
    }
    fun mqttSubscribe(topic:String,qos:Int){
        if (mqttManager.isConnected()) {
            mqttManager.subscribe(topic,qos)
        }
    }

    fun creatConnect(url: String, userName: String?, password: String?, clientId: String) {
            var mCallback = MqttCallbackBus(this)
            mqttManager.creatConnect(url,userName,password,clientId,mCallback)
    }

    fun isServerConnected(): Boolean {
        return mqttManager.isConnected()
    }
}