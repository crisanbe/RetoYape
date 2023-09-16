plugins {
    id("com.android.application")
    kotlin("android")
    id("kover")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.retolistaderecetas"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.retolistaderecetas"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    tasks.withType<JavaCompile> {
        options.compilerArgs.addAll(listOf("-source", "1.8", "-target",  "1.8"))
    }
    buildFeatures {
        compose = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeCompilerVersion
    }

    packagingOptions {
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
        exclude("**/attach_hotspot_windows.dll")
        exclude("META-INF/licenses/ASM")
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            unitTests.isReturnDefaultValues = true
        }
    }
}

dependencies {
    //map
    implementation("com.google.maps.android:maps-compose:2.11.4")
    implementation("com.google.android.gms:play-services-maps:18.1.0")

    //Test
    implementation(Compose.compiler)
    implementation(Compose.ui)
    implementation(Compose.lifecycleCompose)
    implementation(Compose.uiToolingPreview)
    implementation(Compose.hiltNavigationCompose)
    implementation(Compose.material_3)
    implementation(Compose.runtime)
    implementation(Compose.navigation)
    implementation(Compose.viewModelCompose)
    implementation(Compose.activityCompose)
    implementation(Compose.pagingCompose)
    implementation(Compose.pagingIndicatorCompose)
    implementation(Compose.constraintLayoutCompose)
    implementation(DaggerHilt.hiltAndroid)
    implementation("androidx.compose.material:material-icons-extended:1.5.1")
    implementation(Coil.coilCompose)
    implementation(Coil.coilSvg)

    implementation(Retrofit.okHttp)
    implementation(Retrofit.retrofit)
    implementation(Retrofit.okHttpLoggingInterceptor)
    implementation(Retrofit.gsonConverter)
    implementation(Gson.gson)
    implementation(Room.roomKtx)
    implementation(Room.roomRuntime)
    kapt(Room.roomCompiler)

    kapt(DaggerHilt.hiltCompiler)
    debugImplementation(Compose.uiTooling)
    testImplementation(Testing.junit4)
    testImplementation(Testing.junitAndroidExt)
    testImplementation(Testing.truth)
    testImplementation(Testing.coroutines)
    testImplementation(Testing.testCorutinesCore)
    testImplementation(Testing.turbine)
    testImplementation(Testing.mockk)
    testImplementation(Testing.mockito)
    testImplementation(Testing.testMockitoOnlineVersion)
    testImplementation(Testing.mockWebServer)
    testImplementation(Testing.testArchCoreTesting)
    testImplementation(Testing.testHamcrest)
    testImplementation(Testing.testCoreKtx)
    testImplementation(Testing.testExtJunitKtx)
    testImplementation(Testing.daggerHiltRobolectricTest)
    kaptTest(Testing.robolectricDaggerHiltCompileTest)
    coreLibraryDesugaring(Testing.coreLibrary)
    androidTestImplementation(Testing.junit4)
    androidTestImplementation(Testing.junitAndroidExt)
    androidTestImplementation(Testing.truth)
    androidTestImplementation(Testing.coroutines)
    androidTestImplementation(Testing.turbine)
    androidTestImplementation(Testing.mockk)
    androidTestImplementation(Testing.mockWebServer)
    androidTestImplementation(Testing.hiltTesting)
}