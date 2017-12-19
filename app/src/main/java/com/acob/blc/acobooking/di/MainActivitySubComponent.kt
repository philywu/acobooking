package com.acob.blc.acobooking.di

import com.acob.blc.acobooking.ui.MainActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector


/**
 * Created by wugang00 on 5/12/2017.
 */
@Subcomponent
interface MainActivitySubComponent : AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()



}