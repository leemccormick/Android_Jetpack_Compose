package com.leemccormick.jetareader.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

// Create AppModule Object where we added provider to provide instances of anything that we needed for this applications.
// This is where we use Singleton and Providers

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

}