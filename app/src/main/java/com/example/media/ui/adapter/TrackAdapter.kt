package com.example.media.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.media.R
import com.example.media.databinding.ItemTrackBinding
import com.example.media.domain.entity.STATE
import com.example.media.domain.entity.TrackEntity

class TrackAdapter(
    private val onClickListener: (TrackEntity) -> Unit
) : ListAdapter<TrackEntity, TrackAdapter.TrackViewHolder>(TrackDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = ItemTrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackViewHolder(binding, onClickListener, parent.context)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

    class TrackViewHolder(
        private val binding: ItemTrackBinding,
        private val onClickListener: (TrackEntity) -> Unit,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(track: TrackEntity) {
            binding.title.text = track.fileName
            binding.icon.setOnClickListener {
                onClickListener.invoke(track)
            }
            val icon = if (track.state == STATE.PLAY) {
                AppCompatResources.getDrawable(context, R.drawable.ic_baseline_pause_circle_24)
            } else {
                AppCompatResources.getDrawable(context, R.drawable.ic_baseline_play_circle_24)
            }
            binding.icon.setImageDrawable(icon)
        }
    }

    class TrackDiffCallback : DiffUtil.ItemCallback<TrackEntity>() {
        override fun areItemsTheSame(oldItem: TrackEntity, newItem: TrackEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TrackEntity, newItem: TrackEntity): Boolean {
            return oldItem == newItem
        }
    }


}
