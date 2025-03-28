plugins {
    id("youredoingwell.android.feature")
    alias(libs.plugins.google.services)
}

android {
    namespace = "doingwell.feature.signin"

}

dependencies {

    add("implementation", platform(libs.firebase.bom))
    add("implementation", libs.firebase.authenticate)
    add("implementation", libs.androidx.material.icons.extensions)
}