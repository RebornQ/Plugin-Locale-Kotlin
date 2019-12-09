package com.mallotec.reb.languageplugin.receiver

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.mallotec.reb.languageplugin.Constant

class RecreateActivityReceiver(private var cont: Context?) : BroadcastReceiver() {

    fun getDefaultIntentFilter() : IntentFilter? {
        val defaultIntentFilter = IntentFilter()
        defaultIntentFilter.addAction(Constant.ACTION_RECREATE_ACTIVITY)
        return defaultIntentFilter
    }

    fun register() {
        cont!!.registerReceiver(this, getDefaultIntentFilter())
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (cont is Activity) {
            (cont as Activity).recreate()
        }
    }
}