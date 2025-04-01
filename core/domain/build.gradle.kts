plugins {
    id("youredoingwell.kotlin.library")
    id("youredoingwell.hilt")
}

java {
}

dependencies {
    implementation(project(":core:model"))
}
