package com.mallotec.reb.localeplugin.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.mallotec.reb.localeplugin.LocaleConstant
import com.mallotec.reb.localeplugin.receiver.RecreateActivityReceiver
import com.mallotec.reb.localeplugin.utils.ActivityHelper
import com.mallotec.reb.localeplugin.utils.LocaleHelper

/**
 * Created by reborn on 2019-12-11.
 */
class LocaleActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    private var receiverMap : HashMap<Activity, RecreateActivityReceiver> = HashMap()

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        LocaleHelper.getInstance().updateContext(activity)
        if (ActivityHelper.getInstance().getInterfaceUpdateWay() == LocaleConstant.RECREATE_CURRENT_ACTIVITY) {
            // 使用广播也可以实现不重启到 LauncherActivity 只需 recreate() 即可刷新 Resources
            val receiver = RecreateActivityReceiver(activity)
            activity.registerReceiver(receiver, receiver.getDefaultIntentFilter())
            receiverMap[activity] = receiver
        }
    }

    override fun onActivityPaused(activity: Activity) { }

    override fun onActivityStarted(activity: Activity) { }

    override fun onActivityDestroyed(activity: Activity) {
        if (ActivityHelper.getInstance().getInterfaceUpdateWay() == LocaleConstant.RECREATE_CURRENT_ACTIVITY) {
            try {
                activity.unregisterReceiver(receiverMap[activity])
                receiverMap.remove(activity)
            } catch (illException: IllegalArgumentException) {
                illException.printStackTrace()
            }
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) { }

    override fun onActivityStopped(activity: Activity) { }

    override fun onActivityResumed(activity: Activity) { }


}