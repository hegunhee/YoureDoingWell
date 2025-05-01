plugins {
    id("youredoingwell.android.feature")
    alias(libs.plugins.google.services)
}

android {
    namespace = "doingwell.feature.daily"
}

dependencies {

    add("implementation", platform(libs.firebase.bom))
    add("implementation", libs.firebase.authenticate)
    add("implementation", libs.gms.play.services.auth)

}
