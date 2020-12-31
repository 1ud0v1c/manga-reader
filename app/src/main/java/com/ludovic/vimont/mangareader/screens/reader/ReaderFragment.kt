package com.ludovic.vimont.mangareader.screens.reader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.ludovic.vimont.mangareader.R
import com.ludovic.vimont.mangareader.databinding.FragmentReaderBinding
import com.ludovic.vimont.mangareader.entities.Chapter
import com.ludovic.vimont.mangareader.screens.reader.pager.ReaderPageAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReaderFragment : Fragment() {
    private lateinit var pageAdapter: ReaderPageAdapter
    private lateinit var binding: FragmentReaderBinding
    private val viewModel: ReaderViewModel by viewModel()
    private val readFragmentArgs: ReaderFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReaderBinding.inflate(inflater, container, false)
        activity?.let {
            (activity as? AppCompatActivity)?.supportActionBar?.title =
                getString(R.string.reader_fragment_title, readFragmentArgs.chapterTitle)
        }
        pageAdapter = ReaderPageAdapter(ArrayList(), this)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configureViewPager()
        configureViewModel()
    }

    private fun configureViewPager() {
        with(binding) {
            viewPagerChapter.adapter = pageAdapter
            context?.let {
                viewPagerChapter.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        textViewChapterProgression.text = getProgression(position + 1)
                    }
                })
            }
        }
    }

    private fun getProgression(currentPage: Int = 1): String {
        context?.let {
            return it.getString(
                R.string.reader_fragment_progression,
                currentPage, pageAdapter.itemCount
            )
        }
        return ""
    }

    private fun configureViewModel() {
        viewModel.loadChapter(readFragmentArgs.chapterLink)
        viewModel.chapter.observe(viewLifecycleOwner, { chapter: Chapter ->
            pageAdapter.setItems(chapter.images)
        })
    }
}