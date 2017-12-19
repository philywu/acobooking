package com.acob.blc.acobooking.di

import android.app.Activity
import com.acob.blc.acobooking.ui.EventDetailActivity
import com.acob.blc.acobooking.ui.EventsActivity
import com.acob.blc.acobooking.ui.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by wugang00 on 13/12/2017.
 */
@Module(subcomponents = arrayOf(
        MainActivitySubComponent::class,
        EventsActivitySubComponent::class,
        EventDetailActivitySubComponent::class

))
abstract class ActivityModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    internal abstract fun bindsMainActivityInjectorFactory(builder: MainActivitySubComponent.Builder): AndroidInjector.Factory<out Activity>

    @Binds
    @IntoMap
    @ActivityKey(EventsActivity::class)
    internal abstract fun bindsEventsActivityInjectorFactory(builder: EventsActivitySubComponent.Builder): AndroidInjector.Factory<out Activity>

    @Binds
    @IntoMap
    @ActivityKey(EventDetailActivity::class)
    internal abstract fun bindsEventDetailActivityInjectorFactory(builder: EventDetailActivitySubComponent.Builder): AndroidInjector.Factory<out Activity>
}
