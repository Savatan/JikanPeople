package com.example.swapiapp.domain.model

data class Person(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val givenName: String,
    val familyName: String,
    val alternateNames: List<String>,
    val birthday: String,
    val favorites: Int,
    val about: String,
    val url: String,
    val websiteUrl: String
)
