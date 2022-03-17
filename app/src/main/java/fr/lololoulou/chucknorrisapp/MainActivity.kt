package fr.lololoulou.chucknorrisapp

import JokeList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i("test",JokeList.getJokeList().toString())

        val myView = findViewById<RecyclerView>(R.id.recycleView_joke)
        myView.layoutManager = LinearLayoutManager(this)
    }
}