package com.ludovic.vimont.mangareader.screens.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ludovic.vimont.mangareader.R
import com.ludovic.vimont.mangareader.entities.Manga
import kotlin.collections.ArrayList

class ListAdapter(private val mangas: ArrayList<Manga>): RecyclerView.Adapter<ListAdapter.MangaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_manga, parent, false)
        return MangaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MangaViewHolder, position: Int) {
        val manga: Manga = mangas[position]
        with(holder) {
            textViewTitle.text = manga.title
            Glide.with(itemView.context)
                .load(manga.cover)
                .into(imageViewCover)
        }
    }

    override fun getItemCount(): Int {
        return mangas.size
    }

    fun setItems(items: List<Manga>) {
        mangas.clear()
        mangas.addAll(items)
        notifyDataSetChanged()
    }

    inner class MangaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageViewCover: ImageView = itemView.findViewById(R.id.image_view_cover)
        val textViewTitle: TextView = itemView.findViewById(R.id.text_view_manga_title)
    }
}