package com.phily.andr.acobooking.message

import java.util.*

/**
 * Created by wugang00 on 12/12/2017.
 */
data class MyMessageWrap <T>(
        var sender: String,
        var pubTime:Date,
        var type:String?,
        var data: T ) {
}