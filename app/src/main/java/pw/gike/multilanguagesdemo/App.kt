package pw.gike.multilanguagesdemo

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import pw.gike.multilanguagesdemo.utils.LocaleManageUtil
import pw.gike.multilanguagesdemo.utils.SharePrefUtils

class App : Application() {

    override fun attachBaseContext(base: Context) {
        LocaleManageUtil.cacheSystemLocale(base)
        super.attachBaseContext(base)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        LocaleManageUtil.onConfigurationChanged(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        setSharePref(this)
        LocaleManageUtil.updateApplicationContext(this)
    }

    private fun setSharePref(context: Context){
        sharePrefUtils = SharePrefUtils
            .Builder()
            .setContext(context)
            .setPref(Constant.LANGUAGE_SP)
            .create()
    }

    companion object {
        lateinit var instance: App
            private set
        lateinit var sharePrefUtils: SharePrefUtils
            private set
    }
}