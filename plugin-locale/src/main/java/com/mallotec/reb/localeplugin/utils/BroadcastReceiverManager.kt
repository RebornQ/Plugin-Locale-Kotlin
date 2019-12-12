package com.mallotec.reb.localeplugin.utils

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.IntentFilter

/**
 * Created by reborn on 2019-12-12.
 */
object BroadcastReceiverManager {

    var receiverMap : HashMap<Activity, BroadcastReceiver> = HashMap()
        private set

    fun registerBroadcastReceiver(activity: Activity, receiver: BroadcastReceiver, intentFilter: IntentFilter) {
        activity.registerReceiver(receiver, intentFilter)
        receiverMap[activity] = receiver
    }

    fun unregisterBroadcastReceiver(activity: Activity) {
        receiverMap[activity]?.let {
            activity.unregisterReceiver(it)
            receiverMap.remove(activity)
        }
    }

    private fun unregisterAllBroadcastReceiver() {
        for ((activity, receiver) in receiverMap) {
            activity.unregisterReceiver(receiver)
        }
        receiverMap.clear()
    }
}