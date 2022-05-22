package fr.lololoulou.chucknorrisapp


import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET


interface JokeApiService {
    @GET("jokes/random/")
    fun giveMeAJoke(): Single<Joke>
}