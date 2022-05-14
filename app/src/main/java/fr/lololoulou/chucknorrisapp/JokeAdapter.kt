package fr.lololoulou.chucknorrisapp

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView

class JokeAdapter(val onBottomReached: () -> Unit) :
    RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {
    private var jokes = listOf<Joke>()

    inner class JokeViewHolder(private val jokeView: JokeView) :
        RecyclerView.ViewHolder(jokeView) {
        fun updateJoke(joke: Joke) {
            jokeView.setupView(JokeView.Model(joke.id, joke.value))
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
        /* val testView: ConstraintLayout =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.joke_layout, parent, false) as ConstraintLayout*/

        val jokeView = JokeView(parent.context)
        jokeView.layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
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