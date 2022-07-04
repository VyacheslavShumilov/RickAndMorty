package com.hfad.rickandmorty.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hfad.rickandmorty.view.character.CharacterActivity
import com.hfad.rickandmorty.adapter.AdapterCharacters
import com.hfad.rickandmorty.controller.CharactersController
import com.hfad.rickandmorty.controller.ICharacterController
import com.hfad.rickandmorty.databinding.ActivityMainBinding
import com.hfad.rickandmorty.model.Characters
import com.hfad.rickandmorty.model.Info
import com.hfad.rickandmorty.utils.EndlessScrollEventListener

class MainActivity : AppCompatActivity(), ICharactersView, AdapterCharacters.SetOnClickListener {
    private lateinit var binding: ActivityMainBinding
    private var charactersController: ICharacterController? = null
    private lateinit var endlessScrollEventListener: EndlessScrollEventListener
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapterCharacters: AdapterCharacters
    private var errorConnection = false
    private var isLoading = false
    private var isLastPage = false
    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        charactersController = CharactersController(this)
        (charactersController as CharactersController).onCharactersList(1)

        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
        setEndlessListenerScrollView()
        binding.recyclerView.addOnScrollListener(endlessScrollEventListener)
    }

    private fun setEndlessListenerScrollView() {
        endlessScrollEventListener = object : EndlessScrollEventListener(layoutManager) {
            override fun onLoadMore(recyclerView: RecyclerView) {
                isLoading = true
                if (currentPage != 0) {
                    currentPage++
                    (charactersController as CharactersController).onCharactersListPage(currentPage)
                }
            }
        }
    }

    override fun onSuccessList(characters: ArrayList<Characters>) {
        adapterCharacters = AdapterCharacters(characters, this)
        binding.recyclerView.adapter = adapterCharacters
    }

    @SuppressLint("SetTextI18n")
    override fun onSuccessInfo(info: Info) {
        adapterCharacters.showCountCharacters(info.count)
        if (info.next != null) {
            if (currentPage <= info.pages) adapterCharacters.addLoadingFooter(true)
            else isLastPage = true
        }
    }

    override fun onSuccessListPage(characters: ArrayList<Characters>) {
        adapterCharacters.addAllCharacters(characters)
        isLoading = false
        adapterCharacters.addLoadingFooter(false)
    }


    override fun onSuccessInfoPage(info: Info) {
        if (info.next != null) {
            if (currentPage != info.pages) adapterCharacters.addLoadingFooter(true)
            else isLastPage = true
        }
    }

    override fun error(errMessage: String) {
        Toast.makeText(this, errMessage, Toast.LENGTH_SHORT).show()
    }

    override fun errorPage(errMessage: String) {
        errorConnection = true
        adapterCharacters.addLoadingFooter(false)
        adapterCharacters.showReply(true)
    }

    override fun progress(show: Boolean) {
        if (show) binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE
    }

    override fun onClickUsers(id: Int) {
        val intent = Intent(this, CharacterActivity::class.java)
        intent.putExtra("ID", id.toString())
        startActivity(intent)
    }

    override fun onClickReply() {
        errorConnection = false
        adapterCharacters.showReply(false)
        adapterCharacters.addLoadingFooter(true)
        (charactersController as CharactersController).onCharactersListPage(currentPage)
    }
}