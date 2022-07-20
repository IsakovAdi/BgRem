package com.example.bgrem.presentation.fragments

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bgrem.R
import com.example.bgrem.databinding.FragmentNotRemovedBinding
import com.example.bgrem.databinding.FragmentReadyBinding
import com.example.bgrem.presentation.DownloadTask
import com.example.bgrem.presentation.fragments.selectBgFragment.SelectBgFragment
import com.example.bgrem.presentation.fragments.selectBgFragment.SelectBgFragmentArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import java.io.File

class ReadyFragment : Fragment() {
    private val args by navArgs<ReadyFragmentArgs>()
    private var directory: File? = null

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
                    Log.d("downloadUrl", downloadUrl)
                    DownloadTask.downloadImage(downloadUrl, requireContext())
                    true
                }
                else -> false
            }
        }
    }

    companion object {
        const val READY_FRAGMENT = "READY_FRAGMENT"
        private var downloadUrl = String()
    }
}