package task.languagecard.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import task.languagecard.data.repository.card.CardRepository
import task.languagecard.data.repository.card.CardRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {
    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json(Json { prettyPrint = true } )
            }
            install(ContentEncoding) {
                gzip(0.9F)
                deflate(0.9F)
            }
            install(DefaultRequest) {
                url("https://pecto-content-f2egcwgbcvbkbye6.z03.azurefd.net/language-data/language-data/russian-finnish/")
                header("Accept", "*/*")
                header("Referer", "")
            }
            expectSuccess = true
        }
    }
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
abstract class CardRepositoryModule {
    @Singleton
    @Binds
    abstract fun bindsCardRepository(cardRepositoryImpl: CardRepositoryImpl): CardRepository
}