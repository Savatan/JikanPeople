package com.example.swapiapp.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class PeopleResponseDto(
    @Json(name = "pagination") val pagination: PaginationDto,
    @Json(name = "data") val data: List<PersonDto>
)

@JsonClass(generateAdapter = false)
data class PersonDetailResponseDto(
    @Json(name = "data") val data: PersonDto
)

@JsonClass(generateAdapter = false)
data class PaginationDto(
    @Json(name = "last_visible_page") val lastVisiblePage: Int,
    @Json(name = "has_next_page") val hasNextPage: Boolean,
    @Json(name = "current_page") val currentPage: Int
)

@JsonClass(generateAdapter = false)
data class PersonDto(
    @Json(name = "mal_id") val malId: Int,
    @Json(name = "url") val url: String?,
    @Json(name = "website_url") val websiteUrl: String?,
    @Json(name = "images") val images: ImagesDto?,
    @Json(name = "name") val name: String?,
    @Json(name = "given_name") val givenName: String?,
    @Json(name = "family_name") val familyName: String?,
    @Json(name = "alternate_names") val alternateNames: List<String>?,
    @Json(name = "birthday") val birthday: String?,
    @Json(name = "favorites") val favorites: Int?,
    @Json(name = "about") val about: String?
)

@JsonClass(generateAdapter = false)
data class ImagesDto(
    @Json(name = "jpg") val jpg: ImageUrlDto?
)

@JsonClass(generateAdapter = false)
data class ImageUrlDto(
    @Json(name = "image_url") val imageUrl: String?
)
