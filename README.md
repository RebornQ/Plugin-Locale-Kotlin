# Plugin-Locale-Kotlin
[![Android Version](https://img.shields.io/badge/android-5.0%2B-brightgreen.svg)](https://bintray.com/rebornq/maven/plugin-locale) [![Language](https://img.shields.io/badge/language-kotlin-orange.svg)](https://github.com/RebornQ/Plugin-Locale-Kotlin) [![Releases](https://img.shields.io/github/release/RebornQ/Plugin-Locale-Kotlin.svg)](https://github.com/RebornQ/Plugin-Locale-Kotlin) [![Download](https://api.bintray.com/packages/rebornq/maven/plugin-locale/images/download.svg)](https://bintray.com/rebornq/maven/plugin-locale/_latestVersion) [![license](https://img.shields.io/badge/license-Apache2-yellow.svg)](https://github.com/RebornQ/Plugin-Locale-Kotlin/blob/master/LICENSE)

An android library with kotlin for changing multi-language.

Now we support any language but need you to define the connection between language's key and value !

一个用 Kotlin 写的多语言切换的 Android 第三方库。

现在我们支持任何一种语言啦！但是需要你自己定义你要支持的语言列表（Key 与 Value 间的关系），详情请看👉 Wiki （🚪[传送门](https://github.com/RebornQ/Plugin-Locale-Kotlin/wiki)）。

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

### 只需三步即可食用
1. 在 Application 中初始化

    ```java
    LocalePlugin.init(this)
    ```

1. 定义好支持的语言列表对应关系，详情请看👉 Wiki （🚪[传送门](https://github.com/RebornQ/Plugin-Locale-Kotlin/wiki)），如：
    
    ```java
    private fun getLocale(which : String): Locale {
        return when (which) {
            "0" -> Locale.ROOT  // 跟随系统
            "1" -> Locale.ENGLISH
            "2" -> Locale.SIMPLIFIED_CHINESE
            "3" -> Locale.TRADITIONAL_CHINESE
            else -> Locale.SIMPLIFIED_CHINESE
        }
    }
    ```

3. 一句代码调用切换语言：

    ```java
    // 应用切换的语言
    LocaleHelper.getInstance()
        .language(getLocale(which.toString()))
        .apply(this)
    ```
    
   **注意：这里的`this`必须是当前`Activity`的`Context`；`which`是所选的语言标记，详情请看下方注意事项的对应关系**


### Demo

<details>
<summary>效果图（默认第二种刷新界面方式）</summary>

![MultiLanguageDemo-NoRestartToLaunche](/media/MultiLanguageDemo-NoRestartToLauncher.gif)

</details>

更多请查看本项目的 [Demo](https://github.com/RebornQ/Plugin-Locale-Kotlin/tree/master/demo)

## 注意事项、常见问题和更多用法
注意事项、常见问题以及更多用法，请转战 **Wiki** ：🚪[传送门](https://github.com/RebornQ/Plugin-Locale-Kotlin/wiki)

## 写在最后
欢迎大家 Star、Fork 和提 Issue 提 PR 呀～

## Thanks
以下不分排名先后

- Thanks [@MichaelJokAr](https://github.com/MichaelJokAr). 感谢 [@MichaelJokAr](https://github.com/MichaelJokAr) 的教程——[Android国际化(多语言)实现，支持8.0](https://blog.csdn.net/a1018875550/article/details/79845949)
- Thanks [@Bakumon](https://github.com/Bakumon). 感谢 [@宝可梦](https://github.com/Bakumon) 的指点
- Thanks [@JessYan](https://github.com/JessYanCoding). 感谢 [@JessYan](https://github.com/JessYanCoding) 的教程——[我一行代码都不写实现Toolbar!你却还在封装BaseActivity?](https://juejin.im/post/590f09ec128fe100584ee6b0)
- Thanks [@Yaroslav Berezanskyi](https://proandroiddev.com/@yaroslavberezanskyi). 感谢 [@Yaroslav Berezanskyi](https://proandroiddev.com/@yaroslavberezanskyi) 的教程——[How to change the language on Android at runtime and don’t go mad](https://proandroiddev.com/change-language-programmatically-at-runtime-on-android-5e6bc15c758)
