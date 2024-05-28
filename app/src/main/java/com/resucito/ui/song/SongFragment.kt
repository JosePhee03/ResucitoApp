package com.resucito.ui.song

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.chip.Chip
import com.google.android.material.search.SearchBar
import com.resucito.R
import com.resucito.databinding.FragmentSongBinding
import com.resucito.model.Song
import com.resucito.model.Stage
import com.resucito.model.Tags

class SongFragment : Fragment() {
    private var _binding: FragmentSongBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topAppBar = requireActivity().findViewById<MaterialToolbar>(R.id.toolAppBar)

        topAppBar.title = "Song"
        topAppBar.navigationIcon = null
        topAppBar.setTitleTextAppearance(context, R.style.SecondaryToolbarTitleText)

        initUI()
        val song = Song(
            "Mi-\nA. ¡OH SEÑOR, NUESTRO DIOS, \n                         Sol\nQUÉ ADMIRABLE ES TU NOMBRE;\n                                    Si7                                       La-\nPOR TODA LA TIERRA, TU NOMBRE,\n                                                                           Si7\nHASTA EL CIELO SE ELEVA TU AMOR!\n  \nLa-                                                      Si7\nS. Con la boca de los niños pequeños, \nLa-                                            Si7\nafirmas tu gloria, oh Señor,\nDo                                                                     Si7\ny reduces al silencio enemigos y rebeldes.\n \nMi-\nA. ¡OH SEÑOR, NUESTRO DIOS  ... \n  \nLa-                                                                   Si7\nS. Si contemplo el cielo, obra de tus manos, \nLa-                                                              Si7\nla luna y las estrellas que has creado,\nDo                                                                                       Si7\n¿qué es el hombre para que te acuerdes de él,\nDo                                                                Si7\nel hijo del hombre para darle poder?\n \nSol                          Si7                                               Re-\nLo hiciste, lo hiciste poco menos que los ángeles,\nDo                                                        Si7\nde gloria y honor lo has coronado,\nDo                                                             Si7\ntodo lo has sometido bajo sus pies.\n \nMi-\nA. ¡OH SEÑOR, NUESTRO DIOS  ... \n",
            "La-",
            2,
            listOf(Tags.EXIT, Tags.ADVENT),
            "Oh Señor, nuestro Dios",
            159,
            Stage.PRECATECHUMENATE,
            "Sal 8"
        )


        val chipStage = Chip(context).apply {
            text = song.stage.name
        }
        applyStyleChip(chipStage, song.stage)
        binding.TagsChipGroup.addView(chipStage)

        for (tag in song.tags) {
            val chip = Chip(context).apply {
                text = tag.name
                setChipBackgroundColorResource(R.color.background)
                setChipStrokeColorResource(R.color.onSurface)
                setTextColor(ContextCompat.getColor(context, R.color.onSurface))
            }
            binding.TagsChipGroup.addView(chip)
        }


        binding.pageNumber.text = song.page.toString()
        binding.capoNumber.text = song.capo.toString()
        binding.toneText.text = song.tone
        binding.titleText.text = song.title
        binding.subtitleText.text = song.subtitle


        generateLyricLayout(song.lyric)

    }

    private fun initUI() {
        setupToolAppBar()
    }

    private fun setupToolAppBar() {
        val toolAppBar = requireActivity().findViewById<MaterialToolbar>(R.id.toolAppBar)
        val searchBarSong = requireActivity().findViewById<SearchBar>(R.id.searchBar)
        toolAppBar.setTitleTextAppearance(context, R.style.SecondaryToolbarTitleText)
        toolAppBar.visibility = View.VISIBLE
        searchBarSong.visibility = View.GONE
    }


    @SuppressLint("ResourceType")
    private fun generateLyricLayout(lyric: String) {
        context?.let {
            val context = it
            val parts = lyric.split("\n")
            val chordRegex = Regex("(Do|Re|Mi|Fa|Sol|La|Si)[-7]?((?!\\s)|(?=\\s{2,}))")
            val firstLetterRegex = Regex("(A\\.|S\\.)")
            val uppercaseLettersRegex = Regex("[A-Z]{2,}")

            for (part in parts) {
                val newView = TextView(context).apply { textSize = 16F }

                if (chordRegex.findAll(part).any()) {
                    newView.apply {
                        text = "     ".plus(part)
                        setTextColor(ContextCompat.getColor(context, R.color.primary))
                        setTypeface(ResourcesCompat.getFont(context, R.font.franklin_semibold))
                    }
                } else {
                    newView.apply {
                        text = if (firstLetterRegex.findAll(part).any()) {
                            part
                        } else {
                            "     ".plus(part)
                        }
                        if (uppercaseLettersRegex.findAll(part).any()) {
                            setTypeface(ResourcesCompat.getFont(context, R.font.franklin_bold))
                        } else {
                            setTypeface(ResourcesCompat.getFont(context, R.font.franklin_medium))
                        }
                    }
                }
                binding.lyricLayout.addView(newView)
            }
        }
    }

    private fun applyStyleChip(chip: Chip, stage: Stage) {
        context?.let {
            when (stage) {
                Stage.LITURGY -> {
                    chip.setChipBackgroundColorResource(R.color.liturgyContainer)
                    chip.setTextColor(ContextCompat.getColor(it, R.color.onLiturgyContainer))
                    chip.setChipStrokeColorResource(R.color.liturgyContainer)
                }

                Stage.ELECTION -> {
                    chip.setChipBackgroundColorResource(R.color.electionContainer)
                    chip.setTextColor(ContextCompat.getColor(it, R.color.onElectionContainer))
                    chip.setChipStrokeColorResource(R.color.electionContainer)
                }

                Stage.PRECATECHUMENATE -> {
                    chip.setChipBackgroundColorResource(R.color.precatechumenate)
                    chip.setTextColor(
                        ContextCompat.getColor(
                            it,
                            R.color.onPrecatechumenateContainer
                        )
                    )
                    chip.setChipStrokeColorResource(R.color.precatechumenate)
                }

                Stage.CATECHUMENATE -> {
                    chip.setChipBackgroundColorResource(R.color.catechumenateContainer)
                    chip.setTextColor(
                        ContextCompat.getColor(
                            it,
                            R.color.onPrecatechumenateContainer
                        )
                    )
                    chip.setChipStrokeColorResource(R.color.catechumenateContainer)
                }
            }

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSongBinding.inflate(layoutInflater, container, false)

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}