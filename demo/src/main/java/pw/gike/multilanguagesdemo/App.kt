package pw.gike.multilanguagesdemo

import com.mallotec.reb.languageplugin.BaseLocaleApplication


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