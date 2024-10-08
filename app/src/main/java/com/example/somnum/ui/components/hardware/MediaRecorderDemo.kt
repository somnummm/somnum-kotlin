package com.example.somnum.ui.components.hardware

import android.media.MediaRecorder
import android.os.Handler
import android.util.Log
import java.io.File
import java.io.IOException
import kotlin.math.log10


/**
 * amr audio processing
 */
class MediaRecorderDemo {
    private val TAG = "MediaRecord"
    private var mMediaRecorder: MediaRecorder? = null
    private var filePath: String

    constructor() {
        this.filePath = "/dev/null"
    }

    constructor(file: File) {
        this.filePath = file.absolutePath
    }

    private var startTime: Long = 0
    private var endTime: Long = 0

    /**
     * Start recording using amr format
     *
     * Recording files
     * @return
     */
    fun startRecord() {
        // start recording
        /* ①Initial: instantiate the MediaRecorder object */
        if (mMediaRecorder == null) mMediaRecorder = MediaRecorder()
        try {
            /* ②setAudioSource/setVedioSource */
            mMediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC) // Set the microphone
            /* ②Set the encoding of the audio file: AAC/AMR_NB/AMR_MB/Default sound (waveform) sampling */
            mMediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT)
            /*
* ②Set the format of the output file: THREE_GPP/MPEG-4/RAW_AMR/Default THREE_GPP (3gp format
*. H263 video/ARM audio encoding), MPEG-4, RAW_AMR (only supports audio and the audio encoding requirement is AMR_NB)
*/
            mMediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            /* ③Preparation */
            mMediaRecorder!!.setOutputFile(filePath)
            mMediaRecorder!!.setMaxDuration(MAX_LENGTH)
            mMediaRecorder!!.prepare()
            /* ④Start */
            mMediaRecorder!!.start()
            // AudioRecord audioRecord.
            /* Get start time* */
            startTime = System.currentTimeMillis()
            updateMicStatus()
            Log.i("ACTION_START", "startTime$startTime")
        } catch (e: IllegalStateException) {
            Log.i(
                TAG,
                "call startAmr(File mRecAudioFile) failed!"
                        + e.message
            )
        } catch (e: IOException) {
            Log.i(
                TAG,
                ("call startAmr(File mRecAudioFile) failed!"
                        + e.message)
            )
        }
    }

    /**
     * Stop recording
     *
     */
    fun stopRecord(): Long {
        if (mMediaRecorder == null) return 0L
        endTime = System.currentTimeMillis()
        Log.i("ACTION_END", "endTime$endTime")
        mMediaRecorder!!.stop()
        mMediaRecorder!!.reset()
        mMediaRecorder!!.release()
        mMediaRecorder = null
        Log.i("ACTION_LENGTH", "Time" + (endTime - startTime))
        return endTime - startTime
    }

    private val mHandler = Handler()
    private val mUpdateMicStatusTimer: Runnable = Runnable { updateMicStatus() }

    /**
     * Update microphone status
     *
     */
    private val BASE = 1
    private val SPACE = 100 //Interval sampling time

    private fun updateMicStatus() {
        if (mMediaRecorder != null) {
            val ratio = mMediaRecorder!!.maxAmplitude.toDouble() / BASE
            var db = 0.0 // decibel
            if (ratio > 1) db = 20 * log10(ratio)
            Log.d(TAG, "dB value:$db")
            mHandler.postDelayed(mUpdateMicStatusTimer, SPACE.toLong())
        }
    }

    companion object {
        val MAX_LENGTH: Int = 1000 * 60 * 10 // Maximum recording duration 1000*60*10;
    }
}