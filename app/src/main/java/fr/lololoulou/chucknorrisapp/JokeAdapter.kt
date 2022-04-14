package fr.lololoulou.chucknorrisapp

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JokeAdapter: RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {
    private val jokes = JokeList.getJokeList()
    inner class JokeViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    //Create Joke View Holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        return JokeViewHolder(TextView(parent.context))
    }
    //Replace the contents of the view
    override fun onBindViewHolder(holder: JokeAdapter.JokeViewHolder, position: Int) {
        holder.textView.text = jokes[position].value
    }
    // Return the size of jokes dataset
    override fun getItemCount() = jokes.size
}