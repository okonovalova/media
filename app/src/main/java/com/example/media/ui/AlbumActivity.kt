package com.example.media.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.media.databinding.ActivityAlbumBinding
import com.example.media.domain.entity.STATE
import com.example.media.ui.adapter.TrackAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumActivity : AppCompatActivity() {
    private val viewModel: AlbumViewModel by viewModels()
    private val adapter: TrackAdapter by lazy {
        TrackAdapter {
            viewModel.onTrackClicked(it)
        }
    }
    private lateinit var binding: ActivityAlbumBinding
    private val observer: MediaLifecycleObserver by lazy { MediaLifecycleObserver(viewModel::onFinishTrack) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycle.addObserver(observer)

        binding.tracks.adapter = adapter
        viewModel.albumData.observe(this) { album ->
            with(binding) {
                albumTitle.text = album.title
                singerTitle.text = album.artist
                genreTitle.text = album.genre
                yearsTitle.text = album.published
                adapter.submitList(album.tracks)
                progress.visibility = View.GONE
            }
        }
        viewModel.playingTrack.observe(this) { track ->
            when  {
                track == null -> {
                    return@observe
                }
                track.state == STATE.PAUSE -> {
                    observer.lastTrack = null
                    observer.mediaPlayer?.pause()
                }
                track.state == STATE.PLAY -> {
                    observer.apply {
                        lastTrack = track.url
                        mediaPlayer?.reset()
                        mediaPlayer?.setDataSource(track.url)
                    }.play()
                }
            }
        }
    }

}