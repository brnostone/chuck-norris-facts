# Chuck Norris facts
[![CircleCI](https://circleci.com/gh/brnostone/stone-challenge.svg?style=svg)](https://circleci.com/gh/brnostone/stone-challenge)

Used [Chuck Norris API](https://api.chucknorris.io/)

  - Full Kotlin
  - Multi Module
  - Model View ViewModel Architecture (MVVM)
  - Koin for Dependency Injection
  - RxJava
  - Retrofit
  - Mockito, Robolectric and Espresso for writing tests

## Building

**Build the APK into app/build/outputs/apk:**
```sh
./gradlew assembleRelease
```

**Install the APK directly on device:**
```sh
./gradlew installRelease
```

## Testing

**Run Unit Tests (debug + release):**
```sh
./gradlew test
``` 
    
**Run instrumentation tests:**
```sh
./gradlew connectedAndroidTest
```