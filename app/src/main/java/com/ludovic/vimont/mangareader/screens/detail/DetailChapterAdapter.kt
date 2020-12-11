package com.ludovic.vimont.mangareader.screens.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ludovic.vimont.mangareader.R
import com.ludovic.vimont.mangareader.entities.Chapter

class DetailChapterAdapter(private val chapters: ArrayList<Chapter>): RecyclerView.Adapter<DetailChapterAdapter.ChapterViewHolder>() {
    var onItemClick: ((Chapter) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_chapter, parent, false)
        return ChapterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        val chapter: Chapter = chapters[position]
        with(holder) {
            val context: Context = itemView.context
            textViewChapterTitle.text = chapter.title
            textViewChapterDate.text = context.getString(R.string.detail_fragment_item_date, chapter.addedDate)
            itemView.setOnClickListener {
                onItemClick?.invoke(chapter)
            }
        }
    }

    override fun getItemCount(): Int {
        return chapters.size
    }

    fun setItems(items: List<Chapter>) {
        chapters.clear()
        chapters.addAll(items)
        notifyDataSetChanged()
    }

    inner class ChapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textViewChapterTitle: TextView = itemView.findViewById(R.id.text_view_chapter_title)
        val textViewChapterDate: TextView = itemView.findViewById(R.id.text_view_chapter_date)
    }
}