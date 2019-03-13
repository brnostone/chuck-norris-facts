# Chuck Norris facts

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
```
./gradlew assembleRelease
```

**Install the APK directly on device:**
```
./gradlew installRelease
```

## Testing

**Run Unit Tests (debug + release):**
```
./gradlew test
``` 
    
**Run instrumentation tests:**
```
./gradlew connectedAndroidTest
```