package com.ludovic.vimont.mangareader.screens.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ludovic.vimont.mangareader.databinding.ItemGenreBinding

class DetailGenreAdapter(private val genres: ArrayList<String>): RecyclerView.Adapter<DetailGenreAdapter.GenreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) = holder.bind(genres[position])

    override fun getItemCount(): Int = genres.size

    fun setItems(items: List<String>) {
        genres.clear()
        genres.addAll(items)
        notifyDataSetChanged()
    }

    class GenreViewHolder(
        private val binding: ItemGenreBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: String) = with(binding) {
            textViewGenre.text = genre
        }
    }
}