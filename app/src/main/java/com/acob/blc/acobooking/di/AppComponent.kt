package com.acob.blc.acobooking.di


import com.acob.blc.acobooking.ui.ACOBookingApplication
import com.acob.blc.acobooking.ui.EventDetailActivity
import com.acob.blc.acobooking.ui.EventsActivity
import com.acob.blc.acobooking.ui.MainActivity
import dagger.Component
import javax.inject.Singleton




/**
 * Created by wugang00 on 5/12/2017.
 */
@Singleton
@Component(modules =arrayOf ( AppModule::class,
                                ActivityModule::class ))
interface AppComponent  {

    fun inject(application: ACOBookingApplication)
    fun inject(mainActivity : MainActivity)
    fun inject(eventsActivity : EventsActivity)
    fun inject(eventDetailActivity : EventDetailActivity)
    //fun inject(mqttCallback : MqttCallbackBus)

   // fun localStorage(): LocalStorage
}