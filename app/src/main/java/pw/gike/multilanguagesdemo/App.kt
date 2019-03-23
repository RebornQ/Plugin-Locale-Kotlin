package pw.gike.multilanguagesdemo

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import pw.gike.multilanguagesdemo.utils.LocaleManageUtil

class App : Application() {

    override fun attachBaseContext(base: Context) {
        LocaleManageUtil.saveSystemCurrentLanguage(base)
        super.attachBaseContext(base)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        LocaleManageUtil.onConfigurationChanged(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        LocaleManageUtil.setSharePref(this)
        LocaleManageUtil.setApplicationLanguage(this)
    }

    companion object {
        lateinit var instance: App
            private set
    }
}