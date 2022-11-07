package com.example.media.ui

import android.media.MediaPlayer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.media.domain.entity.TrackEntity

class MediaLifecycleObserver(
    private val onFinishTrackListener: () -> Unit
) : LifecycleEventObserver {

    var mediaPlayer: MediaPlayer? = null
    var lastTrack: String? = null

    fun play() {
        mediaPlayer?.setOnPreparedListener {
            it.start()
        }
        mediaPlayer?.prepareAsync()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_PAUSE -> mediaPlayer?.pause()
            Lifecycle.Event.ON_STOP -> {
                mediaPlayer?.release()
                mediaPlayer = null
            }
            Lifecycle.Event.ON_DESTROY -> source.lifecycle.removeObserver(this)
            Lifecycle.Event.ON_START -> {
                mediaPlayer = MediaPlayer()
                mediaPlayer?.setOnCompletionListener {
                    onFinishTrackListener.invoke()
                }
            }
            Lifecycle.Event.ON_RESUME -> {
                lastTrack?.let {
                    mediaPlayer?.reset()
                    mediaPlayer?.setDataSource(it)
                    play()
                }
            }
            else -> Unit
        }
    }
}