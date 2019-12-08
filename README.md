# Plugin-Language-Kotlin
[ ![Download](https://api.bintray.com/packages/rebornq/maven/plugin-language/images/download.svg) ](https://bintray.com/rebornq/maven/plugin-language/_latestVersion)

An android plugin with kotlin for changing multi-language.

一个 Kotlin 版多语言切换的 Android 插件。

## 背景
项目历经9个月的演化，终于从一开始为宝可梦的那样记账研究的语言切换而写的`Demo`进化成第三方库。

这是我的第一个开源第三方库，说起来还挺激动的（嗯...失眠了...）～

## 使用文档
### 引入依赖
**（可选）项目的 build.gradle 中加入：**
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
implementation 'com.mallotec.reb:plugin-language:{last-version}'
```
> **注意：`{last-version}`要替换为最新版本号**，最新版本链接：[https://bintray.com/rebornq/maven/plugin-language/_latestVersion](https://bintray.com/rebornq/maven/plugin-language/_latestVersion)

由于本插件还用了`androidx.preference`，因此需要引入`Preference`相关依赖
```java
// preference
implementation 'androidx.preference:preference:1.1.0'
```

### 只需四步即可食用
1. 自定义`Application`继承`BaseApplication`
2. 所有`Activity`继承`BaseAppCompactActivity`
3. 添加混淆规则

    ```shell
    # LanguagePlugin 混淆规则
    -keep class com.mallotec.reb.languageplugin.** { *; }
    -dontwarn com.mallotec.reb.languageplugin.**
    ```
4. 一句代码调用切换语言：

    ```java
    // 应用切换的语言
    LocaleManageUtil.applyLanguage(this, which.toString())
    ```
   **注意：这里的`this`必须是`Activity`的`Context`，`which`是所选的语言标记**

### Demo
效果图：

![MultiLanguageDemo-NoRestartToLaunche](/media/MultiLanguageDemo-NoRestartToLauncher.gif)

更多请查看本项目的 [Demo](https://github.com/RebornQ/Plugin-Language-Kotlin/tree/master/demo)

## 写在最后
欢迎大家 Star、Fork 和提 Issue 提 PR 呀～

## Thanks
- Thanks [@MichaelJokAr](https://github.com/MichaelJokAr). 感谢[@MichaelJokAr](https://github.com/MichaelJokAr)的教程——[Android国际化(多语言)实现，支持8.0](https://blog.csdn.net/a1018875550/article/details/79845949)。

- Thanks [@Bakumon](https://github.com/Bakumon) 感谢宝可梦的指点