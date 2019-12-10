package com.mallotec.reb.localeplugin

import com.mallotec.reb.localeplugin.utils.ActivityUtil

/**
 * Created by reborn on 2019-12-10.
 */
object LocalePlugin {

    fun setUpdateInterfaceWay(way: Int = LocaleConstant.RECREATE_CURRENT_ACTIVITY): LocalePlugin {
        return this.apply { ActivityUtil.init(way) }
    }

    fun init() {
        // TODO 用来初始化其他东西
    }
}