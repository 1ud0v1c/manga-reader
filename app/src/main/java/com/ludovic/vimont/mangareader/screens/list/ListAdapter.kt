package com.ludovic.vimont.mangareader.screens.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ludovic.vimont.mangareader.R
import com.ludovic.vimont.mangareader.databinding.ItemMangaBinding
import com.ludovic.vimont.mangareader.entities.Manga
import kotlin.collections.ArrayList

class ListAdapter(
    private val mangas: ArrayList<Manga>
): RecyclerView.Adapter<ListAdapter.MangaViewHolder>() {
    var onItemClick: ((Manga) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaViewHolder {
        val binding = ItemMangaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MangaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MangaViewHolder, position: Int) {
        holder.bind(mangas[position], onItemClick)
    }

    override fun getItemCount(): Int = mangas.size

    fun setItems(items: List<Manga>) {
        mangas.clear()
        mangas.addAll(items)
        notifyDataSetChanged()
    }

    class MangaViewHolder(
        private val binding: ItemMangaBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            manga: Manga,
            onItemClick: ((Manga) -> Unit)?
        ) = with(binding) {
            val context: Context = itemView.context
            textViewMangaTitle.text = manga.title
            textViewMangaScore.text = context.getString(R.string.list_fragment_item_score, manga.score)
            imageViewCover.load(manga.cover)
            itemView.setOnClickListener {
                onItemClick?.invoke(manga)
            }
        }
    }
}