# Plugin-Locale-Kotlin
[![Android Version](https://img.shields.io/badge/android-5.0%2B-brightgreen.svg)](https://bintray.com/rebornq/maven/plugin-locale) [![Language](https://img.shields.io/badge/language-kotlin-orange.svg)](https://github.com/RebornQ/Plugin-Locale-Kotlin) [![Releases](https://img.shields.io/github/release/RebornQ/Plugin-Locale-Kotlin.svg)](https://github.com/RebornQ/Plugin-Locale-Kotlin) [![Download](https://api.bintray.com/packages/rebornq/maven/plugin-locale/images/download.svg)](https://bintray.com/rebornq/maven/plugin-locale/_latestVersion) [![license](https://img.shields.io/badge/license-Apache2-yellow.svg)](https://github.com/RebornQ/Plugin-Locale-Kotlin/blob/master/LICENSE)

An android library with kotlin for changing multi-language.

Only support `English/Simplified Chinese/Traditional Chinese`, but you can submit issues or contact to me if you need other languages.

一个用 Kotlin 写的多语言切换的 Android 第三方库。

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

<details>
<summary>混淆规则</summary>

### 添加混淆规则(可选)

```shell
# LocalePlugin 混淆规则
-keep class com.mallotec.reb.localeplugin.** { *; }
-dontwarn com.mallotec.reb.localeplugin.**
```

</details>

### 只需两步即可食用
1. 在 Application 中初始化

    ```java
    LocalePlugin.init(this)
    ```
    或
    ```java
    LocalePlugin.init(this, { 刷新界面的方式 })
    ```
    > 其中`{ 刷新界面的方式 }`有三种：
    > 1. `LocaleConstant.RESTART_TO_LAUNCHER_ACTIVITY`: 清空栈中所有`Activity`并重启到`LauncherActivity`
    > 2. `LocaleConstant.RECREATE_CURRENT_ACTIVITY`: 重新创建当前`Activity`， **默认是这种方式，可不填写**
    > 3. `LocaleConstant.CUSTOM_WAY_TO_UPDATE_INTERFACE`: 自定义刷新界面， **如果选这种方式的朋友请务必查看下方👉[更多用法](https://github.com/RebornQ/Plugin-Locale-Kotlin#%E6%9B%B4%E5%A4%9A%E7%94%A8%E6%B3%95)或👉[Wiki](https://github.com/RebornQ/Plugin-Locale-Kotlin/wiki/%E8%87%AA%E5%AE%9A%E4%B9%89%E5%88%87%E6%8D%A2%E8%AF%AD%E8%A8%80%E5%90%8E%E5%88%B7%E6%96%B0%E7%95%8C%E9%9D%A2%E7%9A%84%E6%96%B9%E5%BC%8F)**
2. 一句代码调用切换语言：

    ```java
    // 应用切换的语言
    LocaleHelper.getInstance()
        .language(which.toString())
        .apply(this)
    ```
    
    若`{ 刷新界面的方式 }`选择了第一种`LocaleConstant.RESTART_TO_LAUNCHER_ACTIVITY`，请使用下面的方式：
    
    ```java
    // 应用切换的语言
    val intent = Intent(this, TargetActivity::class.java)
    LocaleHelper.getInstance()
        .language(which.toString())
        .apply(this, intent)
    ```
    若`{ 刷新界面的方式 }`选择了第三种`LocaleConstant.CUSTOM_WAY_TO_UPDATE_INTERFACE`，请使用下面的方式：
    >  上面有写，**查看下方👉[更多用法](https://github.com/RebornQ/Plugin-Locale-Kotlin#%E6%9B%B4%E5%A4%9A%E7%94%A8%E6%B3%95)或👉[Wiki](https://github.com/RebornQ/Plugin-Locale-Kotlin/wiki/%E8%87%AA%E5%AE%9A%E4%B9%89%E5%88%87%E6%8D%A2%E8%AF%AD%E8%A8%80%E5%90%8E%E5%88%B7%E6%96%B0%E7%95%8C%E9%9D%A2%E7%9A%84%E6%96%B9%E5%BC%8F)**
    
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

<details>
<summary>效果图（默认第二种刷新界面方式）</summary>

![MultiLanguageDemo-NoRestartToLaunche](/media/MultiLanguageDemo-NoRestartToLauncher.gif)

</details>

更多请查看本项目的 [Demo](https://github.com/RebornQ/Plugin-Locale-Kotlin/tree/master/demo)

## 更多用法
更多用法请转战 **Wiki** ：🚪[传送门](https://github.com/RebornQ/Plugin-Locale-Kotlin/wiki)

<details>
<summary>~~第三方 Activity 库适配指南（V1.0.9后已不再需要适配）~~</summary>

以下内容并非不再需要适配的原因，仅保留用作笔记
> **对于切换语言后一定不在返回栈中的`Activity`，不必做适配。** 这是因为`App`内所有界面共享同一个`Locale`，切换后`Locale`变了，新启动的`Activity`用上新的`Context`已经是切换后的`Locale`。所以其实返回栈中的`Locale`也变了，只是界面资源没有刷新。

</details>

<details>
<summary>自定义切换语言后刷新界面的方式</summary>

### 自定义切换语言后刷新界面的方式
首先，下面是几个初始化刷新界面方式的常量：
```java
LocaleConstant.RESTART_TO_LAUNCHER_ACTIVITY -> 重启到主页（非重启 App ）
LocaleConstant.RECREATE_CURRENT_ACTIVITY -> 重启已经打开的 Activity
LocaleConstant.CUSTOM_WAY_TO_UPDATE_INTERFACE -> 自己实现刷新界面的方式
```

插件默认初始化为`LocaleConstant.RECREATE_CURRENT_ACTIVITY`，若要自己实现，需要使用前先在`Application`初始化插件：
```java
LocalePlugin.init(this, LocaleConstant.CUSTOM_WAY_TO_UPDATE_INTERFACE)
```

初始化过后，在切换语言的界面实现`ActivityHelper.OnUpdateInterfaceListener`接口、设置监听器，然后在接口方法体内写自己想要实现的刷新界面逻辑，如：
```java
class SettingActivity : AppCompatActivity(), ActivityHelper.OnUpdateInterfaceListener {

    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        ...
        // 设置监听器
        ActivityHelper.getInstance().setOnUpdateInterfaceListener(this)
    }

    override fun updateInterface(context: Context, intent: Intent?) {
        // TODO: To write your way to update interface
        Toast.makeText(context, intent?.getStringExtra("Test"), Toast.LENGTH_LONG).show()
    }
}
```

然后在切换语言的时候调用：
```java
// 应用切换的语言
LocaleHelper.getInstance()
    .language(which.toString())
    .apply(this@SettingActivity, intent, ActivityUtil)
```

举例：
```java
val intent = Intent(this, MainActivity::class.java)
intent.putExtra("Test", getString(R.string.activity_custom_refresh_way_test))
LocaleHelper.getInstance()
    .language(which.toString())
    .apply(this@SettingActivity, intent, ActivityUtil)
```

</details>

## 常见问题
### 切换语言失效原因及解决方法

<details>
<summary>AndroidX AppCompat 库 1.1.0-alpha03 以上版本导致的 Locale 被一个新的 Configuration 对象覆盖掉</summary>

#### AndroidX AppCompat 库 1.1.0-alpha03 以上版本导致的 Locale 被一个新的 Configuration 对象覆盖掉
仅写出解决方法，本demo经测试无法复现问题

在`Activity`中加入以下代码即可：
```java
// 防止 Locale 被一个新的 Configuration 对象覆盖掉（AndroidX AppCompat 库 1.1.0-alpha03 以上版本）
override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
    overrideConfiguration?.setLocale(LocaleHelper.getInstance().getSetLocale())
    super.applyOverrideConfiguration(overrideConfiguration)
}
```

</details>

## 写在最后
欢迎大家 Star、Fork 和提 Issue 提 PR 呀～

## Thanks
以下不分排名先后

- Thanks [@MichaelJokAr](https://github.com/MichaelJokAr). 感谢 [@MichaelJokAr](https://github.com/MichaelJokAr) 的教程——[Android国际化(多语言)实现，支持8.0](https://blog.csdn.net/a1018875550/article/details/79845949)
- Thanks [@Bakumon](https://github.com/Bakumon). 感谢 [@宝可梦](https://github.com/Bakumon) 的指点
- Thanks [@JessYan](https://github.com/JessYanCoding). 感谢 [@JessYan](https://github.com/JessYanCoding) 的教程——[我一行代码都不写实现Toolbar!你却还在封装BaseActivity?](https://juejin.im/post/590f09ec128fe100584ee6b0)
