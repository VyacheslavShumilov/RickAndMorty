package com.hfad.rickandmorty.controller

import com.hfad.rickandmorty.model.Characters
import com.hfad.rickandmorty.model.ResponseCharacters
import com.hfad.rickandmorty.services.Api
import com.hfad.rickandmorty.view.ICharactersView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharactersController(private val charactersView: ICharactersView) : ICharacterController {

    private var api = Api.create()

    override fun onCharactersList(page:Int) {
        charactersView.let { view ->
            view.progress(true)
            api.getCharacter(page).enqueue(object : Callback<ResponseCharacters> {
                override fun onResponse(
                    call: Call<ResponseCharacters>,
                    response: Response<ResponseCharacters>
                ) {
                    if (response.isSuccessful) {
                        view.progress(false)
                        response.body()?.let { data ->
                            view.onSuccessList(data.characters)
                            view.onSuccessInfo(data.info)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseCharacters>, t: Throwable) {
                    view.progress(false)
                    view.error("Проверьте подключение к интернету")
                }
            })
        }
    }

    override fun onCharactersListPage(page: Int) {
        charactersView.let { view ->
            api.getCharacter(page).enqueue(object : Callback<ResponseCharacters> {
                override fun onResponse(
                    call: Call<ResponseCharacters>,
                    response: Response<ResponseCharacters>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { data ->
                            view.onSuccessListPage(data.characters)
                            view.onSuccessInfoPage(data.info)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseCharacters>, t: Throwable) {
                    view.errorPage("Проверьте подключение к интернету")
                }
            })
        }
    }

}