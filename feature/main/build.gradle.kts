plugins {
    id("youredoingwell.android.feature")
    alias(libs.plugins.google.services)
}

android {
    namespace = "doingwell.feature.main"
}

dependencies {

    implementation(project(":feature:signin"))
    implementation(project(":feature:daily"))
    implementation(project(":feature:addRecord"))
    implementation(project(":feature:addPhoto"))

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.authenticate)
    implementation(libs.gms.play.services.auth)

}
