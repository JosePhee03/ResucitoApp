package com.resucito.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.search.SearchBar
import com.resucito.R
import com.resucito.databinding.FragmentSearchBinding
import com.resucito.model.SongList
import com.resucito.model.Stage
import com.resucito.ui.search.adapter.SongListAdapter


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        initUI()
    }

    private fun initUI() {
        setupToolAppBar()
        setupSongList()
    }


    private fun setupToolAppBar() {
        val toolAppBar = requireActivity().findViewById<MaterialToolbar>(R.id.toolAppBar)
        val searchBarSong = requireActivity().findViewById<SearchBar>(R.id.searchBar)
        toolAppBar.visibility = View.GONE
        searchBarSong.visibility = View.VISIBLE
    }

    private fun setupSongList() {
        val songListView = binding.recycleViewSongList
        songListView.layoutManager = LinearLayoutManager(context)


        val data = ArrayList<SongList>()
        data.add(SongList("Aclamad al Señor", 278, Stage.PRECATECHUMENATE, "Sal 100 (99)", false))
        data.add(
            SongList(
                "Adónde te escondiste amado",
                279,
                Stage.PRECATECHUMENATE,
                "Cántico Espiritual",
                true
            )
        )
        data.add(
            SongList(
                "Alegría, ha nacido el salvador",
                280,
                Stage.CATECHUMENATE,
                "Villancico",
                false
            )
        )
        data.add(
            SongList(
                "Antífona interleccional para el tiempo de Cuaresma",
                281,
                Stage.LITURGY,
                "Antífona interleccional para el tiempo de Cuaresma",
                false
            )
        )
        data.add(
            SongList(
                "Bendición penitencial",
                28,
                Stage.ELECTION,
                "oracion conclusiva de accion de gracias",
                true
            )
        )
        val adapter = SongListAdapter(data)
        songListView.adapter = adapter

        val dividerItemDecoration =
            MaterialDividerItemDecoration(songListView.context, LinearLayoutManager.VERTICAL)
        dividerItemDecoration.dividerColor =
            ContextCompat.getColor(requireContext(), R.color.outlineVariant)
        songListView.addItemDecoration(dividerItemDecoration)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}