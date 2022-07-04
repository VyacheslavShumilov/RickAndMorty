package com.hfad.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.rickandmorty.databinding.ItemEpisodeBinding

class AdapterEpisode(private val episode: ArrayList<String>, var listener: OnClickListener) :
    RecyclerView.Adapter<AdapterEpisode.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemEpisodeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindView(episode[position])

    override fun getItemCount(): Int = episode.size

    inner class ViewHolder(var binding: ItemEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(episode: String) {
            binding.textViewEpisode.text = episode
            binding.textViewEpisode.setOnClickListener {
                val id = episode.substring(40, episode.length)
                listener.onClickEpisode(id)
            }
        }
    }

    interface OnClickListener {
        fun onClickEpisode(id: String)
    }
}