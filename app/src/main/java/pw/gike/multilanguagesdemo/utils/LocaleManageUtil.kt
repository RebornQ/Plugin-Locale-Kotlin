package pw.gike.multilanguagesdemo.utils

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import android.util.Log
import pw.gike.multilanguagesdemo.Constant
import pw.gike.multilanguagesdemo.R
import pw.gike.multilanguagesdemo.activity.MainActivity
import java.util.*

object LocaleManageUtil {

    private val TAG = "LocaleManageUtil"

    private var sharePrefUtils: SharePrefUtils? = null

    // 静态属性，修复识别系统语言为英语的问题
    private var systemCurrentLocal = Locale.ENGLISH

    fun setSharePref(context: Context){
        sharePrefUtils = SharePrefUtils
            .Builder()
            .setContext(context)
            .setPref(Constant.LANGUAGE_SP)
            .create()
    }

    fun saveLanguage(select: Int) {
        sharePrefUtils!!
            .dataPrepare(Constant.TAG_LANGUAGE, select)
            .putData()
    }

    fun getSelectLanguage(): Int {
        return sharePrefUtils!!.getInt(Constant.TAG_LANGUAGE)
    }

    fun setSystemCurrentLocal(local: Locale) {
        systemCurrentLocal = local
    }


    /**
     * 获取系统的locale
     *
     * @return Locale对象
     */
    fun getSystemLocale(context: Context): Locale {
        return systemCurrentLocal
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
     * 获取选择的语言设置
     *
     * @param context
     * @return
     */
    fun getSetLanguageLocale(context: Context): Locale {

        return when (getSelectLanguage()) {
            0 -> getSystemLocale(context)
            1 -> Locale.CHINA
            2 -> Locale.ENGLISH
            else -> Locale.ENGLISH
        }
    }

    fun saveSelectLanguage(context: Context, select: Int) {
        saveLanguage(select)
        setApplicationLanguage(context)
    }

    fun setLocal(context: Context): Context {
        return updateResources(context, getSetLanguageLocale(context))
    }

    private fun updateResources(context: Context, locale: Locale): Context {
        var context = context
        Locale.setDefault(locale)

        val res = context.resources
        val config = Configuration(res.configuration)
        if (Build.VERSION.SDK_INT >= 19) {
            config.setLocale(locale)
            context = context.createConfigurationContext(config)
        } else {
            config.locale = locale
            res.updateConfiguration(config, res.displayMetrics)
        }
        return context
    }

    /**
     * 设置语言类型
     */
    fun setApplicationLanguage(context: Context) {
        val resources = context.applicationContext.resources
        val dm = resources.displayMetrics
        val config = resources.configuration
        val locale = getSetLanguageLocale(context)
        config.locale = locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val localeList = LocaleList(locale)
            LocaleList.setDefault(localeList)
            config.locales = localeList
            context.applicationContext.createConfigurationContext(config)
            Locale.setDefault(locale)
        }
        resources.updateConfiguration(config, dm)
    }

    fun saveSystemCurrentLanguage(context: Context) {
        val locale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList.getDefault().get(0)
        } else {
            Locale.getDefault()
        }
        Log.d(TAG, locale.language)
        setSystemCurrentLocal(locale)
    }

    fun onConfigurationChanged(context: Context) {
        saveSystemCurrentLanguage(context)
        setLocal(context)
        setApplicationLanguage(context)
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
