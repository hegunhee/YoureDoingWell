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

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.authenticate)
    implementation(libs.firebase.realtimeDatabase)
    implementation(libs.firebase.storage)

    add("implementation",project(":feature:main"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
}
