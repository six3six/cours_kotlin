package fr.lololoulou.chucknorrisapp

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
        val jokeAdapter = JokeAdapter(onBottomReached = { fetchAndAddJokes(jokeService) })
        jokeView.adapter = jokeAdapter
        JokeTouchHelper(jokeAdapter).attachToRecyclerView(jokeView)

        jokeInit(jokeAdapter, savedInstanceState, jokeService)

        val swipeRefresh = findViewById<SwipeRefreshLayout>(R.id.swiperefresh)
        swipeRefresh.setColorSchemeColors(Color.BLUE)
        swipeRefresh.setSize(SwipeRefreshLayout.LARGE)
        swipeRefresh.setOnRefreshListener {

            jokeInit(jokeAdapter, savedInstanceState, jokeService)
            swipeRefresh.isRefreshing = false
        }


    }

    private fun jokeInit(
        jokeAdapter: JokeAdapter,
        savedInstanceState: Bundle?,
        jokeService: JokeApiService
    ) {
        jokeAdapter.wipe()
        val preferences = this.getSharedPreferences("jokes", Context.MODE_PRIVATE)
        preferences.all.forEach {
            jokeAdapter.addJoke(Joke(value = it.value as String, id = it.key))
        }

        val serializedJokes = savedInstanceState?.getString("jokes")
        if (serializedJokes != null) {
            val jokes = Json.decodeFromString<List<Joke>>(serializedJokes)
            jokeAdapter.addJokes(jokes)
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