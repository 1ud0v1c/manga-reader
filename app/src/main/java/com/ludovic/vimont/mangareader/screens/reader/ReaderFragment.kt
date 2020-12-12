package com.ludovic.vimont.mangareader.screens.reader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ludovic.vimont.mangareader.databinding.FragmentReaderBinding
import com.ludovic.vimont.mangareader.entities.Chapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReaderFragment: Fragment() {
    private lateinit var binding: FragmentReaderBinding
    private val viewModel: ReaderViewModel by viewModel()
    private val readFragmentArgs: ReaderFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentReaderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configureViewModel()
    }

    private fun configureViewModel() {
        viewModel.loadChapter(readFragmentArgs.chapterLink)
        viewModel.chapter.observe(viewLifecycleOwner, { chapter: Chapter ->
            // TODO:
        })
    }
}