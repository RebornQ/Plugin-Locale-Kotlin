package com.mallotec.reb.languageplugin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by reborn on 2019-12-07.
 */
object ActivityManager {

    /**
     * 跳转主页
     *
     * @param context
     * @param targetActivity
     */
    fun toRestartLauncherActivity(context: Context, targetActivity: AppCompatActivity) {
        val intent = Intent(context, targetActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
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
        val intent = Intent(Constant.ACTION_RECREATE_ACTIVITY)
        context.sendBroadcast(intent) // 发送广播
    }
}