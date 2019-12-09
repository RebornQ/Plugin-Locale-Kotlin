package pw.gike.multilanguagesdemo

import com.mallotec.reb.localeplugin.BaseLocaleApplication


class App : BaseLocaleApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: App
            private set
    }
}