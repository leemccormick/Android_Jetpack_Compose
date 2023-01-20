package com.leemccormick.jetareader.network

import com.leemccormick.jetareader.model.Book
import com.leemccormick.jetareader.model.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

/* To Set Up how to work with API
1) Use Libraries --> Retrofit and GSON, added in on build.gradle
2) Use GSON to create an object from JSON Link in model --> https://www.googleapis.com/books/v1/volumes?q=android
3) Create object Constants and Base_Url variable on util folder
4) Create interface BookApi on network folder and use @Singleton and @GET
5) Go to AppModule in di folder to create @Provide and connect it with BookApi
6) Create an repository in repository folder to inject BookApi and use it to actual call the functions in viewModel
7) In data folder, create data class DataOrException for receive data from api call in repository
8) After create the BookRepository, then we have to go back to this AppModule to inject and provide BookRepository with Api : --> fun provideBookRepository(api: BooksApi) = BookRepository(api)
8) Create viewModel and inject repository on viewModel and use it to get data from api
 */

@Singleton
interface BooksApi {
    // https://www.googleapis.com/books/v1/volumes?q=android
    @GET("volumes")
    suspend fun getAllBooks(@Query("q") query: String) : Book

    @GET("volumes/{bookId}")
    suspend fun getBookInfo(@Path("bookId") bookId: String) : Item
}