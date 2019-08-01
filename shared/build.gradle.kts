import com.android.build.gradle.internal.getProguardFiles
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id("com.android.library")
    id("kotlin-multiplatform")
    id("maven-publish")
    id("kotlin-dce-js")
}

val iosFrameworkName = "mParticle_Apple_SDK_Media"
val iosClassNamePrefix = "MP"

kotlin {
    targets {
        val iOSTarget = if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true) {
            presets["iosArm64"]
        } else {
            presets["iosX64"]
        }



        android {
            publishLibraryVariants("release")
        }
        js()
        jvm()

        (targetFromPreset(iOSTarget, "ios") as KotlinNativeTarget)
                .binaries
                .framework(iosFrameworkName) {
                    compilation.extraOpts("-module-name", iosClassNamePrefix)
                }
    }

    sourceSets.let {
        val main = it.maybeCreate("commonMain")
        val js = it.maybeCreate("jsMain")
        val ios = it.maybeCreate("iosMain")

        val test = it.maybeCreate("commonTest")
        val androidTest = it.maybeCreate("androidTest")

        main.dependencies {
            implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
            implementation(kotlin("reflect"))
        }


        js.dependencies {
            implementation("org.jetbrains.kotlin:kotlin-stdlib-js")
        }

        test.dependencies {
//            implementation("org.jetbrains.kotlin:kotlin-test-common")
//            implementation("org.jetbrains.kotlin:kotlin-test-annotations-common")
            implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
            implementation(kotlin("test-common"))
            implementation(kotlin("test-annotations-common"))
        }

        androidTest.dependencies {
            implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
            implementation(kotlin("test-junit"))
//            implementation(kotlin("test-annotations-common"))
        }
    }

    js().compilations["main"].compileKotlinTask.kotlinOptions {
        this.sourceMap = true
        this.sourceMapEmbedSources = "always"
        this.moduleKind = "umd"
        this.noStdlib = true
        this.metaInfo = false
        this.main = "call"
    }
}

android {
    defaultConfig {
        compileSdkVersion(28)
    }

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}