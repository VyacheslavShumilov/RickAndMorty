package com.hfad.rickandmorty.view.episodes


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.hfad.rickandmorty.controller.episodes.EpisodeController
import com.hfad.rickandmorty.controller.episodes.IEpisodeController
import com.hfad.rickandmorty.databinding.FragmentEpisodeBinding
import com.hfad.rickandmorty.model.Episode


class EpisodeFragment(var id: String) : DialogFragment(), IEpisodeView {

    private lateinit var binding: FragmentEpisodeBinding
    private var episodeController: IEpisodeController? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        episodeController = EpisodeController(this)
        (episodeController as EpisodeController).onEpisode(id.toInt())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentEpisodeBinding.inflate(LayoutInflater.from(requireContext()), container, false)
        return binding.root
    }

    override fun successEpisode(episode: Episode) {
        binding.idTextView.text = episode.id.toString()
        binding.nameTextView.text = episode.name
        binding.airDateTextView.text = episode.airDate
        binding.episodeTextView.text = episode.episode
    }

    override fun error(message: String) {
        Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
