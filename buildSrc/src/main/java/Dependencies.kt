import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {
    private const val ACTIVITY_KTX = "androidx.activity:activity-ktx:${Versions.ACTIVITY_KTX}"
    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${Versions.FRAGMENT_KTX}"
    private const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN}"
    private const val KTX_CORE = "androidx.core:core-ktx:${Versions.KTX_CORE}"
    private const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APPCOMPAT}"
    private const val GOOGLE_MATERIAL_LIB =
        "com.google.android.material:material:${Versions.GOOGLE_MATERIAL_LIB}"
    private const val SWIPE_REFRESH = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.SWIPE_REFRESH}"
    private const val LIFECYCLE_COMPILER = "androidx.lifecycle:lifecycle-compiler:${Versions.LIFECYCLE}"
    private const val LIFECYCLE_LIVE_DATA_KTX = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE}"
    private const val LIFECYCLE_VIEW_MODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE}"
    private const val CONSTRAINT_LAYOUT =
        "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
    private const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"
    private const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
    private const val GLIDE = "com.github.bumptech.glide:glide:${Versions.GLIDE}"
    private const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:${Versions.GLIDE}"
    private const val NAVIGATION_FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
    private const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}"

    //network
    private const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    private const val OKHTTP_INTERCEPTOR =
        "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"
    private const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
    private const val MOSHI_OKHTTP_CONVERTER = "com.squareup.retrofit2:converter-moshi:${Versions.RETROFIT}"
    private const val COROUTINES_ANDROID ="org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}"
    private const val COROUTINES =  "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}"
    private const val NETWORK_RESPONSE_ADAPTER =
        "com.github.haroldadmin:NetworkResponseAdapter:${Versions.NETWORK_RESPONSE_ADAPTER}"
    private const val MOSHI = "com.squareup.moshi:moshi:${Versions.MOSHI}"
    private const val MOSHI_KOTLIN = "com.squareup.moshi:moshi-kotlin:${Versions.MOSHI}"

    private const val HILT = "com.google.dagger:hilt-android:${Versions.HILT}"
    private const val HILT_VIEWMODEL = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.HILT_JETPACK}"

    //kapt
    private const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
    private const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
    private const val HILT_COMPILER = "androidx.hilt:hilt-compiler:${Versions.HILT_JETPACK}"

    //unit test
    private const val JUNIT = "junit:junit:${Versions.JUNIT}"
    private const val ROOM_TESTING = "androidx.room:room-testing:${Versions.ROOM}"
    private const val MOCKITO_CORE = "org.mockito:mockito-core:${Versions.MOCKITO}"

    // Instrumentation tests
    private const val JUNIT_ANDROID = "androidx.test.ext:junit:${Versions.JUNIT_ANDROID}"
    private const val ESPRESSO = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO}"
    private const val ESPRESSO_CONTRIB = "androidx.test.espresso:espresso-contrib:${Versions.ESPRESSO}"
    private const val RUNNER = "androidx.test:runner:${Versions.RUNNER}"

    val mainLibs = listOf(
        ACTIVITY_KTX,
        FRAGMENT_KTX,
        KOTLIN,
        KTX_CORE,
        APP_COMPAT,
        GOOGLE_MATERIAL_LIB,
        CONSTRAINT_LAYOUT,
        RETROFIT,
        OKHTTP,
        OKHTTP_INTERCEPTOR,
        MOSHI_OKHTTP_CONVERTER,
        COROUTINES,
        COROUTINES_ANDROID,
        NETWORK_RESPONSE_ADAPTER,
        MOSHI,
        MOSHI_KOTLIN,
        HILT,
        HILT_VIEWMODEL,
        ROOM_RUNTIME,
        ROOM_KTX,
        LIFECYCLE_LIVE_DATA_KTX,
        LIFECYCLE_VIEW_MODEL_KTX,
        GLIDE,
        NAVIGATION_FRAGMENT_KTX,
        NAVIGATION_UI_KTX,
        SWIPE_REFRESH
    )
    val kaptLibs = listOf(HILT_COMPILER, HILT_ANDROID_COMPILER, ROOM_COMPILER,LIFECYCLE_COMPILER, GLIDE_COMPILER)
    val unitTestLibs = listOf(JUNIT, ROOM_TESTING, MOCKITO_CORE)
    val uiTestLibs = listOf(JUNIT_ANDROID, ESPRESSO, ESPRESSO_CONTRIB, RUNNER)
}

fun DependencyHandler.implementation(dependencies: List<String>) =
    dependencies.forEach { dependency ->
        add("implementation", dependency)
    }

fun DependencyHandler.kapt(dependencies: List<String>) =
    dependencies.forEach { dependency ->
        add("kapt", dependency)
    }

fun DependencyHandler.testImplementation(testDependencies: List<String>) =
    testDependencies.forEach { testDependency ->
        add("testImplementation", testDependency)
    }

fun DependencyHandler.androidTestImplementation(androidTestDependencies: List<String>) =
    androidTestDependencies.forEach { androidTestDependency ->
        add("androidTestImplementation", androidTestDependency)
    }