package com.ludovic.vimont.mangareader.screens.reader

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ludovic.vimont.mangareader.entities.ChapterPage

class ReaderPageAdapter(private val pages: ArrayList<ChapterPage>, fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return pages.size
    }

    override fun createFragment(position: Int): Fragment {
        return ReaderPageFragment.newInstance(pages[position])
    }

    fun setItems(items: List<ChapterPage>) {
        pages.clear()
        pages.addAll(items)
        notifyDataSetChanged()
    }
}