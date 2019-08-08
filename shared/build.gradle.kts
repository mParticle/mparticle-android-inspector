import com.android.build.gradle.internal.getProguardFiles
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id("kotlinx-serialization")

    id("kotlin-multiplatform")
    id("maven-publish")
    id("kotlin-dce-js")
}

val iosFrameworkName = "mParticle_Apple_SDK_Media"
val iosClassNamePrefix = "MP"

kotlin {
    targets {
//        val iOSTarget = if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true) {
//            presets["iosArm64"]
//        } else {
//            presets["iosX64"]
//        }

        js()
        jvm()
    }

    sourceSets.let {
        val main = it.maybeCreate("commonMain")
        val js = it.maybeCreate("jsMain")
        val jvm = it.maybeCreate("jvmMain")
//        val ios = it.maybeCreate("iosMain")

        val test = it.maybeCreate("commonTest")
        val androidTest = it.maybeCreate("androidTest")

        main.dependencies {
            implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:0.11.0")
            implementation(kotlin("reflect"))
        }


        js.dependencies {
            implementation("org.jetbrains.kotlin:kotlin-stdlib-js")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:0.11.0")
        }

        test.dependencies {
            implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
            implementation(kotlin("test-common"))
            implementation(kotlin("test-annotations-common"))
        }

        jvm.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.11.0")

        }

        androidTest.dependencies {
            implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
            implementation(kotlin("test-junit"))
//            implementation(kotlin("test-annotations-common"))
        }
//        ios.dependencies {
//            implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:0.11.0")
//        }
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