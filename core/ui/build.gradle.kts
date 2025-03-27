plugins {
    id("youredoingwell.android.library")
    id("youredoingwell.android.compose")
}

android {
    namespace = "doingwell.core.ui"
}

dependencies {
    implementation(project(":core:designsystem"))
}
