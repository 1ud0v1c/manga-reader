package com.ludovic.vimont.mangareader.screens.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ludovic.vimont.mangareader.R
import com.ludovic.vimont.mangareader.entities.LinkChapter

class DetailChapterAdapter(private val linkChapters: ArrayList<LinkChapter>): RecyclerView.Adapter<DetailChapterAdapter.ChapterViewHolder>() {
    var onItemClick: ((LinkChapter) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_chapter, parent, false)
        return ChapterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        val linkChapter: LinkChapter = linkChapters[position]
        with(holder) {
            val context: Context = itemView.context
            textViewChapterTitle.text = linkChapter.title
            textViewChapterDate.text = context.getString(R.string.detail_fragment_item_date, linkChapter.addedDate)
            itemView.setOnClickListener {
                onItemClick?.invoke(linkChapter)
            }
        }
    }

    override fun getItemCount(): Int {
        return linkChapters.size
    }

    fun getItems(): ArrayList<LinkChapter> {
        return linkChapters
    }

    fun setItems(items: List<LinkChapter>) {
        linkChapters.clear()
        linkChapters.addAll(items)
        notifyDataSetChanged()
    }

    inner class ChapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textViewChapterTitle: TextView = itemView.findViewById(R.id.text_view_chapter_title)
        val textViewChapterDate: TextView = itemView.findViewById(R.id.text_view_chapter_date)
    }
}