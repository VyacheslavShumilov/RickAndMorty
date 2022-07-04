package com.hfad.rickandmorty.model

import com.google.gson.annotations.SerializedName

data class ResponseCharacters(
    @SerializedName("info") val info: Info,
    @SerializedName("results") val characters: ArrayList<Characters>
)

data class Info(
    @SerializedName("count") var count: Int,
    @SerializedName("pages") var pages: Int,
    @SerializedName("next") var next: String? = null,
    @SerializedName("prev") var prev: String? = null
)

data class Characters(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val species: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("origin") val origin: Origin,
    @SerializedName("location") val location: Location,
    @SerializedName("image") val image: String,
    @SerializedName("episode") val episode: ArrayList<String>,
    @SerializedName("url") val url: String,
    @SerializedName("created") val created: String
)

data class Origin(
    @SerializedName("name") var name: String,
    @SerializedName("url") var url: String
)

data class Location(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)

data class Episode(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("air_date") val airDate: String,
    @SerializedName("episode") val episode: String
)



