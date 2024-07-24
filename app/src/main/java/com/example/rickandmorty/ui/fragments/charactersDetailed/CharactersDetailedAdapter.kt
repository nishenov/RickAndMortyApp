package com.example.rickandmorty.ui.fragments.charactersDetailed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.ItemEpisodeBinding

class CharactersDetailedAdapter() : ListAdapter<String, CharactersDetailedViewHolder>(diffUtil) {
    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharactersDetailedViewHolder {
        val binding = ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersDetailedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharactersDetailedViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class CharactersDetailedViewHolder(private val binding: ItemEpisodeBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(episode: String) {
        val episodeNumber = episode.substringAfterLast('/').toIntOrNull() ?: 0
        binding.tvEpisode.text = "Episode $episodeNumber"
    }
}