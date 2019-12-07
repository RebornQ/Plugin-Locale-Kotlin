package com.mallotec.reb.languageplugin

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.mallotec.reb.languageplugin.utils.LocaleManageUtil


open class BaseApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    instance = this

    // 设置语言
    LocaleManageUtil.updateApplicationContext(this)
  }

  override fun attachBaseContext(base: Context) {
    LocaleManageUtil.cacheSystemLocale()
    super.attachBaseContext(base)
  }

  override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
    LocaleManageUtil.onConfigurationChanged(this)
  }

  companion object {
     lateinit var instance: BaseApplication
      private set
  }
}