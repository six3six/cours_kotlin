package fr.lololoulou.chucknorrisapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.content.ContextCompat.startActivity

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

    private fun isSaved(id: String): Boolean {
        val preferences = context.getSharedPreferences("jokes", Context.MODE_PRIVATE)
        return (preferences?.getString(id, null) != null)
    }

    private fun changeFav(id: String) {
        val favButton = findViewById<ImageButton>(R.id.favButton)

        if (!isSaved(id)) {
            favButton.setImageDrawable(getDrawable(context, R.drawable.ic_baseline_star_border_24))
        } else {
            favButton.setImageDrawable(getDrawable(context, R.drawable.ic_baseline_star_24))
        }
    }


    fun setupView(model: Model) {
        findViewById<TextView>(R.id.jokeText).text = model.jokeText
        findViewById<ImageButton>(R.id.shareButton).setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, model.jokeText)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(context, shareIntent, Bundle())
        }

        val favButton = findViewById<ImageButton>(R.id.favButton)

        changeFav(model.id)
        favButton.setOnClickListener {
            val preferences = context.getSharedPreferences("jokes", Context.MODE_PRIVATE).edit()
            if (isSaved(model.id)) {
                preferences.remove(model.id)
            } else {
                preferences.putString(model.id, model.jokeText)
            }
            preferences.apply()
            changeFav(model.id)
        }
    }

}