package com.hfad.rickandmorty.controller.character

import com.hfad.rickandmorty.model.Characters
import com.hfad.rickandmorty.services.Api
import com.hfad.rickandmorty.view.character.ICharacterView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterController(private val characterView: ICharacterView) : ICharacterController {

    private var api = Api.create()

    override fun onCharacter(id: Int) {
        characterView.let { view ->
            view.progress(true)
            api.getCharacterId(id).enqueue(object : Callback<Characters> {
                override fun onResponse(call: Call<Characters>, response: Response<Characters>) {
                    if (response.isSuccessful) {
                        view.progress(false)
                        response.body()?.let { data ->
                            view.successCharacter(data)
                        }
                    }
                }

                override fun onFailure(call: Call<Characters>, t: Throwable) {
                    view.progress(false)
                    view.error("Проверьте подключение к интернету")
                }
            })
        }
    }
}