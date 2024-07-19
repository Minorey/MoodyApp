package com.example.moodyapp.downloader

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri

class AndroidDownloader(
    val context: Context,
) : Downloader {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadFile(url: String,title:String): Long {
        val request = DownloadManager.Request(url.toUri()).setMimeType("image/jpeg")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
            .setTitle("$title.jpg")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "Moody/$title.jpg")
        return downloadManager.enqueue(request)
    }
}