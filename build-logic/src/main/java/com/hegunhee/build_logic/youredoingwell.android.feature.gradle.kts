import com.hegunhee.build_logic.setting.configureHilt
import com.hegunhee.build_logic.setup.libs
import org.gradle.kotlin.dsl.dependencies

plugins {
    id("youredoingwell.android.library")
    id("youredoingwell.android.compose")
}

configureHilt()

dependencies {

    add("implementation",project(":core:designsystem"))
    add("implementation",project(":core:ui"))
    add("implementation",project(":core:model"))

    add("implementation", libs.findLibrary("androidx-core-ktx").get())
    add("implementation", libs.findLibrary("androidx-activity-compose").get())

    add("implementation", libs.findLibrary("androidx-lifecycle-runtime-ktx").get())
    add("implementation",libs.findLibrary("androidx-appcompat").get())
    add("implementation",libs.findLibrary("material").get())

    add("implementation",libs.findLibrary("androidx-navigation").get())
    add("implementation",libs.findLibrary("hilt-navigation").get())
}
