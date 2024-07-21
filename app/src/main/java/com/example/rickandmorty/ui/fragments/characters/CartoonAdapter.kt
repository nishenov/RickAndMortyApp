package com.example.rickandmorty.ui.fragments.characters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.databinding.ItemCharacterBinding
import com.example.rickandmorty.interfaces.OnClick
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CartoonAdapter(private val onClick: OnClick) : ListAdapter<Character, CartoonViewHolder>(diffUtil) {
companion object{
    private val diffUtil = object : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartoonViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartoonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartoonViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener{
            onClick.onClick(getItem(position))
        }
    }
}

class CartoonViewHolder(private val binding: ItemCharacterBinding) : ViewHolder(binding.root) {
    fun bind(character: Character) {
        binding.apply {
            tvName.text = character.name
            tvStatusRace.text = """${character.status} - ${character.species}"""
            tvLocationNow.text = character.location.name
            val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val targetFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
            try {
                val date: Date = originalFormat.parse(character.created)
                val formattedDate: String = targetFormat.format(date)
                tvCreatedTimeDescription.text = formattedDate
            } catch (e: ParseException) {
                e.printStackTrace()
                tvCreatedTimeDescription.text = character.created // fallback to original date string if parsing fails
            }
            Glide.with(root.context)
                .load(character.image)
                .into(imgCharacter)

            val circleStatus = imgLiveStatus.drawable
            val color = when (character.status) {
                "Alive" -> Color.GREEN
                "Dead" -> Color.RED
                else -> Color.GRAY
            }
            circleStatus?.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN)
        }

    }
}
