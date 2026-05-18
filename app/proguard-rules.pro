# Keep Hilt
-keepclassmembers class * {
    @dagger.hilt.android.EarlyEntryPoint <methods>;
}

# Keep Room entities
-keep class com.roamguard.data.local.entity.** { *; }

# Keep serialization
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

# Keep reflection targets
-keep class com.roamguard.root.** { *; }
-keep class com.roamguard.shizuku.** { *; }
