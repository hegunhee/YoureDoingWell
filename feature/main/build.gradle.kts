plugins {
    id("youredoingwell.android.feature")
    alias(libs.plugins.google.services)
}

android {
    namespace = "doingwell.feature.main"
}

dependencies {

    add("implementation", project(":feature:signin"))
    implementation(project(":feature:daily"))

    add("implementation", platform(libs.firebase.bom))
    add("implementation", libs.firebase.authenticate)
    add("implementation", libs.gms.play.services.auth)
}