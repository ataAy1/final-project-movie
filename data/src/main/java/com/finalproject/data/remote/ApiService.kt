package com.finalproject.data.remote

import com.finalproject.data.model.ApiResponse
import com.finalproject.data.model.BasketResponse
import com.finalproject.data.model.BasketItem
import com.finalproject.data.model.MovieResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("movies/getAllMovies.php")
    suspend fun getMovies(): MovieResponse


    @POST("movies/getMovieCart.php")
    @FormUrlEncoded
    suspend fun getMoviesCart(
        @Field("userName") userName: String
    ): BasketResponse



    @POST("movies/insertMovie.php")
    @FormUrlEncoded
    suspend fun addMovie(
        @Field("name") name: String,
        @Field("image") image: String,
        @Field("price") price: Int,
        @Field("category") category: String,
        @Field("rating") rating: Double,
        @Field("year") year: Int,
        @Field("director") director: String,
        @Field("description") description: String,
        @Field("orderAmount") orderAmount: Int,
        @Field("userName") userName: String
    ): ApiResponse


    @POST("movies/deleteMovie.php")
    @FormUrlEncoded
    suspend fun removeBasketItem(
        @Field("cartId") cartId: Int,
        @Field("userName") userName: String
    ): ApiResponse

}
