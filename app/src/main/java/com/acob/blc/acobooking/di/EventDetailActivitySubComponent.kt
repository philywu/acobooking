package com.acob.blc.acobooking.di

import com.acob.blc.acobooking.ui.EventDetailActivity
import com.acob.blc.acobooking.ui.EventsActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector


/**
 * Created by wugang00 on 5/12/2017.
 */
@Subcomponent
interface EventDetailActivitySubComponent : AndroidInjector<EventDetailActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<EventDetailActivity>()



}