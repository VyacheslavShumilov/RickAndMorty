package com.hfad.rickandmorty.view.character

import com.hfad.rickandmorty.model.Characters

interface ICharacterView {
    fun successCharacter(character: Characters)
    fun progress(show: Boolean)
    fun error(message: String)
}