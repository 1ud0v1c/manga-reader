package com.ludovic.vimont.mangareader.screens.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.ludovic.vimont.mangareader.R
import com.ludovic.vimont.mangareader.databinding.FragmentDetailBinding
import com.ludovic.vimont.mangareader.entities.LinkChapter
import com.ludovic.vimont.mangareader.entities.ReadingPage
import com.ludovic.vimont.mangareader.entities.SortOrder
import com.ludovic.vimont.mangareader.screens.detail.adapter.DetailChapterAdapter
import com.ludovic.vimont.mangareader.screens.detail.adapter.DetailGenreAdapter

import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment: Fragment() {
    private var isFavorite: Boolean = false
    private var readingPage: ReadingPage? = null
    private val detailGenreAdapter = DetailGenreAdapter(ArrayList())
    private val detailChapterAdapter = DetailChapterAdapter(ArrayList())
    private val viewModel: DetailViewModel by viewModel()
    private val detailFragmentArgs: DetailFragmentArgs by navArgs()

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActions()
        configureRecyclerViews()
        configureViewModel()
    }

    private fun setupActions() {
        with(binding) {
            imageViewDownload.setOnClickListener {
                viewModel.downloadChapters(detailChapterAdapter.getItems())
            }
            imageViewFavorite.setOnClickListener {
                if (isFavorite) {
                    imageViewFavorite.setImageResource(R.drawable.ic_favorite_empty)
                    readingPage?.let {
                        viewModel.removeFromFavorite(it.id)
                        displaySnackBar(binding.root, getString(R.string.detail_fragment_remove_from_favorite))
                    }
                } else {
                    imageViewFavorite.setImageResource(R.drawable.ic_favorite_full)
                    readingPage?.let {
                        viewModel.addToFavorite(it.id)
                        displaySnackBar(binding.root, getString(R.string.detail_fragment_add_to_favorite))
                    }
                }
                isFavorite = !isFavorite
            }
            imageButtonSortChapters.setOnClickListener {
                viewModel.onSortClicked()
            }
        }
    }

    private fun displaySnackBar(view: View, text: String, duration: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(view, text, duration).show()
    }

    private fun configureRecyclerViews() {
        binding.recyclerViewGenres.adapter = detailGenreAdapter
        binding.recyclerViewChapters.adapter = detailChapterAdapter
        detailChapterAdapter.onItemClick = { linkChapter: LinkChapter ->
            val action: NavDirections = DetailFragmentDirections.actionDetailFragmentToReaderFragment(linkChapter.link)
            findNavController().navigate(action)
        }
    }

    private fun configureViewModel() {
        viewModel.fetchMangaName(detailFragmentArgs.mangaId)
        viewModel.mangaName.observe(viewLifecycleOwner) { title: String ->
            activity?.let {
                (activity as? AppCompatActivity)?.supportActionBar?.title =
                    getString(R.string.detail_fragment_title, title)
            }
        }

        viewModel.fetchMangaContent(detailFragmentArgs.mangaId)
        viewModel.readingPage.observe(viewLifecycleOwner) { page: ReadingPage ->
            with(binding) {
                textViewMangaName.text = page.name
                textViewMangaAuthor.text = page.author
                imageViewMangaCover.load(detailFragmentArgs.mangaCover)
                textViewNumberOfChapters.text = getString(
                    R.string.detail_fragment_number_of_chapters, page.chapters.size
                )
                if (page.isFavorite) {
                    imageViewFavorite.setImageResource(R.drawable.ic_favorite_full)
                    isFavorite = page.isFavorite
                }
                val genres = if (page.genres.size >= 3) {
                    page.genres.subList(0, 3)
                } else {
                    page.genres
                }
                detailGenreAdapter.setItems(genres)
                detailChapterAdapter.setItems(page.chapters)
                readingPage = page
            }
        }
        viewModel.sortOrder.observe(viewLifecycleOwner, ::handleSortOrder)
    }

    private fun handleSortOrder(sortOrder: SortOrder) {
        detailChapterAdapter.sort(sortOrder)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerViewChapters.adapter = null
        binding.recyclerViewGenres.adapter = null
        _binding = null
    }
}