package com.acob.blc.acobooking.ui

import android.R
import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.TaskStackBuilder
import com.acob.blc.acobooking.data.model.OBEvent
import javax.inject.Inject

@TargetApi(Build.VERSION_CODES.O)
/**
 * Created by wugang00 on 21/12/2017.
 */
class NotificationHandler @Inject constructor(context: Context){
    var context = context
    private val channelID: String = "notifChannelACOB"
    private val notificationEventID :Int = 101
    private var mNotificationManager: NotificationManager? = null
    init {
        mNotificationManager =  context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val id = channelID
        val name = "test name"
        val description ="Notification Description"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val mChannel = NotificationChannel(id, name, importance)
// Configure the notification channel.
        mChannel.setDescription(description)
        mChannel.enableLights(true)
// Sets the notification light color for notifications posted to this
// channel, if the device supports this feature.
        mChannel.lightColor = Color.RED
        mChannel.enableVibration(true)
        mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        mNotificationManager?.createNotificationChannel(mChannel)
    }

    fun createEventNotification(event:OBEvent){
        val inboxStyle = NotificationCompat.InboxStyle()

        inboxStyle.setBigContentTitle("A new Event is Received")
        inboxStyle.addLine("Name: ${event.name}")
        inboxStyle.addLine("Detail: ${event.description}")
        inboxStyle.addLine("Date: ${event.startTime}")

        // Building the notification
        val nBuilder = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_dialog_email) // notification icon
                .setContentTitle("A new Event is Received!") // title of notification
                .setContentText("Name: ${event.name}") // text inside the notification
                .setChannelId(channelID)
                .setStyle(inboxStyle) // adds the expandable content to the notification

        mNotificationManager!!.notify(notificationEventID, nBuilder.build())

    }
    fun createEventNotificationSimple(event:OBEvent) {

        // Creates an explicit intent for an Activity
        val resultIntent = Intent(context, EventsActivity::class.java)

        // Creating a artifical activity stack for the notification activity
        val stackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addParentStack(EventsActivity::class.java)
        stackBuilder.addNextIntent(resultIntent)

        // Pending intent to the notification manager
        val resultPending = stackBuilder
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        // Building the notification
        val mBuilder = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_menu_add) // notification icon
                .setContentTitle("New Event Received") // main title of the notification
                .setContentText("A new Event "+event.name+" received") // notification text
                .setChannelId(channelID)
                .setContentIntent(resultPending) // notification intent

        // mId allows you to update the notification later on.
        mNotificationManager!!.notify(10, mBuilder.build())


    }



}