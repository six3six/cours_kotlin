package fr.lololoulou.chucknorrisapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

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


        compositeDisposable.add(
            jokeService.giveMeAJoke().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
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