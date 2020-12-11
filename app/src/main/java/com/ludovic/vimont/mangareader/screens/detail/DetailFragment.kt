package com.ludovic.vimont.mangareader.screens.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ludovic.vimont.mangareader.databinding.FragmentDetailBinding
import com.ludovic.vimont.mangareader.entities.Chapter

import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment: Fragment() {
    private val detailChapterAdapter = DetailChapterAdapter(ArrayList())
    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModel()
    private val detailFragmentArgs: DetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configureRecyclerView()
        configureViewModel()
    }

    private fun configureRecyclerView() {
        val recyclerView: RecyclerView = binding.recyclerViewChapters
        recyclerView.adapter = detailChapterAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    }

    private fun configureViewModel() {
        viewModel.downloadContent(detailFragmentArgs.mangaTitle)
        viewModel.chapters.observe(viewLifecycleOwner, { chapters: List<Chapter> ->
            detailChapterAdapter.setItems(chapters)
        })
    }
}