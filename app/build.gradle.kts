plugins {
    id("youredoingwell.android.application")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.hegunhee.youredoingwell"

    defaultConfig {
        applicationId = "com.hegunhee.youredoingwell"
        versionCode = 1
        versionName = "1.0"
    }

}
