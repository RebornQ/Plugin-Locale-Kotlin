package com.mallotec.reb.localeplugin.utils

import android.content.Context
import android.content.SharedPreferences
import com.mallotec.reb.localeplugin.anno.PreferencesMode
import java.util.*

/**
 * SharedPreferences 工具类
 *
 * @author Reborn https://reb.mallotec.com
 */
object SharedPrefUtils {

    lateinit var pref: SharedPreferences
        private set

    private val map = HashMap<String, Any>()

    val all: MutableMap<String, *>?
        get() = pref.all

    inline fun <reified T> getValue(key: String, default: T? = null): T {
        with(pref) {
            return when (T::class) {
                Int::class -> getInt(key, if (default is Int) default else 0)
                Long::class -> getLong(key, if (default is Long) default else 0L)
                Float::class -> getFloat(key, if (default is Float) default else 0.0f)
                String::class -> getString(key, if (default is String) default else "")
                Boolean::class -> getBoolean(key, if (default is Boolean) default else false)
                else -> throw IllegalArgumentException("SharedPreferences 类型错误")
            } as T
        }
    }

    fun clear() {
        pref.edit().clear().apply()
    }

    fun remove(key: String) {
        pref.edit().remove(key).apply()
    }

    fun dataPrepare(key: String, value: Any): SharedPrefUtils {
        return this.apply {
            // map.clear();
            map[key] = value
        }
    }

    fun apply() {
        // 获取所有键值对对象的集合
        for ((key, value) in map) {
            // 根据键值对对象获取键和值
            putObject(key, value)
        }
        map.clear()
    }

    private fun putObject(key: String, value: Any) {
        with(pref.edit()) {
            when (value) {
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is String -> putString(key, value)
                else -> throw IllegalArgumentException("SharedPreferences 类型错误")
            }.apply()
        }
    }

    object Builder {

        private var builderPrefs: SharedPreferences? = null

        fun build(): SharedPrefUtils {
            return SharedPrefUtils.apply {
                if (!::pref.isInitialized) {
                    builderPrefs?.let {
                        map.clear()
                        pref = it
                    }
                }
            }
        }

        fun setPref(
            context: Context?,
            prefName: String? = context?.let { getDefaultSharedPreferencesName(it) },
            @PreferencesMode mode: Int = getDefaultSharedPreferencesMode()
        ): Builder {
            return this.apply {
                if (builderPrefs == null) {
                    builderPrefs = context?.getSharedPreferences(prefName, mode)
                }
            }
        }

        fun setDefaultPref(context: Context?): Builder {
            return this.apply { setPref(context) }
        }
    }

    /**
     * Returns the name used for storing default shared preferences.
     *
     * @see .getDefaultSharedPreferences
     */
    private fun getDefaultSharedPreferencesName(context: Context): String? {
        return context.packageName + "_preferences"
    }

    private fun getDefaultSharedPreferencesMode(): Int {
        return Context.MODE_PRIVATE
    }
}

/**
 * 为 Context 添加扩展（Application Context 也可使用）
 */
fun Context.prepareSPData(key: String, value: String) =
    SharedPrefUtils.Builder.setDefaultPref(context = this).build().dataPrepare(
        key = key,
        value = value
    )

fun Context.saveSPValue(key: String, value: String) =
    prepareSPData(key = key, value = value).apply()

inline fun <reified T> Context.getSPValue(key: String, default: T? = null) =
    SharedPrefUtils.Builder.setDefaultPref(context = this).build().getValue(
        key = key,
        default = default
    )

fun Context.allSP() = SharedPrefUtils.Builder.setDefaultPref(context = this).build().all

fun Context.clearSP() {
    SharedPrefUtils.Builder.setDefaultPref(context = this).build().clear()
}

fun Context.removeSP(key: String) {
    SharedPrefUtils.Builder.setDefaultPref(context = this).build().remove(key = key)
}
