package com.ludovic.vimont.mangareader.screens.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ludovic.vimont.mangareader.R
import com.ludovic.vimont.mangareader.entities.Manga
import kotlin.collections.ArrayList

class ListAdapter(private val mangas: ArrayList<Manga>): RecyclerView.Adapter<ListAdapter.MangaViewHolder>() {
    var onItemClick: ((Manga) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_manga, parent, false)
        return MangaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MangaViewHolder, position: Int) {
        val manga: Manga = mangas[position]
        with(holder) {
            val context: Context = itemView.context
            textViewTitle.text = manga.title
            textViewScore.text = context.getString(R.string.list_fragment_item_score, manga.score)
            imageViewCover.load(manga.cover)
            itemView.setOnClickListener {
                onItemClick?.invoke(manga)
            }
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
        val textViewScore: TextView = itemView.findViewById(R.id.text_view_manga_score)
    }
}