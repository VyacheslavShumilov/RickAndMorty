package com.hfad.rickandmorty.view.episodes

import androidx.fragment.app.FragmentManager
import com.hfad.rickandmorty.model.Episode

interface IEpisodeView {
    fun successEpisode(episode: Episode)
    fun error(message: String)
}