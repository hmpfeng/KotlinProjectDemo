package com.wsst.lxns.base.http

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by 谢岳峰 on 2018/10/31.
 */
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@kotlin.annotation.Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class RequestTypeDef(
        /** Defines the allowed constants for this element  */
        vararg val value: RequestType = [],
        /** Defines whether the constants can be used as a flag, or just as an enum (the default)  */
        val flag: Boolean = false)


