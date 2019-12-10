package com.mallotec.reb.localeplugin.utils

import android.content.Context
import android.content.Intent
import com.mallotec.reb.localeplugin.LocaleConstant
import com.mallotec.reb.localeplugin.R

/**
 * Created by reborn on 2019-12-07.
 */
object ActivityUtil {

    interface OnUpdateInterfaceListener {
        fun updateInterface(context: Context, intent: Intent?)
    }

    private var onUpdateInterfaceListener: OnUpdateInterfaceListener? = null

    fun setOnUpdateInterfaceListener(onUpdateInterfaceListener: OnUpdateInterfaceListener) {
        this.onUpdateInterfaceListener = onUpdateInterfaceListener
    }

    // 默认方式是 recreate()
    private var updateWay = LocaleConstant.RECREATE_CURRENT_ACTIVITY

    // 若调用时参数为空，则默认方式， recreate()
    fun init(updateWay: Int = LocaleConstant.RECREATE_CURRENT_ACTIVITY) {
        this.updateWay = updateWay
    }

    fun getUpdateWay(): Int {
        return updateWay
    }

    /**
     * 跳转主页
     *
     * @param context
     * @param intent
     */
    fun openWithClearTask(context: Context, intent: Intent?) {
        intent!!.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
        // 杀掉进程，如果是跨进程则杀掉当前进程
//        android.os.Process.killProcess(android.os.Process.myPid())
//        System.exit(0);
    }

    /**
     * 重新加载Activity
     *
     * @param context
     */
    fun recreateActivity(context: Context) {
        // 使用广播也可以实现不重启到 LauncherActivity 只需 recreate() 即可刷新 Resources
        val intent = Intent(LocaleConstant.ACTION_RECREATE_ACTIVITY)
        context.sendBroadcast(intent) // 发送广播
    }

    fun updateInterface(context: Context, intent: Intent?) {
        // if(xx != null) ... else ...
        onUpdateInterfaceListener?.updateInterface(context, intent) ?: throw IllegalArgumentException(context.getString(R.string.plugin_locale_listener_not_initialized))
    }
}