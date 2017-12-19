package com.phily.andr.acobooking.message

import android.util.Log
import com.acob.blc.acobooking.KEY_APP_USER_NAME
import com.acob.blc.acobooking.data.dao.OBEventDao
import com.acob.blc.acobooking.data.model.OBEvent
import com.google.gson.Gson
import com.phily.andr.acobooking.data.LocalStorage
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.lang.reflect.Type
import java.util.*
import javax.inject.Inject



/**
 * Created by wugang00 on 12/12/2017.
 */

class MessageProcessor  @Inject constructor(gson: Gson, lStorage: LocalStorage, eventDao: OBEventDao) {
    val TAG = "Msg Processor"
    var gson = gson
    var localStorage = lStorage
    var eventDao = eventDao
    fun processReceivedMessage(topic: String, message: MqttMessage) {
       // var myType = MyMessageType(MyEvent::class.java)

        //var msgWrap = gson.fromJson(message.toString(),MyMessageWrap::class.java)
        val myType = getMessageTypeByTopic(topic)

        var msgWrap: MyMessageWrap<OBEvent>
        msgWrap = gson.fromJson(message.toString(),myType)
       saveToDB(msgWrap.data)
        Log.d(TAG,topic + "==== class" + msgWrap.data)
    }
    fun saveToDB(event: OBEvent) {
        eventDao.insertEvent(event)
        Log.d(TAG,"Event save to DB")

    }

    fun <T : Any> getJsonString(msgObj: T): String {

        var msgWrap = MyMessageWrap(
                localStorage.readMessage(KEY_APP_USER_NAME),
                Date(),
                msgObj.javaClass.simpleName,
                msgObj

                )
        var jsonStr = gson.toJson(msgWrap)
        return jsonStr ;
    }
    fun getMessageTypeByTopic( topic:String): MyMessageType<*> {
        return MyMessageType(MyMessageWrap::class.java, arrayOf<Type>(OBEvent::class.java))
    }
}