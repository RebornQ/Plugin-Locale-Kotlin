package pw.gike.multilanguagesdemo.activity

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import com.mallotec.reb.localeplugin.utils.LocaleHelper

/**
 * Created by reborn on 2019-12-12.
 */
open class BaseActivity : AppCompatActivity() {

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        overrideConfiguration?.setLocale(LocaleHelper.getInstance().getSetLocale())
        super.applyOverrideConfiguration(overrideConfiguration)
    }
}