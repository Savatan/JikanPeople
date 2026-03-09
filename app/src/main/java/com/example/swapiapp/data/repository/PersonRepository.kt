package com.example.swapiapp.data.repository

import com.example.swapiapp.data.api.JikanApi
import com.example.swapiapp.data.dto.PersonDto
import com.example.swapiapp.domain.model.Person
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonRepository @Inject constructor(
    private val api: JikanApi
) {

    suspend fun getPeople(page: Int = 1): List<Person> {
        val response = api.getPeople(page = page)
        return response.data.map { it.toPerson() }
    }

    suspend fun getPersonById(id: Int): Person {
        val response = api.getPersonById(id)
        return response.data.toPerson()
    }


    suspend fun searchPeople(rawQuery: String): List<Person> {
        val normalized = normalizeQuery(rawQuery)
        if (normalized.isBlank()) {
            return getPeople()
        }

        val response = api.searchPeople(query = normalized)
        val apiResults = response.data.map { it.toPerson() }

        return apiResults.filter { person ->
            val q = normalized.lowercase()
            person.name.lowercase().contains(q) ||
                person.givenName.lowercase().contains(q) ||
                person.familyName.lowercase().contains(q)
        }.ifEmpty {

            apiResults
        }
    }

    companion object {

        fun normalizeQuery(input: String): String {
            return input
                .trim()
                .lowercase()
                .replace(Regex("\\s{2,}"), " ")
        }
    }

    private fun PersonDto.toPerson(): Person {
        val formattedBirthday = birthday
            ?.substringBefore("T")
            ?: "Unknown"

        return Person(
            id = malId,
            name = name ?: "Unknown",
            imageUrl = images?.jpg?.imageUrl,
            givenName = givenName ?: "",
            familyName = familyName ?: "",
            alternateNames = alternateNames ?: emptyList(),
            birthday = formattedBirthday,
            favorites = favorites ?: 0,
            about = about ?: "",
            url = url ?: "",
            websiteUrl = websiteUrl ?: ""
        )
    }
}
