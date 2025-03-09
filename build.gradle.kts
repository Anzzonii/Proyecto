// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("androidx.navigation.safeargs.kotlin") version "2.7.1" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}
val compileSdkVersion by extra(35)
