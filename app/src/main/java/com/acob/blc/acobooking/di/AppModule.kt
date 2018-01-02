package com.acob.blc.acobooking.di

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.acob.blc.acobooking.KEY_DAO_EVENT
import com.acob.blc.acobooking.KEY_DAO_EVENT_REGISTER
import com.acob.blc.acobooking.KEY_DAO_REGISTER
import com.acob.blc.acobooking.data.AppDB
import com.acob.blc.acobooking.data.dao.BaseDao
import com.acob.blc.acobooking.data.dao.OBEventDao
import com.acob.blc.acobooking.data.dao.OBEventWithRegisterDao
import com.acob.blc.acobooking.data.dao.OBRegisterDao
import com.acob.blc.acobooking.ui.NotificationHandler
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.phily.andr.acobooking.data.LocalStorage
import com.phily.andr.acobooking.data.SharedPrefStorage
import com.phily.andr.acobooking.message.MessageProcessor
import com.phily.andr.acobooking.message.MqttManager
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import javax.inject.Singleton
import kotlin.reflect.KClass
import dagger.Binds



/**
 * Created by wugang00 on 13/12/2017.
 */
@Module(includes = arrayOf(AppModule.DaoModule::class))
class AppModule(private val context: Context) {

    var mApplication: Application? = null

    var eventDao: OBEventDao? = null
    var registerDao: OBRegisterDao? = null



    @Provides
    fun providesAppContext() = context

    fun AppModule(application: Application) {
        mApplication = application

/*        var db = providesAppDatabase(context)
        eventDao = db.obEventDao()
        registerDao = db.obRegisterDao()*/
    }

    @Provides
    @Singleton
    fun providesApplication(): Application? {
        return mApplication
    }
    @Provides
    @Singleton
    fun providesAppDatabase(context: Context): AppDB =
            Room.databaseBuilder(context, AppDB::class.java, "acobooking-db")
                    .fallbackToDestructiveMigration()
                    .build()


    @Provides
    @Singleton
    fun providesOBEventDao(database: AppDB) = database.obEventDao()

    @Provides
    @Singleton
    fun providesOBReigisterDao(database: AppDB) = database.obRegisterDao()

    @Provides
    @Singleton
    fun providesOBEventWithRegisterDao(database: AppDB) = database.obEventWithRegisterDao()

    @Provides
    @Singleton
    fun providesMessageProcessor(
            gson : Gson,
            localStorage:LocalStorage,
            mqttManager: MqttManager,
            nHandler:NotificationHandler,
            @DaoScope daoMap:Map<String, out @JvmSuppressWildcards BaseDao>
    ) = MessageProcessor(gson,localStorage,mqttManager,nHandler,daoMap)

    @Provides
    fun providesMqttManager() = MqttManager()


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
    @Provides
    @Singleton
    fun provideNotificationHandler(context: Context): NotificationHandler {
        return NotificationHandler(context)
    }

    @Module
    interface DaoModule {
        @Binds
        @IntoMap
        @DaoScope
        @StringKey(KEY_DAO_EVENT)
        fun bindEventDao(eventDao: OBEventDao):BaseDao

        @Binds
        @IntoMap
        @DaoScope
        @StringKey(KEY_DAO_REGISTER)
        fun bindRegisterDao(register: OBRegisterDao):BaseDao

        @Binds
        @IntoMap
        @DaoScope
        @StringKey(KEY_DAO_EVENT_REGISTER)
        fun bindEventWithRegisterDao(register: OBEventWithRegisterDao):BaseDao
    }
}