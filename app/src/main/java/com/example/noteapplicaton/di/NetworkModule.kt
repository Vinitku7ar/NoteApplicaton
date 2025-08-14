//humare application class direct interact nhi kar sakta sever se toh iss liye hume module
//ki jarurat padi ,okayy thik hai ,
//oh shit , here we go again
package com.example.noteapplicaton.di

import com.example.noteapplicaton.api.AuthInterceptor
import com.example.noteapplicaton.api.NotesAPI
import com.example.noteapplicaton.api.UserAPI
import com.example.noteapplicaton.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create()).
            baseUrl(BASE_URL)

    }
    @Singleton
    @Provides
    fun provideOkHttpCLient(authInterceptor: AuthInterceptor):OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }
    @Singleton
    @Provides
    fun providesUserAPI(retrofitBuilder: Retrofit.Builder): UserAPI {
        return retrofitBuilder.build().create(UserAPI::class.java)
    }


    @Singleton
    @Provides
    fun providesNoteAPI(retrofitBuilder:Retrofit.Builder,okHttpClient: OkHttpClient):NotesAPI{
        return retrofitBuilder.
            client(okHttpClient).
            build().create(NotesAPI::class.java)
    }
}

//THIS ARE UNAUTHORIZED ENDPOINT SO NO NEED TO PASS TOKEN