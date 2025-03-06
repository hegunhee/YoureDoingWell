import com.hegunhee.build_logic.setting.configureComposeAndroid
import com.hegunhee.build_logic.setting.configureKotlinAndroid

plugins {
    id("com.android.application")
}

configureKotlinAndroid()
configureComposeAndroid()