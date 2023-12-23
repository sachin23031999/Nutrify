object Versions {

    const val compileSdk = 34
    const val minSdk = 31
    const val targetSdk = 34

    const val buildgradle = "7.4.2"
    const val kotlingradleplugin = "1.8.20"

    const val webrtc = "1.0.32006"
    const val timber = "4.7.1"
    const val googleservices = "4.3.13"

    const val dokka = "1.5.31"
    const val versioncheck = "0.29.0"

    const val lifecycle = "2.5.1"
    const val appcompat = "1.6.1"
    const val constraint = "2.1.4"
    const val annotation = "1.4.0"
    const val ktx = "1.10.0"
    const val navigation = "2.5.1"
    const val workmanager = "2.8.1"
    const val datasource = "1.1.0-alpha04"

    const val reflect = "1.6.0"
    const val stdlib = "1.6.0"
    const val coroutines = "1.6.0"

    const val dynamicAnimation = "1.0.0-alpha03"

    const val koin = "3.4.0"

    const val material = "1.9.0"

    const val androidxcore = "1.4.0"
    const val androidxjunit = "1.1.3"
    const val junit = "4.13.2"
    const val mockk = "1.12.4"

    const val moto_checkin = "30.002.00"
    const val moto_settings = "31.035.00"
    const val moto_core_services = "33.025.00"
    const val glide = "4.14.1"
    const val gson: String = "2.9.0"
    const val room: String = "2.5.2"
    const val biometric = "1.2.0-alpha04"
    const val viewpager2 = "1.0.0"
    const val phone_number = "8.13.13"
    const val flag_kit = "1.0.2"
    const val flexbox = "2.0.1"

    const val safetynet = "18.0.1"
    const val location = "21.0.1"
    const val firebase = "32.2.2"
    const val firebasemessaging = "23.2.1"
    const val config = "21.4.1"
    const val retrofit = "2.9.0"
    const val zxing = "3.3.2"
    const val logginginteceptor = "4.9.1"

    const val maps = "2.4.0"
    const val play_services_maps = "18.1.0"
    const val billing_version = "6.0.1"

    const val insetter = "0.6.1"

    const val numberPickerVersion = "2.4.13"
    const val custom_tab = "1.5.0"
    const val secure_preferences = "1.1.0-alpha06"

    const val camerax = "1.3.0-alpha07"
    const val mlkit_barcode = "17.1.0"

    const val shimmer_Version = "0.5.0"

    const val lottieVersion = "3.4.0"
    const val secrets = "2.0.1"

    const val metricsdk = "01.00.00.05"
    const val motobillingclient = "1.0.0.016"
    const val lenovoid = "1.0.15"

    const val places = "3.2.0"

    const val root_beer_fresh = "0.0.10"
}

object Deps {

    val shimmer = "com.facebook.shimmer:shimmer:${Versions.shimmer_Version}"
    val lottie = "com.airbnb.android:lottie:${Versions.lottieVersion}"

