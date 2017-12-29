package com.acob.blc.acobooking.di


import com.acob.blc.acobooking.ui.ACOBookingApplication
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

    //fun inject(mqttCallback : MqttCallbackBus)

   // fun localStorage(): LocalStorage
}