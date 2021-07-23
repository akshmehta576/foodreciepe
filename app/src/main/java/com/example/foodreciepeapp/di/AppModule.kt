package com.example.foodreciepeapp.di

import com.example.foodreciepeapp.api.FoodReciepeApi
import com.example.foodreciepeapp.repository.FoodReciepeRepository
import com.example.foodreciepeapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideFoodReciepeRepository(
        api : FoodReciepeApi
    ) = FoodReciepeRepository(api)

    @Singleton
    @Provides
    fun providePokeApi():FoodReciepeApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build().create(FoodReciepeApi::class.java)
    }
}