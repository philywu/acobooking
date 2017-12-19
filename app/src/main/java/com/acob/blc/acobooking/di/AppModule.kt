package com.acob.blc.acobooking.di

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.acob.blc.acobooking.data.AppDB
import com.acob.blc.acobooking.data.dao.OBEventDao
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.phily.andr.acobooking.data.LocalStorage
import com.phily.andr.acobooking.data.SharedPrefStorage
import com.phily.andr.acobooking.message.MessageProcessor
import com.phily.andr.acobooking.message.MqttCallbackBus
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by wugang00 on 13/12/2017.
 */
@Module
class AppModule(private val context: Context) {

    var mApplication: Application? = null

    @Provides
    fun providesAppContext() = context

    fun AppModule(application: Application) {
        mApplication = application
    }

    @Provides
    @Singleton
    fun providesApplication(): Application? {
        return mApplication
    }
    @Provides
    @Singleton
    fun providesAppDatabase(context: Context): AppDB =
            Room.databaseBuilder(context, AppDB::class.java, "acobooking-db").build()

    @Provides
    @Singleton
    fun providesOBEventDao(database: AppDB) = database.obEventDao()


    @Provides
    fun providesMqttCallbackBus(processor : MessageProcessor) = MqttCallbackBus(processor)

    @Provides
    fun providesMessageProcessor(gson : Gson,localStorage:LocalStorage, eventDao: OBEventDao) = MessageProcessor(gson,localStorage,eventDao)


    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyyMMddHHmmss")
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideLocalStorage(): LocalStorage {
        return SharedPrefStorage(context);
    }


}