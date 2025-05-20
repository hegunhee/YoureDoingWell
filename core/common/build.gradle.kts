plugins {
    id("youredoingwell.android.library")
    id("youredoingwell.android.compose")
}

android {
    namespace = "doingwell.core.common"
}

dependencies {

    implementation(libs.androidx.lifecycle.runtime.ktx)
}
