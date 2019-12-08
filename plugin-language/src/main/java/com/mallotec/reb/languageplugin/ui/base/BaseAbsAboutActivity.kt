package com.mallotec.reb.languageplugin.ui.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.drakeet.about.AbsAboutActivity
import com.mallotec.reb.languageplugin.Constant
import com.mallotec.reb.languageplugin.utils.LocaleManageUtil


abstract class BaseAbsAboutActivity : AbsAboutActivity() {

    class RecreateActivityBroadcastReceiver(private var cont: Context?) : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            if (cont is BaseAbsAboutActivity) {
                (cont as BaseAbsAboutActivity).recreate()
            }
        }
    }

    private var intentFilter: IntentFilter? = null
    private var recreateActivityBroadcastReceiver: RecreateActivityBroadcastReceiver? = null

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleManageUtil.updateContext(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 使用 EventBus 可以实现不重启到 LauncherActivity 只需 recreate() 即可刷新 Resources
//        EventBus.getDefault().register(this)

        // 使用广播也可以实现不重启到 LauncherActivity 只需 recreate() 即可刷新 Resources
        intentFilter = IntentFilter()
        intentFilter!!.addAction(Constant.ACTION_RECREATE_ACTIVITY)
        recreateActivityBroadcastReceiver = RecreateActivityBroadcastReceiver(this)
        registerReceiver(recreateActivityBroadcastReceiver, intentFilter)
    }

    // 防止 Locale 被一个新的 Configuration 对象覆盖掉（AppCompat库1.1.0-alpha03以上版本）
    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        if (overrideConfiguration != null) {
            overrideConfiguration?.setLocale(LocaleManageUtil.getSetLocale())
        }
        super.applyOverrideConfiguration(overrideConfiguration)
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public fun onEvent(str: String?) {
//        when (str) {
//            Constant.EVENT_RECREATE_ACTIVITY -> {
//                recreate() //刷新界面
//            }
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
//        EventBus.getDefault().unregister(this)
        unregisterReceiver(recreateActivityBroadcastReceiver)
    }
}