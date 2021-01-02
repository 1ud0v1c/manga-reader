package com.ludovic.vimont.mangareader.screens.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ludovic.vimont.mangareader.databinding.FragmentListBinding
import com.ludovic.vimont.mangareader.entities.Manga
import com.ludovic.vimont.mangareader.ui.EndlessRecyclerViewScrollListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment: Fragment() {
    private val listAdapter = ListAdapter(ArrayList())
    private lateinit var binding: FragmentListBinding
    private val viewModel: ListViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configureRecyclerView()
        configureViewModel()
    }

    private fun configureRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        val recyclerView: RecyclerView = binding.recyclerViewMangas
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = linearLayoutManager
        listAdapter.onItemClick = { manga: Manga ->
            val action: NavDirections = ListFragmentDirections.actionListFragmentToDetailFragment(manga.id, manga.cover)
            findNavController().navigate(action)
        }
        val endlessRecyclerViewScrollListener = object: EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(currentPage: Int) {
                viewModel.fetchMangas(currentPage+1)
            }
        }
        recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener)
    }

    private fun configureViewModel() {
        viewModel.fetchMangas()
        viewModel.mangas.observe(viewLifecycleOwner, { mangas: List<Manga> ->
            listAdapter.setItems(mangas)
        })
    }
}