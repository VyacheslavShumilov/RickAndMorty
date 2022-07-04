package com.hfad.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.rickandmorty.databinding.ItemCharacterBinding
import com.hfad.rickandmorty.databinding.ItemLoadingBinding
import com.hfad.rickandmorty.databinding.ItemNoInternetBinding
import com.hfad.rickandmorty.databinding.ItemTitleBinding
import com.hfad.rickandmorty.model.Characters
import com.squareup.picasso.Picasso

class AdapterCharacters(private val _characters: ArrayList<Characters>,
    private val listener: SetOnClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isLoadingAdded = false
    private var errorConnection = false
    private var countCharacters = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var viewHolder: RecyclerView.ViewHolder? = null
        when (viewType) {
            LOADING -> {
                val viewLoading =
                    ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                viewHolder = LoadingViewHolder(viewLoading)
            }
            ITEM -> {
                val viewItem =
                    ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                viewHolder = CharactersViewHolder(viewItem)
            }
            // TODO: Если нет и интернета, вывести сообщение и кнопку повтора
            ERROR_CONNECTION -> {
                val viewError =
                    ItemNoInternetBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                viewHolder = NoInternetViewHolder(viewError)
            }

            TITLE -> {
                val viewTitle =
                    ItemTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                viewHolder = TitleViewHolder(viewTitle)
            }
        }
        return viewHolder!!
    }

    fun addAllCharacters(characters: ArrayList<Characters>) {
        _characters.addAll(characters)
        notifyItemInserted(_characters.size - 1)
    }

    override fun getItemViewType(position: Int): Int {
        return if (isPositionHeader(position)) TITLE
        else if (position == _characters.size && isLoadingAdded) LOADING
        else if (position == _characters.size && errorConnection) ERROR_CONNECTION
        else ITEM
    }

    private fun isPositionHeader(position: Int): Boolean = position == 0

    fun addLoadingFooter(show: Boolean) {
        isLoadingAdded = show
    }

    fun showReply(show: Boolean) {
        errorConnection = show
        notifyItemChanged(_characters.size)
    }

    fun showCountCharacters(count:Int){
        countCharacters = count
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            LOADING -> {
                val loadingViewHolder = holder as LoadingViewHolder
                loadingViewHolder.binding.progressBarAdapter.visibility = View.VISIBLE
            }
            ITEM -> {
                val characters = _characters[position - 1]
                val charactersViewHolder = holder as CharactersViewHolder
                charactersViewHolder.bindView(characters)
            }
            // TODO: Если нет и интернета, вывести сообщение и кнопку повтора
            ERROR_CONNECTION -> {
                val noInternetViewHolder = holder as NoInternetViewHolder
                noInternetViewHolder.binding.btnRepeat.setOnClickListener { listener.onClickReply() }
            }

            TITLE -> {
                val titleViewHolder = holder as TitleViewHolder
                titleViewHolder.binding.countCharacters.text = "Количество персонажей $countCharacters"
            }
        }
    }

    inner class CharactersViewHolder(var binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(characters: Characters) {
            with(binding) {
                nameTextView.text = characters.name
                genderTextView.text = characters.gender
                speciesTextView.text = characters.species
                Picasso.get().load(characters.image).into(avatarImageView)

                itemView.setOnClickListener {
                    listener.onClickUsers(characters.id)
                }
            }
        }
    }

    inner class LoadingViewHolder(val binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root)

    // TODO: ДЗ
    inner class NoInternetViewHolder(val binding: ItemNoInternetBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class TitleViewHolder(val binding: ItemTitleBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = _characters.size + 1

    interface SetOnClickListener {
        fun onClickUsers(id: Int)
        fun onClickReply()
    }

    companion object {
        const val ITEM = 0
        const val LOADING = 1

        // TODO: ДЗ
        const val ERROR_CONNECTION = 2

        const val TITLE = 3
    }
}
