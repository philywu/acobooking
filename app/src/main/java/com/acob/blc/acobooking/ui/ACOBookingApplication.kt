package com.acob.blc.acobooking.ui

import android.app.Activity
import android.app.Application
import com.acob.blc.acobooking.di.AppComponent
import com.acob.blc.acobooking.di.AppModule
import com.acob.blc.acobooking.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by wugang00 on 13/12/2017.
 */
class  ACOBookingApplication : Application(), HasActivityInjector {

    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    lateinit var appComponent: AppComponent


    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(applicationContext))
                .build()

        appComponent.inject(this)

    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    fun getComponent(): AppComponent {
        return appComponent
    }
}