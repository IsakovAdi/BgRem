package com.example.bgrem.presentation.fragments

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
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
                    DownloadTask.downloadImage(downloadUrl, requireContext())
                    true
                }
                else -> false
            }
        }
    }

//    fun downloadImage() {
//        directory = File(Environment.DIRECTORY_PICTURES)
//        if (directory?.exists()!!) {
//            directory?.mkdirs()
//        }
//        createDownloadManager()
//    }
//
//    private fun createDownloadManager() {
//        val downloadManager =
//            requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//        val downloadUri = Uri.parse(args.downloadUrl)
//        val request = DownloadManager.Request(downloadUri).apply {
//            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
//                .setAllowedOverRoaming(false)
//                .setTitle(args.downloadUrl.substring(args.downloadUrl.lastIndexOf("/") + 1))
//                .setDescription("")
//                .setDestinationInExternalPublicDir(
//                    directory.toString(),
//                    args.downloadUrl.substring(args.downloadUrl.lastIndexOf("/") + 1)
//                )
//        }
//        startDownload(downloadManager, request)
//    }
//
//
//    @SuppressLint("Range")
//    private fun startDownload(
//        downloadManager: DownloadManager,
//        request: DownloadManager.Request
//    ) {
//        val downloadId = downloadManager.enqueue(request)
//        val query = DownloadManager.Query().setFilterById(downloadId)
//        Thread(Runnable {
//            var downloading = true
//            while (downloading) {
//                val cursor: Cursor = downloadManager.query(query)
//                cursor.moveToFirst()
//                if (cursor.getInt(
//                        cursor.getColumnIndex(
//                            DownloadManager.COLUMN_STATUS
//                        )
//                    ) == DownloadManager.STATUS_SUCCESSFUL
//                ) {
//                    downloading = false
//                }
//                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
//                statusMessage(status)
//                cursor.close()
//            }
//        }).start()
//    }
//
//    private fun statusMessage(status: Int) {
//        if (status == DownloadManager.STATUS_SUCCESSFUL) {
//            var msg =
//                "Image downloaded successfully in ${directory}" + File.separator + args.downloadUrl.substring(
//                    args.downloadUrl.lastIndexOf("/") + 1
//                )
//            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
//
//        } else if (status == DownloadManager.STATUS_FAILED) {
//            Toast.makeText(
//                requireContext(),
//                "Download has been failed, please try again",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//    }

    companion object {
        const val READY_FRAGMENT = "READY_FRAGMENT"
        private var downloadUrl = String()
    }
}