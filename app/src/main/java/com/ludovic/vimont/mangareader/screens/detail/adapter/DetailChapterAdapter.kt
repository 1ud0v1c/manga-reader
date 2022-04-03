package com.ludovic.vimont.mangareader.screens.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ludovic.vimont.mangareader.R
import com.ludovic.vimont.mangareader.databinding.ItemChapterBinding
import com.ludovic.vimont.mangareader.entities.LinkChapter

class DetailChapterAdapter(
    private val linkChapters: ArrayList<LinkChapter>
): RecyclerView.Adapter<DetailChapterAdapter.ChapterViewHolder>() {
    var onItemClick: ((LinkChapter) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        val binding = ItemChapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        holder.bind(linkChapters[position], onItemClick)
    }

    override fun getItemCount(): Int = linkChapters.size

    fun getItems(): ArrayList<LinkChapter> = linkChapters

    fun setItems(items: List<LinkChapter>) {
        linkChapters.clear()
        linkChapters.addAll(items)
        notifyDataSetChanged()
    }

    class ChapterViewHolder(
        private val binding: ItemChapterBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(
            linkChapter: LinkChapter,
            onItemClick: ((LinkChapter) -> Unit)?
        ) = with(binding) {
            val context: Context = itemView.context
            textViewChapterTitle.text = linkChapter.title
            textViewChapterDate.text = context.getString(
                R.string.detail_fragment_item_date, linkChapter.addedDate
            )
            itemView.setOnClickListener {
                onItemClick?.invoke(linkChapter)
            }
        }
    }
}