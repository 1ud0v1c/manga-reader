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
    private var previousPageScrollState: Int = -1
    private lateinit var pageAdapter: ReaderPageAdapter
    private val viewModel: ReaderViewModel by viewModel()
    private val readFragmentArgs: ReaderFragmentArgs by navArgs()

    private var _binding: FragmentReaderBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentReaderBinding.inflate(inflater, container, false)
        pageAdapter = ReaderPageAdapter(ArrayList(), this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViewModel()
    }

    private fun configureViewModel() {
        viewModel.loadChapter(readFragmentArgs.chapterLink)
        viewModel.chapter.observe(viewLifecycleOwner) { chapter: Chapter ->
            activity?.let {
                (activity as? AppCompatActivity)?.supportActionBar?.title =
                    getString(R.string.reader_fragment_title, chapter.currentChapter.toString())
            }
            configureViewPager()
            pageAdapter.setItems(chapter.images)
        }
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

                    override fun onPageScrollStateChanged(newState: Int) {
                        super.onPageScrollStateChanged(newState)
                        if (previousPageScrollState == ViewPager2.SCROLL_STATE_DRAGGING
                            && newState == ViewPager2.SCROLL_STATE_IDLE) {
                            if (viewPagerChapter.currentItem == 0) {
                                viewModel.chapter.value?.let { chapter: Chapter ->
                                    if (chapter.previous.isNotEmpty()) {
                                        viewModel.loadChapter(chapter.previous)
                                    }
                                }
                            }
                            if (viewPagerChapter.currentItem == pageAdapter.itemCount - 1) {
                                viewModel.chapter.value?.let { chapter: Chapter ->
                                    if (chapter.next.isNotEmpty()) {
                                        viewModel.loadChapter(chapter.next)
                                    }
                                }
                            }
                        }
                        previousPageScrollState = newState
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}