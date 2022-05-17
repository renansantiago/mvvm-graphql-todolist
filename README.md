# Project Architecture
### Communication between layers 
1. UI calls methods from ViewModel.
2. ViewModel executes one or multiple Repository function.
3. The Repository returns data from one or multiple Data Sources (GraphQL API, Local Room DB). The repository is the single source of truth.
4. Information flows back to the UI where we display the data fetched from data sources.

# Project Structure
* Data
    * Repository implementation class as well as the remote and local data sources and mappers
* Domain
    * Domain models and repository interfaces.
* Presentation 
    * Views related code, packages are divided by feature.
* DI
    * Dependency injection and its configuration.
* Utils
    * Extension functions and helper files.

Project Tests
---------------
The project has some UI and Unit tests.

Libraries Used
---------------
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
* [LiveData](https://developer.android.com/jetpack/arch/livedata)
* [ViewBinding](https://developer.android.com/topic/libraries/view-binding/)
* [Material](https://material.io/develop/android/docs/getting-started/)
* [Coroutine](https://github.com/Kotlin/kotlinx.coroutines#user-content-android)
* [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/)
* [Apollo GraphQL](https://www.apollographql.com/docs/android/)
* [Dagger Hilt](https://dagger.dev/hilt/)
* [Room](https://developer.android.com/training/data-storage/room)
* [Espresso](https://developer.android.com/training/testing/espresso/)
* [Barista](https://github.com/AdevintaSpain/Barista)
* [JUnit](https://junit.org/junit4/)
* [Truth](https://github.com/google/truth)
