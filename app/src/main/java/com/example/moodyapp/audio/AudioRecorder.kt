package com.example.moodyapp.audio

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import java.io.File
import java.io.FileOutputStream

//clase para grabar
class AudioRecorder (private val context: Context){
    private var recorder: MediaRecorder? = null
    private fun create(): MediaRecorder {
        //la version sdk de android
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            MediaRecorder(context)
        }else{
            MediaRecorder()
        }
    }
    //funcion para grabar
    fun record(file: File){
        //inicializa el recorder
        create().apply {
            //configura el audio
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(FileOutputStream(file).fd)
            prepare()
            start()
            //guarda el recorder
            recorder = this
        }
    }
    //funcion para parar de grabar :3
    fun stop(){
        recorder?.stop()
        recorder?.release()
        recorder = null
    }
}