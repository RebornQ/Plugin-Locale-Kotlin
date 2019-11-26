package pw.gike.multilanguagesdemo.activity

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import pw.gike.multilanguagesdemo.utils.LocaleManageUtil

abstract class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleManageUtil.updateContext(newBase))
    }

    // 防止 Locale 被一个新的 Configuration 对象覆盖掉（AppCompat库1.1.0-alpha03以上版本）
    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        if (overrideConfiguration != null) {
            overrideConfiguration?.setLocale(LocaleManageUtil.getSetLocale(this))
        }
        super.applyOverrideConfiguration(overrideConfiguration)
    }
}