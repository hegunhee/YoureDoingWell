plugins {
    id("youredoingwell.android.application")
    alias(libs.plugins.kotlin.compose)
    // Firebase
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.hegunhee.youredoingwell"

    defaultConfig {
        applicationId = "com.hegunhee.youredoingwell"
        versionCode = 1
        versionName = "1.0"
    }

}
