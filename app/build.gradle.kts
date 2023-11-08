import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
}

fun getBaseUrl(propertyBaseUrl : String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyBaseUrl)
}

android {
    namespace = "com.example.numberoneproject"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.numberoneproject"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", getBaseUrl("BASE_URL"))
        buildConfigField("String", "NAVER_CLIENT_ID", getBaseUrl("NAVER_CLIENT_ID"))
        buildConfigField("String", "NAVER_CLIENT_SECRET", getBaseUrl("NAVER_CLIENT_SECRET"))
        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", "\"${getBaseUrl("KAKAO_NATIVE_APP_KEY")}\"")
        manifestPlaceholders["kakaoKey"] = "kakao${getBaseUrl("KAKAO_NATIVE_APP_KEY")}"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    // Retrofit, OkHttp
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.8.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.8.0")

    // viewModels
    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("androidx.fragment:fragment-ktx:1.6.1")

    // Hilt kotlin 1.9.0에서는 hilt 2.48
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    // Jetpack Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.6.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.6.0")

    // Naver Login
    implementation("com.navercorp.nid:oauth-jdk8:5.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")

    // Kakao Login
    implementation("com.kakao.sdk:v2-user:2.11.0")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Circle ImageView
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Ted Permission
    implementation("io.github.ParkSangGwon:tedpermission-normal:3.3.0")

    // lottie library
    implementation("com.airbnb.android:lottie:6.1.0")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.5.0"))
    implementation("com.google.firebase:firebase-messaging-ktx:23.3.1")

    // 현재 위치, GPS 추적
    implementation("com.google.android.gms:play-services-maps:18.0.0")
    implementation("com.google.android.gms:play-services-location:19.0.0")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")


    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}