package com.hfad.rickandmorty.view

import com.hfad.rickandmorty.model.Characters
import com.hfad.rickandmorty.model.Info

interface ICharactersView {
    fun onSuccessList(characters: ArrayList<Characters>)
    fun onSuccessListPage(characters: ArrayList<Characters>)
    fun onSuccessInfo(info: Info)
    fun onSuccessInfoPage(info: Info)
    fun error(errMessage: String)
    fun errorPage(errMessage: String)
    fun progress(show: Boolean)
}