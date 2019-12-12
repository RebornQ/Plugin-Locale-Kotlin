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

    // 用 Map 管理 BroadcastReceiver，保证广播接收器不会被重复注册和注销
    private var receiverMap : HashMap<Activity, BroadcastReceiver>? = HashMap()

    /**
     * 开放 BroadcastReceiver 注册和注销方法给使用者，
     * 方便解决 Activity 对象被回收时还没来得及执行 onDestroy() 方法导致没注销对应的广播接收器引发的内存泄漏
     */

    // 注册 BroadcastReceiver
    fun registerBroadcastReceiver(activity: Activity, receiver: BroadcastReceiver, intentFilter: IntentFilter) {
        if (receiverMap?.get(activity) == null) {
            activity.registerReceiver(receiver, intentFilter)
            receiverMap?.set(activity, receiver)
            Log.e(TAG, activity.localClassName + "#BroadcastReceiver register")
        } else {
            Log.e(TAG, activity.localClassName + "#BroadcastReceiver is already registered")
        }
    }

    // 注销 BroadcastReceiver
    fun unregisterBroadcastReceiver(activity: Activity) {
        receiverMap?.get(activity)?.let {
            activity.unregisterReceiver(it)
            receiverMap?.remove(activity)
            Log.e(TAG, activity.localClassName + "#BroadcastReceiver unregister")
        } ?: Log.e(TAG, activity.localClassName + "#BroadcastReceiver is already unregistered")
    }


    fun unregisterAllBroadcastReceiver() {
        receiverMap?.let {
            for ((activity, receiver) in it) {
                activity.unregisterReceiver(receiver)
            }
        }
        receiverMap?.clear()
        receiverMap = null
    }
}