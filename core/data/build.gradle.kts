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
    implementation(libs.firebase.storage)
    implementation(libs.androidx.rules)

    testImplementation(libs.assertj.core)

}
