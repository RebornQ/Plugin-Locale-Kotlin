package pw.gike.multilanguagesdemo.utils


import android.content.Context
import android.content.SharedPreferences

import java.util.HashMap

class SharePrefUtils {

    private var pref: SharedPreferences? = null

    private val map = HashMap<String, Any>()

    val all: Map<String, *>
        get() = pref!!.all

    private fun setPref(pref: SharedPreferences) {
        map.clear()
        this.pref = pref
    }

    fun getString(key: String): String? {
        return pref!!.getString(key, "")
    }

    fun getBoolean(key: String): Boolean {
        return pref!!.getBoolean(key, false)
    }

    fun getInt(key: String): Int {
        return pref!!.getInt(key, 0)
    }

    fun clear() {
        pref!!.edit().clear().apply()
    }

    fun dataPrepare(key: String, value: Any): SharePrefUtils {
        //            map.clear();
        map[key] = value
        return this
    }

    fun putData(): SharePrefUtils {
        // 获取所有键值对对象的集合
        for ((key, value) in map) {
            // 根据键值对对象获取键和值
            putObject(key, value)
        }
        return this
    }

    private fun putObject(key: String, value: Any) {
        when (value) {
            is Boolean -> pref!!.edit().putBoolean(key, value).apply()
            is Float -> pref!!.edit().putFloat(key, value).apply()
            is Int -> pref!!.edit().putInt(key, value).apply()
            is Long -> pref!!.edit().putLong(key, value).apply()
            is String -> pref!!.edit().putString(key, value).apply()
        }
    }

    class Builder {

        private var context: Context? = null

        private var pref: SharedPreferences? = null

        fun setContext(context: Context): Builder {
            this.context = context
            return this
        }

        fun setPref(prefName: String): Builder {
            if (pref == null && context != null) {
                pref = context!!.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            }
            return this
        }

        fun create(): SharePrefUtils {

            val sharePrefUtils = SharePrefUtils()

            if (pref != null) {
                sharePrefUtils.setPref(pref!!)
            }
            return sharePrefUtils
        }
    }

}
