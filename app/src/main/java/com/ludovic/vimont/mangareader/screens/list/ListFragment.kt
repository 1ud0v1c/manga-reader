package com.ludovic.vimont.mangareader.screens.list

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ludovic.vimont.mangareader.R
import com.ludovic.vimont.mangareader.api.JikanAPI
import com.ludovic.vimont.mangareader.databinding.FragmentListBinding
import com.ludovic.vimont.mangareader.entities.Manga
import com.ludovic.vimont.mangareader.ui.EndlessRecyclerViewScrollListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment: Fragment() {
    private val viewModel: ListViewModel by viewModel()

    private val listAdapter = ListAdapter(ArrayList())
    private lateinit var endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener

    private var _binding: FragmentListBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        configureViewModel()
    }

    private fun configureRecyclerView() {
        val recyclerView: RecyclerView = binding.recyclerViewMangas
        recyclerView.adapter = listAdapter
        listAdapter.onItemClick = { manga: Manga ->
            val action: NavDirections = ListFragmentDirections.actionListFragmentToDetailFragment(manga.id, manga.cover)
            findNavController().navigate(action)
        }
        endlessRecyclerViewScrollListener = object: EndlessRecyclerViewScrollListener(
            layoutManager = requireNotNull(recyclerView.layoutManager)
        ) {
            override fun onLoadMore(currentPage: Int) = viewModel.fetchMangas(currentPage + 1)
        }
        recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener)
    }

    private fun configureViewModel() {
        viewModel.fetchMangas()
        viewModel.mangas.observe(viewLifecycleOwner) { mangas: List<Manga> ->
            listAdapter.setItems(mangas)
            endlessRecyclerViewScrollListener.currentPage = mangas.size / JikanAPI.ITEMS_PER_PAGE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.action_bar_search_action)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                searchView.onActionViewCollapsed()
                endlessRecyclerViewScrollListener.isLoading = false
            } else {
                endlessRecyclerViewScrollListener.isLoading = true
            }
        }
        searchView.setOnCloseListener {
            true
        }

        val searchPlate = searchView.findViewById(androidx.appcompat.R.id.search_src_text) as EditText
        searchPlate.hint = getString(R.string.action_bar_menu_search_title)
        val searchPlateView: View = searchView.findViewById(androidx.appcompat.R.id.search_plate)
        searchPlateView.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.transparent))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val search = newText.orEmpty()
                listAdapter.filter.filter(search)
                return false
            }
        })

        val searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        return super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerViewMangas.adapter = null
        _binding = null
    }
}