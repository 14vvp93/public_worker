plugins {
    alias(libs.plugins.android.application)
}

val appVersionName = "1.0.0"

android {
    namespace = "com.mt8163tools"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.mt8163tools"
        minSdk = 28
        targetSdk = 28
        versionCode = 10000
        versionName = appVersionName
    }

    buildFeatures {
        viewBinding = true
    }

    lint {
        disable += "ExpiredTargetSdkVersion"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
    }
}

androidComponents {
    onVariants(selector().withBuildType("debug")) { variant ->
        variant.outputs.forEach { output ->
            output.outputFileName.set("MT8163-Tools-v$appVersionName-${variant.name}.apk")
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment)
    compileOnly(libs.xposed.api)
}
