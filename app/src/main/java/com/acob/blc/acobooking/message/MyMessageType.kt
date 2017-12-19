package com.phily.andr.acobooking.message

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type





/**
 * Created by wugang00 on 12/12/2017.
 */
class MyMessageType<T>(private val cl: Class<T>, val args: Array<Type>) : ParameterizedType {

    override fun getActualTypeArguments(): Array<Type> {

        return args
    }

    override fun getRawType(): Type {
        return cl
    }

    override fun getOwnerType(): Type? {
        return null
    }
}