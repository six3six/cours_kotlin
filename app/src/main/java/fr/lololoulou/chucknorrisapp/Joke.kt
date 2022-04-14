package fr.lololoulou.chucknorrisapp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Joke(
    val categories: List<String> = listOf(),
    @SerialName("created_at") val createdAt: String,
    @SerialName("icon_url") val iconUrl: String,
    val id: String,
    @SerialName("updated_at") val updatedAt: String,
    val url: String,
    val value: String
)
