package com.finalproject.di

import android.util.Log
import com.finalproject.data.local.MoviesDao
import com.finalproject.data.mapper.BasketMapper
import com.finalproject.data.remote.ApiService
import com.finalproject.data.repository.AuthRepositoryImpl
import com.finalproject.data.repository.BasketRepositoryImpl
import com.finalproject.data.repository.MovieRepositoryImpl
import com.finalproject.data.repository.OrderRepositoryImpl
import com.finalproject.data.repository.ProfileRepositoryImpl
import com.finalproject.domain.repository.AuthRepository
import com.finalproject.domain.repository.BasketRepository
import com.finalproject.domain.repository.MovieRepository
import com.finalproject.domain.repository.OrderRepository
import com.finalproject.domain.repository.ProfileRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideProfileRepository(
        firestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth,
        firebaseStorage: FirebaseStorage

    ): ProfileRepository {
        return ProfileRepositoryImpl(firestore, firebaseAuth, firebaseStorage)
    }

    @Provides
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    fun provideMovieRepository(
        apiService: ApiService,
        moviesDao: MoviesDao
    ): MovieRepository {
        return MovieRepositoryImpl(apiService, moviesDao)
    }


    @Provides
    fun provideBasketRepository(
        apiService: ApiService,
        basketMapper: BasketMapper
    ): BasketRepository {
        return BasketRepositoryImpl(apiService, basketMapper)
    }



    @Provides
    fun provideOrderRepository(
        firestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth,

    ): OrderRepository {
        return OrderRepositoryImpl(firestore, firebaseAuth)
    }
}