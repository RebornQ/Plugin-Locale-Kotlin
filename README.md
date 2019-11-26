# MultiLanguagesDemo-Kotlin
An android demo with kotlin for changing multi-language. Thanks [@MichaelJokAr](https://github.com/MichaelJokAr).

一个Kotlin版多语言切换的Android demo。感谢[@MichaelJokAr](https://github.com/MichaelJokAr)的教程——[Android国际化(多语言)实现，支持8.0](https://blog.csdn.net/a1018875550/article/details/79845949)。

## 多语言实现中遇到的问题的解决方法
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

