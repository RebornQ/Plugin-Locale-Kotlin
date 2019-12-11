package com.mallotec.reb.localeplugin.lifecycle

import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import com.mallotec.reb.localeplugin.utils.LocaleHelper

/**
 * Created by reborn on 2019-12-11.
 */
class LocaleComponentLifecycleCallbacks(private val context: Context)  : ComponentCallbacks {

    override fun onLowMemory() { }

    override fun onConfigurationChanged(newConfig: Configuration) {
        LocaleHelper.getInstance().onConfigurationChanged(context)
    }
}