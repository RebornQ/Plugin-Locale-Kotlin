package com.mallotec.reb.localeplugin.ui.base

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mallotec.reb.localeplugin.receiver.RecreateActivityReceiver
import com.mallotec.reb.localeplugin.utils.LocaleManageUtil


abstract class BaseLocaleAppCompatActivity : AppCompatActivity() {

    private var recreateActivityReceiver: RecreateActivityReceiver? = null

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleManageUtil.updateContext(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 使用 EventBus 可以实现不重启到 LauncherActivity 只需 recreate() 即可刷新 Resources
//        EventBus.getDefault().register(this)

        // 使用广播也可以实现不重启到 LauncherActivity 只需 recreate() 即可刷新 Resources
        recreateActivityReceiver = RecreateActivityReceiver(this)
        registerReceiver(recreateActivityReceiver, recreateActivityReceiver!!.getDefaultIntentFilter())
    }

    // 防止 Locale 被一个新的 Configuration 对象覆盖掉（AppCompat库1.1.0-alpha03以上版本）
    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        overrideConfiguration?.setLocale(LocaleManageUtil.getSetLocale())
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
        unregisterReceiver(recreateActivityReceiver)
    }
}