package fr.lololoulou.chucknorrisapp

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class JokeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


    init {
        //inflate(context, R.layout.joke_layout, this)

        LayoutInflater.from(context).inflate(R.layout.joke_layout, this, true)
        layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    data class Model(
        val id: String,
        val jokeText: String
    )

    fun setupView(model: Model) {
        findViewById<TextView>(R.id.jokeText).text = model.jokeText
    }

}