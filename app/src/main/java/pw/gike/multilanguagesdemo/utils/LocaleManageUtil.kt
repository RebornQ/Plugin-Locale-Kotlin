package pw.gike.multilanguagesdemo.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import android.util.Log
import pw.gike.multilanguagesdemo.App
import pw.gike.multilanguagesdemo.Constant
import pw.gike.multilanguagesdemo.R
import pw.gike.multilanguagesdemo.activity.MainActivity
import java.util.*

object LocaleManageUtil {

    private val TAG = "LocaleManageUtil"

    // 静态属性，修复识别系统语言为英语的问题
    private var currentSystemLocale = Locale.ENGLISH

    fun saveLanguage(select: Int) {
        App.sharePrefUtils
            .dataPrepare(Constant.TAG_LANGUAGE, select)
            .putData()
    }

    fun getSelectLanguage(): Int {
        return App.sharePrefUtils.getInt(Constant.TAG_LANGUAGE)
    }

    fun setSystemCurrentLocal(local: Locale) {
        currentSystemLocale = local
    }


    /**
     * 获取系统的locale
     *
     * @return Locale对象
     */
    fun getSystemLocale(context: Context): Locale {
        return currentSystemLocale
    }

    fun getSelectLanguage(context: Context): String {
        return when (getSelectLanguage()) {
            0 -> context.getString(R.string.text_language_auto)
            1 -> context.getString(R.string.text_language_zh)
            2 -> context.getString(R.string.text_language_en)
            else -> context.getString(R.string.text_language_en)
        }
    }

    /**
     * 获取已选择的语言设置
     *
     * @param context
     * @return
     */
    fun getSetLocale(context: Context): Locale {

        return when (getSelectLanguage()) {
            0 -> getSystemLocale(context)
            1 -> Locale.CHINA
            2 -> Locale.ENGLISH
            else -> Locale.ENGLISH
        }
    }

    fun saveSelectLanguage(context: Context, select: Int) {
        saveLanguage(select)
        updateApplicationContext(context)
    }

    fun updateContext(context: Context): Context {
        return updateResources(context, getSetLocale(context))
    }

    private fun updateResources(context: Context, locale: Locale): Context {
        Locale.setDefault(locale)

        val res = context.resources
        val dm = res.displayMetrics
        val config = res.configuration
        // 更新configuration，防止返回的context是更新config前的状态
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setAppLocale(config, locale)
            // ApplicationContext貌似没办法在其他地方更新configuration
            res.updateConfiguration(config, dm)
            context.createConfigurationContext(config)
        } else {
            setAppLocaleLegacy(config, locale)
            res.updateConfiguration(config, dm)
            context
        }
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
        config.locales = localeList
    }

    /**
     * 设置语言类型
     */
    fun updateApplicationContext(context: Context) : Context{
        return updateContext(context.applicationContext)
    }

    private fun getSystemLocaleLegacy(): Locale {
        return Locale.getDefault()
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun getSystemLocale(): Locale {
        return LocaleList.getDefault().get(0)
    }

    fun cacheSystemLocale(context: Context) {
        val locale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getSystemLocale()
        } else {
            getSystemLocaleLegacy()
        }
        Log.d(TAG, locale.language)
        setSystemCurrentLocal(locale)
    }

    fun onConfigurationChanged(context: Context) {
        cacheSystemLocale(context)
        updateContext(context)
        updateApplicationContext(context)
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

    /**
     * 跳转主页
     *
     * @param context
     */
    fun toRestartLauncherActivity(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
        // 杀掉进程，如果是跨进程则杀掉当前进程
//        android.os.Process.killProcess(android.os.Process.myPid())
//        System.exit(0);
    }
}
