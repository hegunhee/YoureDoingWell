import com.hegunhee.build_logic.setting.configureHilt
import com.hegunhee.build_logic.setup.libs
import org.gradle.kotlin.dsl.dependencies

plugins {
    id("youredoingwell.android.library")
    id("youredoingwell.android.compose")
}

configureHilt()

dependencies {

    add("implementation", libs.findLibrary("androidx-core-ktx").get())
    add("implementation", libs.findLibrary("androidx-activity-compose").get())

    add("implementation", libs.findLibrary("androidx-lifecycle-runtime-ktx").get())
}
