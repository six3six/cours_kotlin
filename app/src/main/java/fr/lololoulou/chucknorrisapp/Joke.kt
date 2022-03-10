package fr.lololoulou.chucknorrisapp

import java.util.*

data class Joke(
    val categories: List<String>,
    val createdAt: String,
    val iconUrl: String,
    val id: String,
    val updatedAt: String,
    val url : String,
    val value : String
)
