# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

############################################

# 对于一些基本指令的添加

############################################
# 指定代码的压缩级别，值在0-7之间。一般设置5足矣
-optimizationpasses 5

# 打印混淆信息
-verbose

# 代码优化选项，不加该行会将没有用到的类删除，发布的是代码库这个选项需要
# 在做混淆之前最开始会默认对代码进行压缩，为了增加反编译的难度可以选择不压缩
-dontshrink

# 保留参数的名称和方法，该选项可以保留调试级别的属性。
-keepparameternames

# 过滤泛型,出现类型转换错误时再启用这个。目前的项目暂时无泛型类型，我先注释了
#-keepattributes Signature

# 保护代码中的Annotation不被混淆
-keepattributes *Annotation*

# 指定不去忽略非公共的库的类的成员
-dontskipnonpubliclibraryclassmembers

# 指定不去忽略非公共的库类(不跳过library中的非public的类)
-dontskipnonpubliclibraryclasses

#############################################
#
# Android开发中一些需要保留的公共部分
#
#############################################

# 保留我们使用的四大组件，自定义的Application等等这些类不被混淆
# 因为这些子类都有可能被外部调用
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Appliction
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService


# 保留support下的所有类及其内部类
-keep class android.support.** {*;}
#
# 保留继承的
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**
#
# 保留R下面的资源
-keep class **.R$* {*;}

# 保留本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留在Activity中的方法参数是view的方法，
# 这样以来我们在layout中写的onClick就不会被影响
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}

# 保留我们自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 保留Parcelable序列化类不被混淆
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# 保留Serializable序列化的类不被混淆
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 移除Log类打印各个等级日志的代码，打正式包的时候可以做为禁log使用，这里可以作为禁止log打印的功能使用
# 记得proguard-android.txt中一定不要加-dontoptimize才起作用
# 另外的一种实现方案是通过BuildConfig.DEBUG的变量来控制
-assumenosideeffects class android.util.Log {
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

# EventBus 3.0 混淆规则：http://greenrobot.org/eventbus/documentation/proguard/
#-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Only required if you use AsyncExecutor
#-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
#    <init>(java.lang.Throwable);
#}

# Plugin-Locale 混淆规则
# 保持了 utils 包下所有类里面所有 public 修饰的成员和方法
-keep class com.mallotec.reb.localeplugin.utils.* {
    public <methods>;
    public static final <fields>;
}
-keep class com.mallotec.reb.localeplugin.BaseLocaleApplication
-keep class com.mallotec.reb.localeplugin.LocalePlugin {
    public <methods>;
    public static final <fields>;
}
-keep class com.mallotec.reb.localeplugin.LocaleConstant {
    public static final <fields>;
}