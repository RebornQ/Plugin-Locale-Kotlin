package pw.gike.multilanguagesdemo

import android.app.Application
import com.mallotec.reb.localeplugin.LocaleConstant
import com.mallotec.reb.localeplugin.LocalePlugin


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        LocalePlugin.init(this, LocaleConstant.RECREATE_CURRENT_ACTIVITY)
    }

    companion object {
        lateinit var instance: App
            private set
    }
}