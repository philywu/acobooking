package com.acob.blc.acobooking.di

import com.acob.blc.acobooking.ui.EventsActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector


/**
 * Created by wugang00 on 5/12/2017.
 */
@Subcomponent
interface EventsActivitySubComponent : AndroidInjector<EventsActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<EventsActivity>()



}