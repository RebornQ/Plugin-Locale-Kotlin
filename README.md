# MultiLanguagesDemo-Kotlin
An android demo with kotlin for changing multi-language. Thanks [@MichaelJokAr](https://github.com/MichaelJokAr).

一个Kotlin版多语言切换的Android demo。感谢[@MichaelJokAr](https://github.com/MichaelJokAr)的教程——[Android国际化(多语言)实现，支持8.0](https://blog.csdn.net/a1018875550/article/details/79845949)。

## 多语言实现中常见问题的解决方法
### AndroidX AppCompat 库 1.1.0-alpha03 以上版本导致的 Locale 被一个新的 Configuration 对象覆盖掉
仅写出解决方法，本demo经测试无法复现问题
> git commit 链接：[https://github.com/RebornQ/MultiLanguagesDemo-Kotlin/commit/64bc52e759e74efdb1d4125ac186ce07cdf0e840](https://github.com/RebornQ/MultiLanguagesDemo-Kotlin/commit/64bc52e759e74efdb1d4125ac186ce07cdf0e840)

在`BaseActivity`中加入以下代码即可：
```java
// 防止 Locale 被一个新的 Configuration 对象覆盖掉（AndroidX AppCompat 库 1.1.0-alpha03 以上版本）
override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
    if (overrideConfiguration != null) {
        overrideConfiguration?.setLocale(LocaleManageUtil.getSetLocale(this))
    }
    super.applyOverrideConfiguration(overrideConfiguration)
}
```

### 关于无需重启 App 刷新语言配置的方法
设置语言后是需要重新启动一遍 Activity 配置才能生效的（切换使用新的 Configuration 重新渲染对应语言环境），这里整理了几种方法。
#### 方法一：重启 Activity 到启动页
这种是最经典最容易实现的方法：清空栈内所有 Activity 并启动到 LauncherActivity
##### 效果图
先上效果图：
![MultiLanguageDemo-RestartToLaunche](/media/MultiLanguageDemo-RestartToLauncher.gif)

##### 实现
```java
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
```

#### 方法二：直接重启当前 Activity 并通知其他 Activity 也重新渲染
核心方法是`Activity`的`recreate()`方法，可以重新渲染对应语言环境的界面。
##### 效果图
先上效果图：
![MultiLanguageDemo-NoRestartToLaunche](/media/MultiLanguageDemo-NoRestartToLauncher.gif)
##### 1. 原生广播实现

`Constant.kt`中：
```java
const val ACTION_RECREATE_ACTIVITY = "android.action.RECREATE_ACTIVITY"
```

因为要在`BroadcastReceiver`中调用`Activity`的方法，所以要传入`Activity`的引用，这里我使用了回调的方法：

```java
interface OnGetActivityListener {
        fun getActivity() : BaseActivity
}
```

然后`BaseActivity`实现这个接口，实现`BroadcastReceiver`，`onReceive()`方法中调用`recreate()`，再在`onCreate()`中动态注册`BroadcastReceiver`：
```java
abstract class BaseActivity : AppCompatActivity() , OnGetActivityListener {

    class RecreateActivityBroadcastReceiver : BroadcastReceiver() {
        private var myOnGetActivityListener : OnGetActivityListener? = null

        override fun onReceive(context: Context?, intent: Intent?) {
            myOnGetActivityListener?.getActivity()?.recreate()
        }

        public fun setOnGetActivityListener(onGetActivityListener: OnGetActivityListener) {
            myOnGetActivityListener = onGetActivityListener
        }
    }

    private var intentFilter: IntentFilter? = null
    private var recreateActivityBroadcastReceiver: RecreateActivityBroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 使用广播也可以实现不重启到 LauncherActivity 只需 recreate() 即可刷新 Resources
        intentFilter = IntentFilter()
        intentFilter!!.addAction(Constant.ACTION_RECREATE_ACTIVITY)
        recreateActivityBroadcastReceiver = RecreateActivityBroadcastReceiver()
        recreateActivityBroadcastReceiver!!.setOnGetActivityListener(this)
        registerReceiver(recreateActivityBroadcastReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(recreateActivityBroadcastReceiver)
    }

    override fun getActivity(): BaseActivity {
        return this
    }
}
```
**注意：不要忘记在`onDestroy()`中反注册`BroadcastReceiver`！！！**

最后，切换语言后发送广播（`SettingActivity`）：
```java
// 使用广播也可以实现不重启到 LauncherActivity 只需 recreate() 即可刷新 Resources
val intent = Intent(Constant.ACTION_RECREATE_ACTIVITY)
sendBroadcast(intent) // 发送广播
```

##### 2. EventBus实现
EventBus 的原理在这里不展开说，这里只说怎么实现。

`app.gradle`中：
```java
implementation 'org.greenrobot:eventbus:3.1.1'
```

`Constant.kt`中：
```java
const val EVENT_RECREATE_ACTIVITY = "event_recreate_activity"
```

`BaseActivity`中，`onCreate()`注册和`onDestroy()`反注册 EventBus，并实现`onEvent()`刷新界面事件：
```java
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun onEvent(str: String?) {
        when (str) {
            Constant.EVENT_REFRESH_LANGUAGE -> {
                recreate() // 刷新界面
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
```

最后，切换语言后发送事件执行通知（`SettingActivity`）：
```java
EventBus.getDefault().post(Constant.EVENT_REFRESH_LANGUAGE)
```

**务必记得添加混淆规则！！！**
`proguard-rules.pro`:
```shell
# EventBus 3.0 混淆规则：http://greenrobot.org/eventbus/documentation/proguard/
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Only required if you use AsyncExecutor
#-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
#    <init>(java.lang.Throwable);
#}
```