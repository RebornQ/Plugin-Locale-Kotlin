package com.mallotec.reb.languageplugin.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import android.util.Log
import com.mallotec.reb.languageplugin.BaseApplication
import com.mallotec.reb.languageplugin.DefaultSPHelper
import com.mallotec.reb.languageplugin.R
import java.util.*

object LocaleManageUtil {

    private val TAG = "LocaleManageUtil"

    // 静态属性，修复识别系统语言为英语的问题
    private var currentSystemLocale = Locale.SIMPLIFIED_CHINESE

    /**
     * 获取已选择的语言设置
     */
    fun getSetLocale(): Locale {
        return when (DefaultSPHelper.language) {
            "0" -> currentSystemLocale
            "1" -> Locale.ENGLISH
            "2" -> Locale.SIMPLIFIED_CHINESE
            "3" -> Locale.TRADITIONAL_CHINESE
            else -> Locale.SIMPLIFIED_CHINESE
        }
    }

    fun getSelectLanguageString(context: Context): String {
        return when (DefaultSPHelper.language) {
            "0" -> context.resources.getStringArray(R.array.language_titles)[0]
            "1" -> context.resources.getStringArray(R.array.language_titles)[1]
            "2" -> context.resources.getStringArray(R.array.language_titles)[2]
            "3" -> context.resources.getStringArray(R.array.language_titles)[3]
            else -> context.resources.getStringArray(R.array.language_titles)[3]
        }
    }
    
    fun updateContext(context: Context): Context {
        return updateResources(context, getSetLocale())
    }

    private fun updateResources(context: Context, locale: Locale): Context {
        Locale.setDefault(locale)

        var cont = context
        val res = cont.resources
        val dm = res.displayMetrics
        val config = res.configuration
        // 更新configuration，防止返回的context是更新config前的状态
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setAppLocale(config, locale)
            // ApplicationContext貌似没办法在其他地方更新configuration
            cont = cont.createConfigurationContext(config)  // 对于8.0+系统必须先调用context.createConfigurationContext(config);否则后面的updateConfiguration不起作用。
        } else {
            setAppLocaleLegacy(config, locale)
        }
        res.updateConfiguration(config, dm)
        return cont
    }

    @Suppress("DEPRECATION")
    private fun setAppLocaleLegacy(config: Configuration, locale: Locale) {
        config.locale = locale
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun setAppLocale(config: Configuration, locale: Locale) {
        config.setLocale(locale)
        val localeList = LocaleList(locale)
        LocaleList.setDefault(localeList)
        config.setLocales(localeList)
    }

    /**
     * 设置语言类型
     */
    fun updateApplicationContext(context: Context) : Context {
        return updateContext(context.applicationContext)
    }

    private fun getSystemLocaleLegacy(): Locale {
        return Locale.getDefault()
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun getSystemLocale(): Locale {
        return LocaleList.getDefault().get(0)
    }

    // 只有第一次存下来的 Locale 才是正确的系统语言，后面 LocaleList 会随着切换语言变动导致 getSystemLocale() 获取的不再是系统语言
    fun cacheSystemLocale() {
        val locale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getSystemLocale()
        } else {
            getSystemLocaleLegacy()
        }
        currentSystemLocale = locale
    }

    // 因此若要获取系统语言只能拿已缓存下来的 Locale
    fun getCurrentSystemLocale(): Locale {
        return currentSystemLocale
    }

    fun onConfigurationChanged(context: Context) {
        cacheSystemLocale()
        updateContext(context)
        updateApplicationContext(context)
    }

    fun applyLanguage(context: Context, select: String) {
        // 需要先保存选择的语言，否则更新 application 的语言配置时，拿到的还是上次配置的语言
        DefaultSPHelper.language = select
        // recreate() 后只在 BaseActivity#attachBaseContext() 更新 Context，不更新 ApplicationContext，因此要手动更新
        updateApplicationContext(BaseApplication.instance)

        // 使用 EventBus 可以实现不重启到 LauncherActivity 只需 recreate() 即可刷新 Context 的 Resources
//        EventBus.getDefault().post(Constant.EVENT_RECREATE_ACTIVITY)
        // 使用广播也可以实现不重启到 LauncherActivity 只需 recreate() 即可刷新 Context 的 Resources
        ActivityUtil.recreateActivity(context)
        // 重启到 LauncherActivity 刷新 Context 的 Resources
//        ActivityUtil.toRestartLauncherActivity(context)
    }

    // 输出当前context使用的语言，仅调试用
    @Suppress("DEPRECATION")
    @TargetApi(Build.VERSION_CODES.N)
    fun printContextLocale(context: Context, tag: String) {
        val resources = context.resources
        val config = resources.configuration
        Log.d("Locale-$tag", config.locale.language)
        Log.d("Locale-$tag", config.locales.toLanguageTags())
    }
}
