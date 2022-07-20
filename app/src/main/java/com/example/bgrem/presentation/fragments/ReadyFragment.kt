package com.example.bgrem.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bgrem.R
import com.example.bgrem.databinding.FragmentReadyBinding
import com.example.bgrem.domain.models.TaskStatus
import com.example.bgrem.presentation.DownloadTask
import com.squareup.picasso.Picasso

class ReadyFragment : Fragment() {
    private val args by navArgs<ReadyFragmentArgs>()
    private var isDownload = false
    private val binding: FragmentReadyBinding by lazy {
        FragmentReadyBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (args.direction == FinalLoadingFragment.FINAL_LOADING_FRAGMENT) {
            Picasso.get().load(args.downloadUrl).into(binding.mainImage)
            downloadUrl = args.downloadUrl
        }
        binding.bottomNavView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.save -> {
                    isDownload = true
                    DownloadTask.downloadImage(requireContext(), downloadUrl)
                    true
                }
                else -> false
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!isDownload) {
                        Toast.makeText(
                            requireContext(),
                            "Вы не сохранили фото",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    findNavController().navigate(
                        ReadyFragmentDirections.actionReadyFragmentToMainFragment2()
                    )
                }
            })
    }


    companion object {
        const val READY_FRAGMENT = "READY_FRAGMENT"
        private var downloadUrl = String()
    }
}