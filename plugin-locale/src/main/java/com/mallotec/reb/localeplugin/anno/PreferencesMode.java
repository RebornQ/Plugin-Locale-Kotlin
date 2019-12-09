package com.mallotec.reb.localeplugin.anno;

import android.annotation.SuppressLint;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.content.Context.MODE_MULTI_PROCESS;
import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.MODE_WORLD_READABLE;
import static android.content.Context.MODE_WORLD_WRITEABLE;

/**
 * 确保输入的 mode 不会是乱输入
 *
 *  @author : JXD
 *  @date : 2019/4/22 星期一
 */
@SuppressLint({"WorldReadableFiles", "WorldWriteableFiles"})
@IntDef(flag = true, value = {
        MODE_PRIVATE,
        MODE_WORLD_READABLE,
        MODE_WORLD_WRITEABLE,
        MODE_MULTI_PROCESS,
})
@Retention(RetentionPolicy.SOURCE)
public @interface PreferencesMode {}