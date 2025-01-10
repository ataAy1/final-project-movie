package com.finalproject.di

import android.util.Log
import com.finalproject.domain.repository.BasketRepository
import com.finalproject.domain.repository.MovieRepository
import com.finalproject.domain.repository.ProfileRepository
import com.finalproject.domain.usecase.basket.AddItemToBasketUseCase
import com.finalproject.domain.usecase.movies.GetMovieByIdUseCase
import com.finalproject.domain.usecase.profile.address.GetAddressUseCase
import com.finalproject.domain.usecase.profile.address.SaveAddressUseCase
import com.finalproject.domain.usecase.profile.cards.GetCardsUseCase
import com.finalproject.domain.usecase.profile.cards.SaveCardUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetCardsUseCase(
        repository: ProfileRepository
    ): GetCardsUseCase {
        return GetCardsUseCase(repository)
    }

    @Provides
    fun provideSaveCardUseCase(
        repository: ProfileRepository
    ): SaveCardUseCase {
        return SaveCardUseCase(repository)
    }

    @Provides
    fun provideGetAddressUseCase(
        profileRepository: ProfileRepository
    ): GetAddressUseCase {
        return GetAddressUseCase(profileRepository)
    }

    @Provides
    fun provideSaveAddressUseCase(
        profileRepository: ProfileRepository
    ): SaveAddressUseCase {
        return SaveAddressUseCase(profileRepository)
    }

    @Provides
    fun provideGetMovieByIdUseCase(
        movieRepository: MovieRepository
    ): GetMovieByIdUseCase {
        return GetMovieByIdUseCase(movieRepository)
    }

    @Provides
    fun provideAddMovieToBasketUseCase(
        basketRepository: BasketRepository
    ): AddItemToBasketUseCase {
        return AddItemToBasketUseCase(basketRepository)
    }

}