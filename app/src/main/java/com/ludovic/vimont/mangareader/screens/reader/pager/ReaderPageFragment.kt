package com.ludovic.vimont.mangareader.screens.reader.pager

import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.ludovic.vimont.mangareader.api.MangaAPI
import com.ludovic.vimont.mangareader.databinding.FragmentPageBinding
import com.ludovic.vimont.mangareader.entities.ChapterPage

class ReaderPageFragment: Fragment() {
    companion object {
        private const val KEY_IMAGE_URL = "KEY_IMAGE_URL"

        fun newInstance(chapterPage: ChapterPage): ReaderPageFragment {
            val readerPageFragment = ReaderPageFragment()
            val args = Bundle()
            args.putString(KEY_IMAGE_URL, chapterPage.link)
            readerPageFragment.arguments = args
            return readerPageFragment
        }
    }
    private var _binding: FragmentPageBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            context?.let {
                val imageURL: String = requireArguments().getString(KEY_IMAGE_URL, "")
                photoViewCurrentPage.load(imageURL) {
                    for ((key, value) in MangaAPI.headers) {
                        addHeader(key, value)
                    }
                }
            }
            with(photoViewCurrentPage) {
                setZoomTransitionDuration(300)
                setScaleLevels(1f, 2f, 5f)
                setOnDoubleTapListener(
                    object : GestureDetector.SimpleOnGestureListener() {
                        override fun onDoubleTap(e: MotionEvent): Boolean {
                            if (scale > 1f) {
                                setScale(1f, e.x, e.y, true)
                            } else {
                                setScale(2f, e.x, e.y, true)
                            }
                            return true
                        }
                    }
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}