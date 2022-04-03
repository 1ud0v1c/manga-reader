package com.ludovic.vimont.mangareader.screens.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ludovic.vimont.mangareader.databinding.FragmentFavoriteBinding
import com.ludovic.vimont.mangareader.entities.Manga
import com.ludovic.vimont.mangareader.screens.list.ListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment: Fragment() {
    private val listAdapter = ListAdapter(ArrayList())
    private val viewModel: FavoriteViewModel by viewModel()

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        configureViewModel()
    }

    private fun configureRecyclerView() {
        binding.recyclerViewFavorites.adapter = listAdapter
        listAdapter.onItemClick = { manga: Manga ->
            val action: NavDirections = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(manga.id, manga.cover)
            findNavController().navigate(action)
        }
    }

    private fun configureViewModel() {
        viewModel.loadFavorites()
        viewModel.mangas.observe(viewLifecycleOwner) { mangas: List<Manga> ->
            listAdapter.setItems(mangas)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerViewFavorites.adapter = null
        _binding = null
    }
}