package com.resucito.ui.song

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.appbar.MaterialToolbar
import com.resucito.R
import com.resucito.databinding.FragmentSongBinding
import com.resucito.model.Song
import com.resucito.model.Stage
import com.resucito.model.Tags

class SongFragment : Fragment() {
    private var _binding: FragmentSongBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSongBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topAppBar = requireActivity().findViewById<MaterialToolbar>(R.id.topAppBar)

        topAppBar.title = "Song"
        topAppBar.navigationIcon = null
        topAppBar.setTitleTextAppearance(context, R.style.SecondaryToolbarTitleText)

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

        binding.pageNumber.text = song.page.toString()
        binding.capoNumber.text = song.capo.toString()
        binding.toneText.text = song.tone
        binding.titleText.text = song.title
        binding.subtitleText.text = song.subtitle


    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}