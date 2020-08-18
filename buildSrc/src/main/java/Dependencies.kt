@file:Suppress("MayBeConstant")

object ApplicationID {
    val id = "com.currenciesapp"
}

object Releases {
    val versionCode = 1
    val versionName = "1"
}

object Modules {
    val app = ":app"
    val domain = ":domain"
    val data = ":data"
}

object Versions {
    val kotlin = "1.3.70"

    val gradle = "3.5.1"
    val minSDK = 23
    val targetSDK = 29
    val compileSDK = 29

    val instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    val buildToolsVersion = "29.0.2"
    val kotlinCoroutines = "1.3.9"

    val navigation = "2.3.0-alpha06"
    val appCompat = "1.1.0"
    val material = "1.1.0"
    val core_ktx = "1.2.0"
    val constraint = "1.1.3"

    val retrofit = "2.6.0"
    val okhttp = "3.12.0"
    val loggingInterceptor = "3.12.0"

    val moshi = "1.8.0"

    val koinAndroidX = "2.1.5"
    val koinViewModelAndroidX = "2.1.5"

    val mvrx = "1.4.0"
    val epoxy = "3.9.0"

    val room = "2.2.5"

    val threeTen = "1.2.1"

    val junit = "4.12"
    val ext_junit = "1.1.1"
    val espresso = "3.2.0"
    val mockk = "1.9.3"
    val assertjCore = "3.12.2"
    val threetenabp = "1.3.2"

    val timber = "4.7.1"

    val leakCanary = "2.0"

    val ktlint = "0.29.0"

    val currencyPicker = "1.2.1"
    val circleImageView = "3.1.0"
}

object Libraries {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    val coreKtx = "androidx.core:core-ktx:${Versions.core_ktx}"
    val constraint = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"

    val koinAndroidX = "org.koin:koin-androidx-scope:${Versions.koinAndroidX}"
    val koinViewModelAndroidX = "org.koin:koin-androidx-viewmodel:${Versions.koinViewModelAndroidX}"

    val kotlinCoroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"

    val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    val okHttpInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"

    val epoxy = "com.airbnb.android:epoxy:${Versions.epoxy}"
    val epoxyKapt = "com.airbnb.android:epoxy-processor:${Versions.epoxy}"

    val mvrx = "com.airbnb.android:mvrx:${Versions.mvrx}"

    val room = "androidx.room:room-runtime:${Versions.room}"
    val roomAnnotationProcessor =
        "androidx.room:room-compiler:${Versions.room}"

    val roomCoroutines = "androidx.room:room-ktx:${Versions.room}"

    val threeTen = "com.jakewharton.threetenabp:threetenabp:${Versions.threeTen}"

    val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    val navigationKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

    val material = "com.google.android.material:material:${Versions.material}"

    val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"

    val ktlint = "com.github.shyiko:ktlint:${Versions.ktlint}"

    val currencyPicker = "com.github.midorikocak:currency-picker-android:${Versions.currencyPicker}"

    val circleImageView = "de.hdodenhof:circleimageview:${Versions.circleImageView}"
}

object TestLibraries {
    val mvrx = "com.airbnb.android:mvrx-testing:${Versions.mvrx}"
    val assertjCore = "org.assertj:assertj-core:${Versions.assertjCore}"
    val mockk = "io.mockk:mockk:${Versions.mockk}"
    val junit = "junit:junit:${Versions.junit}"
    val extJunit = "androidx.test.ext:junit:${Versions.ext_junit}"
    val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    val kotlinCoroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinCoroutines}"
    val threetenabp = "org.threeten:threetenbp:${Versions.threetenabp}"
    val koin = "org.koin:koin-test:${Versions.koinAndroidX}"
    val roomTest =  "androidx.room:room-testing:${Versions.room}"
}
