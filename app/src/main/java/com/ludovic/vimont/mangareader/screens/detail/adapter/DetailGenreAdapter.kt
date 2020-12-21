package com.ludovic.vimont.mangareader.screens.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ludovic.vimont.mangareader.R

class DetailGenreAdapter(private val genres: ArrayList<String>): RecyclerView.Adapter<DetailGenreAdapter.GenreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        return GenreViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre: String = genres[position]
        with(holder) {
            textViewGenre.text = genre
        }
    }

    override fun getItemCount(): Int {
        return genres.size
    }

    fun setItems(items: List<String>) {
        genres.clear()
        genres.addAll(items)
        notifyDataSetChanged()
    }

    inner class GenreViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textViewGenre: TextView = itemView.findViewById(R.id.text_view_genre)
    }
}