package com.thk.findbook.utils

import android.util.Log

inline fun <reified T> T.logd(msg: String) = Log.d(T::class.java.simpleName, msg)