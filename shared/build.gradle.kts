plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidLibrary {
        namespace = "org.shad.adman.jaw.generation.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        androidResources.enable = true
    }


    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }
    sourceSets {
        androidMain.dependencies {
            implementation(libs.accompanist.permissions)
            implementation(libs.ui.tooling)
        }
        commonMain.dependencies {
            implementation(libs.foundation)
            implementation(libs.components.resources)
            implementation(libs.ui.tooling.preview)
            implementation(libs.runtime)
            implementation(libs.material3)
            implementation(libs.ui)
        }
    }

}

compose.resources {
    publicResClass = true
    packageOfResClass = "shared.resources"
    generateResClass = auto
}
