package com.hfad.rickandmorty.controller.episodes

import com.hfad.rickandmorty.model.Episode
import com.hfad.rickandmorty.services.Api
import com.hfad.rickandmorty.view.episodes.IEpisodeView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EpisodeController(private val episodesView: IEpisodeView): IEpisodeController {

    private var api = Api.create()

    override fun onEpisode(id: Int) {
        episodesView.let { view ->
            api.getEpisodeId(id).enqueue(object : Callback<Episode> {
                override fun onResponse(call: Call<Episode>, response: Response<Episode>) {
                    if (response.isSuccessful) {
                        response.body()?.let { data ->
                            view.successEpisode(data)
                        }
                    }
                }

                override fun onFailure(call: Call<Episode>, t: Throwable) {
                    view.error("Нет подключения к интернету")
                }
            })
        }
    }
}