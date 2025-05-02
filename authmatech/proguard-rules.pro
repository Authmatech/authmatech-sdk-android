# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Preserve public APIs and SDK interfaces

#-keep public class com.authmatech.sdk.** { *; }

# Retain JSON keys used in responses
#-keepclassmembers class * {
#    @org.json.* <fields>;
#}

# Preserve logging during debugging (optional)
#-assumenosideeffects class android.util.Log {
#    public static *** d(...);
#    public static *** v(...);
#}

# Support for Network operations
#-dontwarn okhttp3.**
#-dontwarn javax.annotation.**


# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile



