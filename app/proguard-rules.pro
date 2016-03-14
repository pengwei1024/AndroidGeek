# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/baidu/android/android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-keepattributes **
# Keep all classes except the ones indicated by the patterns preceded by an exclamation mark
-keep class !android.support.v7.view.menu.**,!android.support.design.internal.NavigationMenu,!android.support.design.internal.NavigationMenuPresenter,!android.support.design.internal.NavigationSubMenu,** {*;}
# Skip preverification
-dontpreverify
# Specifies not to optimize the input class files
-dontoptimize
# Specifies not to shrink the input class files
-dontshrink
# Specifies not to warn about unresolved references and other important problems at all
-dontwarn **
# Specifies not to print notes about potential mistakes or omissions in the configuration, such as
# typos in class names or missing options that might be useful
-dontnote **
