package com.example.media.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.media.data.repository.AlbumRepository
import com.example.media.domain.entity.AlbumEntity
import com.example.media.domain.entity.STATE
import com.example.media.domain.entity.TrackEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val albumRepository: AlbumRepository
) : ViewModel() {
    private val _albumData: MutableLiveData<AlbumEntity> = MutableLiveData()
    val albumData: LiveData<AlbumEntity>
        get() = _albumData
    private val _playingTrack: MutableLiveData<TrackEntity?> = MutableLiveData()
    val playingTrack: LiveData<TrackEntity?>
        get() = _playingTrack

    init {
        viewModelScope.launch {
            with(Dispatchers.IO) {
                albumRepository.getAlbum()?.let {
                    _albumData.postValue(it)
                }
            }
        }
    }

    fun onTrackClicked(clickedTrack: TrackEntity) {
        val playingTrackId = playingTrack.value?.id ?: -1
        val newTracks: List<TrackEntity> = _albumData.value?.tracks?.map {
            val state = when {
                it.id == playingTrackId && it.id == clickedTrack.id -> STATE.PAUSE
                it.id == clickedTrack.id -> STATE.PLAY
                else -> STATE.NOT_PLAY
            }
            val track = TrackEntity(it.fileName, it.id, it.url, state)
            if (track.state != STATE.NOT_PLAY) _playingTrack.postValue(track)
            track
        }.orEmpty()
        _albumData.postValue(_albumData.value?.copy(tracks = newTracks))

    }

    fun onFinishTrack() {
        val playingTrack = playingTrack.value ?: return
        val items = albumData.value?.tracks.orEmpty()
        val currentTrack = items.find { it.id == playingTrack.id }
        val nextIndex = items.indexOf(currentTrack) + 1
        if (nextIndex in items.indices) {
            onTrackClicked(items[nextIndex])
        } else {
            onTrackClicked(items[0])
        }
    }
}