    val buildgradle = "com.android.tools.build:gradle:${Versions.buildgradle}"
    val gradleplugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlingradleplugin}"
    val googleservices = "com.google.gms:google-services:${Versions.googleservices}"
    val dokka = "org.jetbrains.dokka:dokka-gradle-plugin:${Versions.dokka}"
    val versioncheck = "com.github.ben-manes:gradle-versions-plugin:${Versions.versioncheck}"
    val material = "com.google.android.material:material:${Versions.material}"
    val numberPick = "io.github.ShawnLin013:number-picker:${Versions.numberPickerVersion}"

    val play_services = object {
        val safetynet = "com.google.android.gms:play-services-safetynet:${Versions.safetynet}"
        val location = "com.google.android.gms:play-services-location:${Versions.location}"
        val maps = "com.google.android.gms:play-services-maps:${Versions.play_services_maps}"
        val billing = "com.android.billingclient:billing-ktx:${Versions.billing_version}"
    }

    val android = object {
        val flexbox = "com.google.android:flexbox:${Versions.flexbox}"
    }

    val androidx = object {
        val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        val ktx = "androidx.core:core-ktx:${Versions.ktx}"
        val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"
        val annotation = "androidx.annotation:annotation:${Versions.annotation}"
        val room_compiler: String = "androidx.room:room-compiler:${Versions.room}"
        val room_runtime: String = "androidx.room:room-runtime:${Versions.room}"
        val room_ktx: String = "androidx.room:room-ktx:${Versions.room}"
        val biometric = "androidx.biometric:biometric-ktx:${Versions.biometric}"
        val navigation_fragment =
            "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
        val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
        val navigation_safe_args =
            "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
        val viewpager2 = "androidx.viewpager2:viewpager2:${Versions.viewpager2}"
        val lib_phone_number =
            "com.googlecode.libphonenumber:libphonenumber:${Versions.phone_number}"
        val flag_kit =
            "com.github.murgupluoglu:flagkit-android:${Versions.flag_kit}"

        val dynamicAnimation =
            "androidx.dynamicanimation:dynamicanimation-ktx:${Versions.dynamicAnimation}"
        val lifecycle = object {
            val runtime_ktx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
            val extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
            val process = "androidx.lifecycle:lifecycle-process:${Versions.lifecycle}"
        }
        val workmanager = "androidx.work:work-runtime-ktx:${Versions.workmanager}"
        val datasource = "androidx.datastore:datastore-preferences:${Versions.datasource}"
        val custom_tabs = "androidx.browser:browser:${Versions.custom_tab}"
        val secure_preferences = "androidx.security:security-crypto:${Versions.secure_preferences}"
    }

    val kotlinx = object {
        val coroutines_android =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        val coroutines_play_services =
            "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.coroutines}"
    }

    val jetbrains = object {
        val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.reflect}"
        val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.stdlib}"
    }

    val coroutines = object {
        val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    }

    val koin = object {
        val core = "io.insert-koin:koin-android:${Versions.koin}"
        val test = "io.insert-koin:koin-test:${Versions.koin}"
    }

    val test = object {
        val androidx = object {
            val core = "androidx.test:core:${Versions.androidxcore}"
            val junit = "androidx.test.ext:junit:${Versions.androidxjunit}"
        }
        val junit = "junit:junit:${Versions.junit}"
        val mockk = "io.mockk:mockk:${Versions.mockk}"
    }

    val moto = object {
        val checkin = "moto-checkin:moto-checkin:${Versions.moto_checkin}"
        val settings = "moto-settings:moto-settings:${Versions.moto_settings}"
        val core_services = "moto-core_services:moto-core_services:${Versions.moto_core_services}"
    }

    val glide = object {
        val lib = "com.github.bumptech.glide:glide:${Versions.glide}"
        val compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    }

    val maps = object {
        val utils = "com.google.maps.android:android-maps-utils:${Versions.maps}"
    }

    const val gson = "com.google.code.gson:gson:${Versions.gson}"

    val retrofit = object {
        val builder = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        val converter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    }

    const val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.logginginteceptor}"

    val zxing = "com.google.zxing:core:${Versions.zxing}"

    val insetter = "dev.chrisbanes.insetter:insetter:${Versions.insetter}"

    val mlkit_barcode = "com.google.mlkit:barcode-scanning:${Versions.mlkit_barcode}"

    val camerax_camera = "androidx.camera:camera-camera2:${Versions.camerax}"
    val camerax_lifecycle = "androidx.camera:camera-lifecycle:${Versions.camerax}"
    val camerax_view = "androidx.camera:camera-view:${Versions.camerax}"

    val firebase = object {
        val bom = "com.google.firebase:firebase-bom:${Versions.firebase}"
        val messaging_ktx =
            "com.google.firebase:firebase-messaging-ktx:${Versions.firebasemessaging}"
        val config = "com.google.firebase:firebase-config-ktx:${Versions.config}"
    }

    val secrets = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:" +
        "secrets-gradle-plugin:${Versions.secrets}"

    val metricsdk = "com.motorola.metrics:motometrics:${Versions.metricsdk}"
    val moto_billing_client = "com.motorola:billingclient:${Versions.motobillingclient}"
    val lenovo_id = "com.moto.lenovoidsdk:moto_row_sdk:v${Versions.lenovoid}@aar"

    val places = "com.google.android.libraries.places:places:${Versions.places}"

    val webrtc = "org.webrtc:google-webrtc:${Versions.webrtc}"
    val timberLogger = "com.jakewharton.timber:timber:${Versions.timber}"
    const val root_beer_fresh = "com.github.kimchangyoun:rootbeerFresh:${Versions.root_beer_fresh}"
}
