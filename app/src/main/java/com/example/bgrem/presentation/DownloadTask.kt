package com.example.bgrem.presentation

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import java.io.File

object DownloadTask {
    private var fileUrl = String()
    private lateinit var directory: File

    fun downloadImage(url: String, context: Context) {
        fileUrl = url
        directory = File(Environment.DIRECTORY_PICTURES)
        if (!directory.exists()) {
            directory.mkdirs()
        }
        createDownloadManager(context)
    }

    private fun createDownloadManager(context: Context) {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadUri = Uri.parse(fileUrl)
        val request = DownloadManager.Request(downloadUri).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(fileUrl.substring(fileUrl.lastIndexOf("/") + 1))
                .setDescription("")
                .setDestinationInExternalPublicDir(
                    directory.toString(),
                    fileUrl.substring(fileUrl.lastIndexOf("/") + 1)
                )
        }
        startDownload(context, downloadManager, request)
    }

    @SuppressLint("Range")
    private fun startDownload(
        context: Context,
        downloadManager: DownloadManager,
        request: DownloadManager.Request
    ) {
        val downloadId = downloadManager.enqueue(request)
        val query = DownloadManager.Query().setFilterById(downloadId)
        Thread(Runnable {
            var downloading = true
            while (downloading) {
                val cursor: Cursor = downloadManager.query(query)
                cursor.moveToFirst()
                if (cursor.getInt(
                        cursor.getColumnIndex(
                            DownloadManager.COLUMN_STATUS
                        )
                    ) == DownloadManager.STATUS_SUCCESSFUL
                ) {
                    downloading = false
                }
                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                statusMessage(context, status)
                cursor.close()
            }
        }).start()
    }

    private fun statusMessage(context: Context,status: Int) {
        if (status == DownloadManager.STATUS_SUCCESSFUL) {
            var msg =
                "Image downloaded successfully in $directory" + File.separator + fileUrl.substring(
                    fileUrl.lastIndexOf("/") + 1
                )
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

        } else if (status == DownloadManager.STATUS_FAILED) {
            Toast.makeText(
                context,
                "Download has been failed, please try again",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}