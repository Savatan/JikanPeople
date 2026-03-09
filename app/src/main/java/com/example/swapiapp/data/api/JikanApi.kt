package com.example.swapiapp.data.api

import com.example.swapiapp.data.dto.PeopleResponseDto
import com.example.swapiapp.data.dto.PersonDetailResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JikanApi {

    @GET("people")
    suspend fun getPeople(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25
    ): PeopleResponseDto

    @GET("people/{id}")
    suspend fun getPersonById(
        @Path("id") id: Int
    ): PersonDetailResponseDto

    @GET("people")
    suspend fun searchPeople(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25
    ): PeopleResponseDto
}
