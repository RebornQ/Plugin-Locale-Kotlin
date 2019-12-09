package com.mallotec.reb.languageplugin.utils

import android.content.Context
import android.content.Intent
import com.mallotec.reb.languageplugin.LocaleConstant

/**
 * Created by reborn on 2019-12-07.
 */
object ActivityUtil {

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
    fun recreateActivity(context: Context){
        // 使用广播也可以实现不重启到 LauncherActivity 只需 recreate() 即可刷新 Resources
        val intent = Intent(LocaleConstant.ACTION_RECREATE_ACTIVITY)
        context.sendBroadcast(intent) // 发送广播
    }
}