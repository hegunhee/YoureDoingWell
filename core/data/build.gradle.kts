plugins {
    id("youredoingwell.android.library")
}

android {
    namespace = "doingwell.core.data"
}

dependencies {

    implementation(project(":core:domain"))
    implementation(project(":core:model"))

    implementation(libs.firebase.realtimeDatabase)
    testImplementation(libs.assertj.core)

}
