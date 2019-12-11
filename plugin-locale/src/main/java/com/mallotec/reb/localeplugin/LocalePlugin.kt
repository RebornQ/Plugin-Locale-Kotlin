package com.mallotec.reb.localeplugin

import android.app.Application
import com.mallotec.reb.localeplugin.lifecycle.LocaleActivityLifecycleCallbacks
import com.mallotec.reb.localeplugin.lifecycle.LocaleComponentLifecycleCallbacks
import com.mallotec.reb.localeplugin.utils.ActivityHelper
import com.mallotec.reb.localeplugin.utils.LocaleHelper

/**
 * Created by reborn on 2019-12-10.
 */
object LocalePlugin {

    private fun setLifecycleCallbacks(application: Application) {
        application.registerActivityLifecycleCallbacks(LocaleActivityLifecycleCallbacks())
        application.registerComponentCallbacks(LocaleComponentLifecycleCallbacks(application))
    }

    fun init(application: Application, updateInterfaceWay: Int = LocaleConstant.RECREATE_CURRENT_ACTIVITY): LocalePlugin {
        return this.apply {
            ActivityHelper.init(updateInterfaceWay)
            LocaleDefaultSPHelper.init(application)
            LocaleHelper.init(application)

            setLifecycleCallbacks(application)
            LocaleHelper.getInstance().cacheSystemLocale()
            // 设置语言
            LocaleHelper.getInstance().updateApplicationContext(application)
        }
    }
}