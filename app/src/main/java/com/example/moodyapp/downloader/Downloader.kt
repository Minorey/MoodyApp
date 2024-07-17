package com.example.moodyapp.downloader

interface Downloader {

    fun downloadFile(url: String,title:String): Long

}