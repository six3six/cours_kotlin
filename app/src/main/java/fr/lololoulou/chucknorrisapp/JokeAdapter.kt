package fr.lololoulou.chucknorrisapp

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class JokeAdapter(val onBottomReached: () -> Unit) :
    RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {
    private var jokes = listOf<Joke>()

    inner class JokeViewHolder(private val constraintLayout: ConstraintLayout) :
        RecyclerView.ViewHolder(constraintLayout) {
        fun updateJoke(text: String) {
            val constraintValue: TextView =
                constraintLayout.findViewById<TextView>(R.id.textView_joke_layout)
            constraintValue.text = text
        }
    }

    fun addJoke(joke: Joke) {
        this.jokes = jokes + joke
        this.notifyItemInserted(jokes.size - 1)
    }

    fun addJokes(jokes: List<Joke>) {
        val firstIndex = this.jokes.size - 1
        this.jokes = this.jokes + jokes
        this.notifyItemRangeInserted(firstIndex, jokes.size)
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

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {

        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addOnScrollListener(JokeScrollListener(onBottomReached))
    }

    inner class JokeScrollListener(private val onBottomReached: () -> Unit) : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollVertically(1)){
                this.onBottomReached()
            }
        }
    }


    // Return the size of jokes dataset
    override fun getItemCount() = jokes.size

    fun getJokes() = jokes
}