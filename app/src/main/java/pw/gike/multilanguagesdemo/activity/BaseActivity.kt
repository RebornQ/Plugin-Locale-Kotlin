package pw.gike.multilanguagesdemo.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import pw.gike.multilanguagesdemo.utils.LocaleManageUtil

abstract class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleManageUtil.updateContext(newBase))
    }
}