package com.ludovic.vimont.mangareader.ui

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * RecyclerView which load data after reaching a certain offset
 * @see https://guides.codepath.com/android/endless-scrolling-with-adapterviews-and-recyclerview
 */
abstract class EndlessRecyclerViewScrollListener(private var layoutManager: RecyclerView.LayoutManager,
                                                 private val visibleThreshold: Int = 5): RecyclerView.OnScrollListener() {
    private var isLoading = true
    private var currentPage = 0
    private val startingPageIndex = 0
    private var previousTotalItemCount = 0

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (index: Int in lastVisibleItemPositions.indices) {
            if (index == 0) {
                maxSize = lastVisibleItemPositions[index]
            } else if (lastVisibleItemPositions[index] > maxSize) {
                maxSize = lastVisibleItemPositions[index]
            }
        }
        return maxSize
    }

    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        var lastVisibleItemPosition = 0
        val totalItemCount = layoutManager.itemCount
        when (layoutManager) {
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions: IntArray =
                    (layoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
                lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
            }
            is GridLayoutManager -> {
                lastVisibleItemPosition =
                    (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
            }
            is LinearLayoutManager -> {
                lastVisibleItemPosition =
                    (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            }
        }

        if (totalItemCount < previousTotalItemCount) {
            currentPage = startingPageIndex
            previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                isLoading = true
            }
        }

        if (isLoading && totalItemCount > previousTotalItemCount) {
            isLoading = false
            previousTotalItemCount = totalItemCount
        }

        if (!isLoading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            onLoadMore(currentPage)
            isLoading = true
        }
    }

    fun getCurrentPage(): Int {
        return currentPage
    }

    fun resetState() {
        currentPage = startingPageIndex
        previousTotalItemCount = 0
        isLoading = true
    }

    abstract fun onLoadMore(currentPage: Int)
}