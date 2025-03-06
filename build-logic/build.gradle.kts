plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.androidGradlePlugin)
    implementation(libs.kotlinGradlePlugin)
    implementation(libs.hiltGradlePlugin)
}

gradlePlugin {
    plugins {
        register("HiltAndroid") {
            id = "youredoingwell.hilt"
            implementationClass = "com.hegunhee.build_logic.setting.HiltPlugin"
        }
    }
}
