package fr.lololoulou.chucknorrisapp

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

//simport kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jokeService = JokeApiServiceFactory().create()

        val jokeView = findViewById<RecyclerView>(R.id.recycleView_joke)
        jokeView.layoutManager = LinearLayoutManager(this)
        jokeView.adapter = JokeAdapter(onBottomReached = { fetchAndAddJokes(jokeService) })

        val serializedJokes = savedInstanceState?.getString("jokes")
        if (serializedJokes != null) {
            val jokes = Json.decodeFromString<List<Joke>>(serializedJokes)
            (jokeView.adapter as? JokeAdapter)?.addJokes(jokes)
        } else {
            fetchAndAddJokes(jokeService)
        }
    }

    private fun fetchAndAddJokes(jokeService: JokeApiService) {
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        val jokeView = findViewById<RecyclerView>(R.id.recycleView_joke)

        progressBar.visibility = View.VISIBLE
        compositeDisposable.add(
            jokeService.giveMeAJoke().repeat(10).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).doFinally {
                    progressBar.visibility = View.INVISIBLE
                }.subscribeBy(
                    onNext = { joke -> (jokeView.adapter as? JokeAdapter)?.addJoke(joke) },
                    onError = { error -> error.printStackTrace() },
                )
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val myView = findViewById<RecyclerView>(R.id.recycleView_joke)
        val jokes = (myView?.adapter as? JokeAdapter)?.getJokes();
        outState.putString("jokes", Json.encodeToString(jokes))

        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}