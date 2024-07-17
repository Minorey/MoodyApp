package com.example.moodyapp.downloader

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class DownloadCompletedReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if(intent?.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE){
            val id=intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1L)
            if(id!=-1L){
                println("Download Completed with id: $id")
            }
        }
    }


}