package pw.gike.multilanguagesdemo

import com.mallotec.reb.localeplugin.BaseLocaleApplication
import com.mallotec.reb.localeplugin.LocaleConstant
import com.mallotec.reb.localeplugin.LocalePlugin


class App : BaseLocaleApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        LocalePlugin
            .setUpdateInterfaceWay(LocaleConstant.CUSTOM_WAY_TO_UPDATE_INTERFACE)   // 若调用时参数为空，则默认方式， recreate()
            .init()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}