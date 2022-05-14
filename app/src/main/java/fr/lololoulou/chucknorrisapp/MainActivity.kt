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
import java.util.concurrent.TimeUnit

//simport kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myView = findViewById<RecyclerView>(R.id.recycleView_joke)
        myView.layoutManager = LinearLayoutManager(this)
        myView.adapter = JokeAdapter()

        val jokeService = JokeApiServiceFactory().create()

        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)


        progressBar.visibility = View.VISIBLE
        compositeDisposable.add(
            jokeService.giveMeAJoke().delay(1000, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).doFinally {
                    progressBar.visibility = View.INVISIBLE
                }.subscribeBy(
                    onSuccess = { joke -> (myView.adapter as? JokeAdapter)?.addJoke(joke) },
                    onError = { error -> error.printStackTrace() },
                )
        )
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}