package com.mallotec.reb.localeplugin.utils

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.util.Log

/**
 * Created by reborn on 2019-12-12.
 */
object BroadcastReceiverManager {

    private const val TAG = "ReceiverManager"

    var receiverMap : HashMap<Activity, BroadcastReceiver> = HashMap()
        private set

    fun registerBroadcastReceiver(activity: Activity, receiver: BroadcastReceiver, intentFilter: IntentFilter) {
        if (receiverMap[activity] == null) {
            activity.registerReceiver(receiver, intentFilter)
            receiverMap[activity] = receiver
        } else {
            Log.e(TAG, activity.localClassName + "#BroadcastReceiver is already registered")
        }
    }

    fun unregisterBroadcastReceiver(activity: Activity) {
        receiverMap[activity]?.let {
            activity.unregisterReceiver(it)
            receiverMap.remove(activity)
        } ?: Log.e(TAG, activity.localClassName + "#BroadcastReceiver is already unregistered")
    }

    private fun unregisterAllBroadcastReceiver() {
        for ((activity, receiver) in receiverMap) {
            activity.unregisterReceiver(receiver)
        }
        receiverMap.clear()
    }
}