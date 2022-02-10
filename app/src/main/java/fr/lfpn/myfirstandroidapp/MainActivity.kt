package fr.lfpn.myfirstandroidapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val v = listOf("Laurent", "Axel", "Guillaume", "Théo", "Samuel", "Louis", "Victor")
        val vSorted = v.sortedBy { it.length }

        Log.d("Names", vSorted.toString())
        name_list.text = vSorted.toString()

        try_me.setOnClickListener {
            hello.text = vSorted[Random.nextInt(0, v.size)]
            val constraint = try_me.layoutParams as ConstraintLayout.LayoutParams
            constraint.bottomToTop = hello.id
            constraint.topToTop = -1
        }
    }

    override fun onPause() {
        super.onPause()
        hello.text = "Alors... On part ?????"
    }
}