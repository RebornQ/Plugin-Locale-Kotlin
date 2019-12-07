package pw.gike.multilanguagesdemo

import com.mallotec.reb.languageplugin.BaseApplication


class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: App
            private set
    }
}