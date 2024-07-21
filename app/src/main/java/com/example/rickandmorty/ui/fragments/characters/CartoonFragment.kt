package com.example.rickandmorty.ui.fragments.characters

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.rickandmorty.R
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.databinding.FragmentCartoonBinding
import com.example.rickandmorty.interfaces.OnClick
import com.example.rickandmorty.ui.fragments.charactersDetailed.CharactersDetailedFragment
import com.example.rickandmorty.utils.Resource
import com.example.rickandmorty.utils.gone
import com.example.rickandmorty.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartoonFragment : Fragment(), OnClick {
    private var _binding: FragmentCartoonBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this)[CartoonViewModel::class.java]
    }

    private val cartoonAdapter by lazy {
        CartoonAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartoonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCharacters.adapter = cartoonAdapter

        viewModel.characters.observe(viewLifecycleOwner){characters ->
            when(characters){
                is Resource.Error -> {
                    binding.pbCartoon.gone()
                    Toast.makeText(requireContext(), characters.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    binding.pbCartoon.visible()
                }
                is Resource.Success -> {
                    binding.pbCartoon.gone()
                    cartoonAdapter.submitList(characters.data)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(character: Character) {
        val action = CartoonFragmentDirections.actionCharactersFragmentToCharactersDetailedFragment(character.id)
        findNavController().navigate(action)
    }
}