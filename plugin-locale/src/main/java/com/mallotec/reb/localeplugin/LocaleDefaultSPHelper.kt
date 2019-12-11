/*
 * Copyright 2018 Bakumon. https://github.com/Bakumon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.mallotec.reb.localeplugin

import android.content.Context
import com.mallotec.reb.localeplugin.utils.getSPValue
import com.mallotec.reb.localeplugin.utils.saveSPValue

/**
 * 默认 Preference
 * @author Bakumon https://bakumon.me
 */
class LocaleDefaultSPHelper(private val context: Context) {

    fun getContext() : Context{
        return context
    }

    companion object{
        private lateinit var instance : LocaleDefaultSPHelper

        private fun getInstance(): LocaleDefaultSPHelper {
            check(::instance.isInitialized) { "LocaleDefaultSPHelper should be initialized first"  }
            return instance
        }

        /**
         * 语言
         */
        var language: String
            get() = getInstance().getContext().getSPValue(
                LocaleConstant.LANGUAGE,
                "0"
            )
            set(value) = getInstance().getContext().saveSPValue(
                LocaleConstant.LANGUAGE,
                value
            )

        fun init(context: Context): LocaleDefaultSPHelper{
            check(!::instance.isInitialized) { "LocaleDefaultSPHelper is already initialized" }
            instance = LocaleDefaultSPHelper(context)
            return instance
        }
    }
}