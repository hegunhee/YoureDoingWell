import com.hegunhee.build_logic.setting.configureComposeAndroid
import com.hegunhee.build_logic.setting.configureHilt
import com.hegunhee.build_logic.setting.configureKotlinAndroid
import com.hegunhee.build_logic.setup.libs

plugins {
    id("com.android.application")
}

configureKotlinAndroid()
configureComposeAndroid()
configureHilt()

dependencies {

    add("implementation", libs.findLibrary("androidx-core-ktx").get())
    add("implementation", libs.findLibrary("androidx-lifecycle-runtime-ktx").get())
    add("implementation", libs.findLibrary("androidx-activity-compose").get())

}
