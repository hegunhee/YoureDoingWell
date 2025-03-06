import com.hegunhee.build_logic.setting.configureHilt
import com.hegunhee.build_logic.setting.configureKotlinAndroid

plugins {
    id("com.android.library")
}

configureKotlinAndroid()
configureHilt()
