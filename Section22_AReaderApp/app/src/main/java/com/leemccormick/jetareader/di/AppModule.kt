package com.leemccormick.jetareader.di

import com.leemccormick.jetareader.network.BooksApi
import com.leemccormick.jetareader.repository.BookRepository
import com.leemccormick.jetareader.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// Create AppModule Object where we added provider to provide instances of anything that we needed for this applications.
// This is where we use Singleton and Providers

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // After create the BookRepository, then we have to come back to this AppModule to inject with this...
    @Singleton
    @Provides
    fun provideBookRepository(api: BooksApi) = BookRepository(api)

    // After create BookApi Interface then connect it here...
    @Singleton
    @Provides
    fun provideBookApi(): BooksApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java) // Create the BookApi service
    }
}