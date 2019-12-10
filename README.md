# Plugin-Locale-Kotlin
[![Releases](https://img.shields.io/badge/android-5.0%2B-brightgreen.svg)](https://bintray.com/rebornq/maven/plugin-locale) [![Releases](https://img.shields.io/github/release/RebornQ/Plugin-Locale-Kotlin.svg)](https://github.com/RebornQ/Plugin-Locale-Kotlin) [ ![Download](https://api.bintray.com/packages/rebornq/maven/plugin-locale/images/download.svg) ](https://bintray.com/rebornq/maven/plugin-locale/_latestVersion)

An android plugin with kotlin for changing multi-language.

Only support `English/Simplified Chinese/Traditional Chinese`, but you can submit issues or contact to me if you need other languages.

一个用 Kotlin 写的多语言切换的 Android 插件。

目前只支持`英文/简中/繁中`三种语言，需要添加其他的语言可以提交 issue 或者直接联系我。

## 背景
项目历经9个月的演化，终于从一开始为宝可梦的那样记账研究的语言切换而写的`Demo`进化成第三方库。

这是我的第一个开源第三方库，说起来还挺激动的（嗯...失眠了...）～

## 使用文档
### 引入依赖
#### plugin-locale
**（可选）项目的 build.gradle 中加入：**
> *当 JCenter 无法链接的时候可以尝试使用*

```java
allprojects {
    repositories {
        // ...
        maven { url "https://dl.bintray.com/rebornq/maven/" }
    }
}
```

**app.gradle 中加入：**
```java
implementation 'com.mallotec.reb:plugin-locale:{last-version}'
```
> **注意：`{last-version}`要替换为最新版本号**，最新版本链接：[https://bintray.com/rebornq/maven/plugin-locale/_latestVersion](https://bintray.com/rebornq/maven/plugin-locale/_latestVersion)

### 添加混淆规则(可选)

```shell
# LocalePlugin 混淆规则
-keep class com.mallotec.reb.localeplugin.** { *; }
-dontwarn com.mallotec.reb.localeplugin.**
```
    
### 只需三步即可食用
1. 自定义`Application`继承`BaseApplication`
2. 所有`Activity`继承`BaseAppCompatActivity`，第三方`Activity`库的适配请查看下方适配指南
3. 一句代码调用切换语言：

    ```java
    // 应用切换的语言
    LocaleManageUtil
        .language(which.toString())
        .apply(this)
    ```
   **注意：这里的`this`必须是当前`Activity`的`Context`；`which`是所选的语言标记，详情请看下方注意事项的对应关系**

### 注意事项
`SharePreferences`中`language`字段的`value`与 App 语言的对应关系：

```java
"0" -> 跟随系统
"1" -> English
"2" -> 简体中文
"3" -> 繁体中文
```

### Demo
效果图：

![MultiLanguageDemo-NoRestartToLaunche](/media/MultiLanguageDemo-NoRestartToLauncher.gif)

更多请查看本项目的 [Demo](https://github.com/RebornQ/Plugin-Locale-Kotlin/tree/master/demo)

### Activity 库适配指南
我们知道，除了官方的 [AppCompatActivity](https://developer.android.com/jetpack/androidx/releases/appcompat) 外，还有一些优秀开发者写的`Activity`库，比如`drakeet`的 [AbsAboutActivity](https://github.com/PureWriter/about-page)。

这时候我们要继承这些`Activity`做自己的处理的时候，继承了其他`Activity`就没法继承`BaseAppCompatActivity`了呀！那怎么办呢？

别急，下面是适配指南～

举例适配`drakeet`的`AbsAboutActivity`，加入以下内容：
```java
abstract class TestActivity : AbsAboutActivity() {

    private var recreateActivityReceiver: RecreateActivityReceiver? = null

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleManageUtil.updateContext(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 使用广播实现不重启到 LauncherActivity 只需 recreate() 即可刷新 Resources
        recreateActivityReceiver = RecreateActivityReceiver(this)
        registerReceiver(recreateActivityReceiver, recreateActivityReceiver!!.getDefaultIntentFilter())
    }

    // 防止 Locale 被一个新的 Configuration 对象覆盖掉（AppCompat库1.1.0-alpha03以上版本）
    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        if (overrideConfiguration != null) {
            overrideConfiguration?.setLocale(LocaleManageUtil.getSetLocale())
        }
        super.applyOverrideConfiguration(overrideConfiguration)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(recreateActivityReceiver)
    } 
}
```

> **对于切换语言后一定不在返回栈中的`Activity`，不必做适配。**这是因为`App`内所有界面共享同一个`Locale`，切换后`Locale`变了，新启动的`Activity`用上新的`Context`已经是切换后的`Locale`。所以其实返回栈中的`Locale`也变了，只是界面资源没有刷新。

## 写在最后
欢迎大家 Star、Fork 和提 Issue 提 PR 呀～

## Thanks
- Thanks [@MichaelJokAr](https://github.com/MichaelJokAr). 感谢[@MichaelJokAr](https://github.com/MichaelJokAr)的教程——[Android国际化(多语言)实现，支持8.0](https://blog.csdn.net/a1018875550/article/details/79845949)。

- Thanks [@Bakumon](https://github.com/Bakumon) 感谢宝可梦的指点
