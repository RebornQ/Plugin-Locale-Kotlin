package pw.gike.multilanguagesdemo.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pw.gike.multilanguagesdemo.Constant
import pw.gike.multilanguagesdemo.interfaces.OnGetActivityListener
import pw.gike.multilanguagesdemo.utils.LocaleManageUtil


abstract class BaseActivity : AppCompatActivity() , OnGetActivityListener {

    class RecreateActivityBroadcastReceiver : BroadcastReceiver() {
        private var myOnGetActivityListener : OnGetActivityListener? = null

        override fun onReceive(context: Context?, intent: Intent?) {
            myOnGetActivityListener?.getActivity()?.recreate()
        }

        public fun setOnGetActivityListener(onGetActivityListener: OnGetActivityListener) {
            myOnGetActivityListener = onGetActivityListener
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
        recreateActivityBroadcastReceiver = RecreateActivityBroadcastReceiver()
        recreateActivityBroadcastReceiver!!.setOnGetActivityListener(this)
        registerReceiver(recreateActivityBroadcastReceiver, intentFilter)
    }

    // 防止 Locale 被一个新的 Configuration 对象覆盖掉（AppCompat库1.1.0-alpha03以上版本）
    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        if (overrideConfiguration != null) {
            overrideConfiguration?.setLocale(LocaleManageUtil.getSetLocale(this))
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

    override fun getActivity(): BaseActivity {
        return this
    }
}