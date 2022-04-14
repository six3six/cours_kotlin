package fr.lololoulou.chucknorrisapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

//simport kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jokeService = JokeApiServiceFactory().create()

        val subscription = jokeService.giveMeAJoke().subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { joke -> Log.i("jokeService", "$joke") },
                onError = { error ->
                    error.printStackTrace()
                    Log.i("jokeService", "${error.message}")
                },
            )

        //Log.i("test", JokeList.getJokeList().toString())

        val myView = findViewById<RecyclerView>(R.id.recycleView_joke)
        myView.layoutManager = LinearLayoutManager(this)
        myView.adapter = JokeAdapter()

    }
}