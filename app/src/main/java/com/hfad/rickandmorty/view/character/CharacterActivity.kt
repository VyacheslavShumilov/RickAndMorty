package com.hfad.rickandmorty.view.character

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.hfad.rickandmorty.view.episodes.EpisodeFragment
import com.hfad.rickandmorty.adapter.AdapterEpisode
import com.hfad.rickandmorty.controller.character.CharacterController
import com.hfad.rickandmorty.controller.character.ICharacterController
import com.hfad.rickandmorty.databinding.ActivityCharacterBinding
import com.hfad.rickandmorty.model.Characters
import com.squareup.picasso.Picasso

class CharacterActivity : AppCompatActivity(), ICharacterView, AdapterEpisode.OnClickListener {

    lateinit var binding: ActivityCharacterBinding
    private var id: String = ""
    private var characterController: ICharacterController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val args = intent.extras
        id = args?.get("ID").toString()

        characterController = CharacterController(this)
        (characterController as CharacterController).onCharacter(id.toInt())
    }

    override fun successCharacter(character: Characters) {
        Picasso.get().load(character.image).into(binding.imageView)
        binding.nameChTextView.text = character.name
        binding.speciesChTextView.text = character.species
        binding.genderChTextView.text = character.gender
        binding.statusChTextView.text = character.status
        binding.episodeChTextView.text = character.episode.size.toString()
        binding.locationChTextView.text = character.location.name

        val adapterEpisode = AdapterEpisode(character.episode, this)
        binding.recyclerViewEpisode.adapter = adapterEpisode

    }

    override fun progress(show: Boolean) {
        if (show) {
            binding.characterConstraintLayout.visibility = View.VISIBLE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun error(message: String) {
        binding.characterConstraintLayout.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    // TODO: Вывести информацию об эпизоде в DialogFragment

    override fun onClickEpisode(id: String) {
        val episodeFragment = EpisodeFragment(id)
        episodeFragment.show(supportFragmentManager, "TAG")
    }
}