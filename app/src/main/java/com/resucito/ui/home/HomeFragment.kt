package com.resucito.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.search.SearchBar
import com.resucito.R
import com.resucito.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        setupAppBar()
        setupBottomNav()
        setupFAB()
    }

    private fun setupBottomNav() {
        val bottomNavView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavView)
        bottomNavView.visibility = View.VISIBLE
    }

    private fun setupFAB() {
        binding.fabCreateSong.visibility = View.VISIBLE
        binding.fabCreateSong.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_navigation_create_song)
        }
    }

    private fun setupAppBar() {
        val toolAppBar = requireActivity().findViewById<MaterialToolbar>(R.id.toolAppBar)
        val searchBar = requireActivity().findViewById<SearchBar>(R.id.searchBar)

        searchBar.visibility = View.GONE
        toolAppBar.visibility = View.VISIBLE
        toolAppBar.navigationIcon = null
        toolAppBar.title = getString(R.string.app_name)
        toolAppBar.setTitleTextAppearance(context, R.style.PrimaryToolbarTitleText)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}