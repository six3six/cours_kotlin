package fr.lololoulou.chucknorrisapp

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class JokeAdapter(val onBottomReached: () -> Unit) :
    RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {
    private var jokes = mutableListOf<Joke>()

    inner class JokeViewHolder(private val jokeView: JokeView) :
        RecyclerView.ViewHolder(jokeView) {
        fun updateJoke(joke: Joke) {
            jokeView.setupView(JokeView.Model(joke.id, joke.value))
        }
    }

    fun addJoke(joke: Joke) {
        this.jokes.add(joke)
        this.notifyItemInserted(jokes.size - 1)
    }

    fun addJokes(jokes: List<Joke>) {
        val firstIndex = this.jokes.size - 1
        this.jokes.addAll(jokes)
        this.notifyItemRangeInserted(firstIndex, jokes.size)
    }

    fun swapJoke(jokeIndex: Int, target: Int) {
        val joke = jokes[jokeIndex]
        jokes[jokeIndex] = jokes[target]
        jokes[target] = joke

        this.notifyItemMoved(jokeIndex, target)
    }

    fun removeJoke(jokeIndex: Int) {
        jokes.removeAt(jokeIndex)

        this.notifyItemRemoved(jokeIndex)
    }

    fun wipe() {
        val size = jokes.size
        jokes = mutableListOf<Joke>()
        this.notifyItemRangeRemoved(0, size)
    }

    //Create Joke View Holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        return JokeViewHolder(JokeView(parent.context))
    }

    //Replace the contents of the view
    override fun onBindViewHolder(holder: JokeAdapter.JokeViewHolder, position: Int) {
        holder.updateJoke(jokes[position])
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {

        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addOnScrollListener(JokeScrollListener(onBottomReached))
    }

    inner class JokeScrollListener(private val onBottomReached: () -> Unit) :
        RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollVertically(1)) {
                this.onBottomReached()
            }
        }
    }


    // Return the size of jokes dataset
    override fun getItemCount() = jokes.size

    fun getJokes() = jokes


}