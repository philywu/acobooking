package com.phily.andr.acobooking.message

import android.content.ContentValues.TAG
import android.util.Log
import com.acob.blc.acobooking.*
import com.acob.blc.acobooking.data.dao.OBEventDao
import com.acob.blc.acobooking.data.dao.OBRegisterDao
import com.acob.blc.acobooking.data.model.OBEvent
import com.acob.blc.acobooking.data.model.OBRegister
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

class MessageProcessor  @Inject constructor(gson: Gson, lStorage: LocalStorage, mqttManager : MqttManager, nHandler: NotificationHandler, eventDao: OBEventDao,registerDao: OBRegisterDao) {


    val msgTopicEvent = "acobooking/event" //localStorage.readMessage(KEY_MQTT_TOPIC_EVENT)
    val msgTopicRegisterPrefix = "acobooking/register/"
    val msgQos= 1 // localStorage.readMessage(KEY_MQTT_QOS)

    var gson = gson
        var localStorage = lStorage
        var eventDao = eventDao
        var registerDao = registerDao
        var nHandler = nHandler
        var mqttManager = mqttManager

        var mCallback = MqttCallbackBus(this)
        val client_prefix = "acob_client_"

        fun processReceivedMessage(topic: String, message: MqttMessage) {
            // var myType = MyMessageType(MyEvent::class.java)

            //var msgWrap = gson.fromJson(message.toString(),MyMessageWrap::class.java)
            val className = getClassNameByTopic(topic)

            when (className) {
                OBEvent::class.java.simpleName -> {
                    val myType = MyMessageType(MyMessageWrap::class.java, arrayOf<Type>(OBEvent::class.java))
                    var msgWrap: MyMessageWrap<OBEvent>
                    msgWrap = gson.fromJson(message.toString(), myType)
                    saveEvent(msgWrap.data)
                    var event = msgWrap.data as OBEvent

                    val topic=msgTopicRegisterPrefix+event.owner+"/"+event.evtId
                    messageSubscribe(topic,msgQos)
                    Log.d(TAG, topic + "==== class" + msgWrap.data)
                    nHandler.createEventNotification(msgWrap.data)

                }
                OBRegister::class.java.simpleName -> {
                    val myType = MyMessageType(MyMessageWrap::class.java, arrayOf<Type>(OBRegister::class.java))
                    var msgWrap: MyMessageWrap<OBRegister> = gson.fromJson(message.toString(), myType)
                    saveRegister(msgWrap.data)

                    Log.d(TAG, topic + "==== class" + msgWrap.data)


                }

            }



        }

    private fun saveRegister(data: OBRegister) {
        var evt = registerDao.findRegisterByEventUser(data.evtId,data.userName)
        if (evt == null ) {
            registerDao.insert(data)
        }
        Log.d(TAG, "Event Register save to DB")
    }

    private fun getClassNameByTopic(topic: String): String? {
        return when {
            topic.equals(msgTopicEvent) -> OBEvent::class.java.simpleName //MyMessageType(MyMessageWrap::class.java, arrayOf<Type>(OBEvent::class.java))
            topic.startsWith(msgTopicRegisterPrefix) ->OBRegister::class.java.simpleName
            else -> null
        }


    }

    fun saveEvent(event: OBEvent) {
            var evt = eventDao.findEventByEventId(event.evtId)
            if (evt == null ) {
                eventDao.insertEvent(event)
            }
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

        /*fun getMessageTypeByTopic(topic: String): Type? {

           return when {
                topic.equals(msgTopicEvent) -> //MyMessageType(MyMessageWrap::class.java, arrayOf<Type>(OBEvent::class.java))
                topic.startsWith(msgTopicRegisterPrefix) -> MyMessageType(MyMessageWrap::class.java, arrayOf<Type>(OBRegister::class.java))
                else -> null
            }
}
*/




    fun releaseConnection() {
        mqttManager.release()
    }

    fun <T: Any> messagePublish(topic:String, msgObj:T, qos:Int){
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
    fun messageSubscribe(topic:String, qos:Int){
        if (mqttManager.isConnected()) {
            mqttManager.subscribe(topic,qos)
        }
    }



    fun isServerConnected(): Boolean {
        return mqttManager.isConnected()
    }
    fun creatConnect():Boolean {
        //mqtt connection information
         var URL = ""

        //private String userName = "sammy";
         var userName: String? = null

        //private String password = "password";
         var password: String? = null

         var clientId = ""

        var mqttTopicEvent =""
        var mqttQos = 0
        var clientRandom = 0

        //set a random number
        clientRandom = Random().nextInt(1000)
        //read setting


        val serverString = localStorage.readMessage(KEY_MQTT_SERVER)
        val portString = localStorage.readMessage(KEY_MQTT_PORT)
        if (!"".equals(serverString) && !"".equals(portString)) {
            URL = "tcp://" + localStorage.readMessage(KEY_MQTT_SERVER) + ":" + localStorage.readMessage(KEY_MQTT_PORT)

            clientId = client_prefix+ localStorage.readMessage(KEY_APP_USER_NAME) //+"_"+ clientRandom
        }
        Log.d(TAG, "trying connect to " + URL + " with " + clientId)
        return  mqttManager.creatConnect(URL,userName,password,clientId,mCallback)
    }
}