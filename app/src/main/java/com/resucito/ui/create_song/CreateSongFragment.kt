package com.resucito.ui.create_song

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.resucito.R
import com.resucito.databinding.FragmentCreateSongBinding

class CreateSongFragment : Fragment() {

    companion object {
        fun newInstance() = CreateSongFragment()
    }

    private var _binding: FragmentCreateSongBinding? = null
    private val binding get() = _binding!!


    private val viewModel: CreateSongViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateSongBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolAppBar = requireActivity().findViewById<MaterialToolbar>(R.id.toolAppBar)
        val bottomNavView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavView)
        toolAppBar.setNavigationIcon(R.drawable.ic_arrow_left)
        bottomNavView.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}