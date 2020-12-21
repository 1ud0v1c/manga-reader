package com.ludovic.vimont.mangareader.screens.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ludovic.vimont.mangareader.R
import com.ludovic.vimont.mangareader.databinding.FragmentDetailBinding
import com.ludovic.vimont.mangareader.entities.LinkChapter
import com.ludovic.vimont.mangareader.entities.ReadingPage

import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment: Fragment() {
    private var isFavorite: Boolean = false
    private val detailGenreAdapter = DetailGenreAdapter(ArrayList())
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
        setupActions()
        configureRecyclerViews()
        configureViewModel()
    }

    private fun setupActions() {
        with(binding) {
            imageViewDownload.setOnClickListener {
                viewModel.downloadChapters(detailChapterAdapter.getItems())
            }
            // TODO: add favorite action
            imageViewFavorite.setOnClickListener {
                if (isFavorite) {
                    imageViewFavorite.setImageResource(R.drawable.ic_favorite_empty)
                } else {
                    imageViewFavorite.setImageResource(R.drawable.ic_favorite_full)
                }
                isFavorite = !isFavorite
            }
        }
    }

    private fun configureRecyclerViews() {
        val recyclerViewGenres: RecyclerView = binding.recyclerViewGenres
        recyclerViewGenres.adapter = detailGenreAdapter
        recyclerViewGenres.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        val recyclerViewChapters: RecyclerView = binding.recyclerViewChapters
        recyclerViewChapters.adapter = detailChapterAdapter
        recyclerViewChapters.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        detailChapterAdapter.onItemClick = { linkChapter: LinkChapter ->
            val action: NavDirections = DetailFragmentDirections.actionDetailFragmentToReaderFragment(linkChapter.link)
            findNavController().navigate(action)
        }
    }

    private fun configureViewModel() {
        viewModel.fetchMangaContent(detailFragmentArgs.mangaId)
        viewModel.readingPage.observe(viewLifecycleOwner, { page: ReadingPage ->
            with(binding) {
                textViewMangaName.text = page.name
                textViewMangaAuthor.text = page.author
                context?.let {
                    Glide.with(it)
                        .load(detailFragmentArgs.mangaCover)
                        .into(imageViewMangaCover)
                    textViewNumberOfChapters.text = it.getString(R.string.detail_fragment_number_of_chapters, page.chapters.size)
                }
                val genres = if (page.genres.size >= 3) page.genres.subList(0, 3) else page.genres
                detailGenreAdapter.setItems(genres)
                detailChapterAdapter.setItems(page.chapters)
            }
        })
    }
}