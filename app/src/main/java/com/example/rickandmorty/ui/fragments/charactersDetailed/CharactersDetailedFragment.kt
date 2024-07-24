package com.example.rickandmorty.ui.fragments.charactersDetailed

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.databinding.FragmentCartoonBinding
import com.example.rickandmorty.databinding.FragmentCharactersDetailedBinding
import com.example.rickandmorty.utils.Resource
import com.example.rickandmorty.utils.gone
import com.example.rickandmorty.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
@AndroidEntryPoint
class CharactersDetailedFragment : Fragment() {
    private var _binding: FragmentCharactersDetailedBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this)[CharactersDetailedViewModel::class.java]
    }

    private val charactersDetailedAdapter by lazy {
        CharactersDetailedAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersDetailedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvEpisodes.adapter = charactersDetailedAdapter

        val characterId =arguments?.getInt("characterId") ?: return
        viewModel.setCharacterId(characterId)

        viewModel.charactersDetails.observe(viewLifecycleOwner){resource->
            when(resource){
                is Resource.Error -> {
                    binding.pbDetails.gone()
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    binding.pbDetails.visible()
                }
                is Resource.Success -> {
                    binding.pbDetails.gone()
                    resource.data?.let {character ->
                        bind(character)
                        charactersDetailedAdapter.submitList(character.episode)
                    }
                }
            }

        }

    }

    private fun bind(character: Character) = with(binding) {
        tvName.text = character.name
        tvStatus.text = """${character.status} - ${character.species}"""
        tvLocation.text = character.location.name
        tvGender.text = character.gender
        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val targetFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        try {
            val date: Date = originalFormat.parse(character.created)
            val formattedDate: String = targetFormat.format(date)
            tvCreatedTime.text = formattedDate
        } catch (e: ParseException) {
            e.printStackTrace()
            tvCreatedTime.text = character.created
        }
        Glide.with(root.context)
            .load(character.image)
            .into(imgDetailCharacter)

        val circleStatus = imgStatusOfCharacter.drawable
        val color = when (character.status) {
            "Alive" -> Color.GREEN
            "Dead" -> Color.RED
            else -> Color.GRAY
        }
        circleStatus?.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}