package com.ludovic.vimont.mangareader.screens.reader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.ludovic.vimont.mangareader.databinding.FragmentPageBinding
import com.ludovic.vimont.mangareader.entities.ChapterPage

class ReaderPageFragment : Fragment() {
    companion object {
        private const val KEY_IMAGE_URL = "KEY_IMAGE_URL"

        fun newInstance(chapterPage: ChapterPage): ReaderPageFragment {
            val readerPageFragment = ReaderPageFragment()
            val args = Bundle()
            args.putString(KEY_IMAGE_URL, chapterPage.getURL())
            readerPageFragment.arguments = args
            return readerPageFragment
        }
    }
    private lateinit var binding: FragmentPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(binding) {
            context?.let {
                val imageURL: String = requireArguments().getString(KEY_IMAGE_URL, "")
                Glide.with(it)
                    .load(imageURL)
                    .into(imageViewCurrentPage)
            }
        }
    }
}