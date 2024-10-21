package com.example.atest

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import kotlin.math.log10

class AudioRecordDemo {
    var mAudioRecord: AudioRecord? = null
    var isGetVoiceRun: Boolean = false
    var mLock: Any = Any()

    val noiseLevel: Unit
        get() {
            if (isGetVoiceRun) {
                Log.e(TAG, "Still recording it")
                return
            }
            mAudioRecord = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT,
                AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE
            )
            if (mAudioRecord == null) {
                Log.e("sound", "mAudioRecord initialization failed")
            }
            isGetVoiceRun = true

            Thread {
                mAudioRecord!!.startRecording()
                val buffer = ShortArray(BUFFER_SIZE)
                while (isGetVoiceRun) {
                    //r is the actual length of data read, generally speaking, r will be less than buffersize
                    val r = mAudioRecord!!.read(buffer, 0, BUFFER_SIZE)
                    var v: Long = 0
                    // Take out the contents of buffer. Do the sum of squares
                    for (i in buffer.indices) {
                        v += (buffer[i] * buffer[i]).toLong()
                    }
                    // Divide the sum of squares by the total length of the data to get the volume.
                    val mean = v / r.toDouble()
                    val volume = 10 * log10(mean)
                    Log.d(TAG, "Decibel value:$volume")
                    // About ten times a second
                    synchronized(mLock) {
                        try {
                            (mLock as Object).wait(100)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                }
                mAudioRecord!!.stop()
                mAudioRecord!!.release()
                mAudioRecord = null
            }.start()
        }

    companion object {
        private const val TAG = "AudioRecord"
        const val SAMPLE_RATE_IN_HZ: Int = 8000
        val BUFFER_SIZE: Int = AudioRecord.getMinBufferSize(
            SAMPLE_RATE_IN_HZ,
            AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT
        )
    }
}