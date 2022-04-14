package fr.lololoulou.chucknorrisapp

import JokeList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class JokeAdapter : RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {
    private val jokes = JokeList.getJokeList()

    inner class JokeViewHolder(val constraintLayout: ConstraintLayout) :
        RecyclerView.ViewHolder(constraintLayout) {
        fun updateJoke(text: String) {
            val constraintValue: TextView =
                constraintLayout.findViewById<TextView>(R.id.textView_joke_layout)
            constraintValue.text = text
        }
    }

    //Create Joke View Holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val testView: ConstraintLayout =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.joke_layout, parent, false) as ConstraintLayout
        return JokeViewHolder(testView)
    }

    //Replace the contents of the view
    override fun onBindViewHolder(holder: JokeAdapter.JokeViewHolder, position: Int) {
        holder.updateJoke(jokes[position].value)
    }

    // Return the size of jokes dataset
    override fun getItemCount() = jokes.size
}