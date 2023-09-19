package com.movieapp.filmtogo.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.movieapp.filmtogo.R
import com.movieapp.filmtogo.databinding.CategoriesItemBinding
import com.movieapp.filmtogo.modelRemote.Genre
import java.util.Random

class PreferencesAdapter : RecyclerView.Adapter<PreferencesAdapter.PreferencesViewHolder>() {

    private val selectedItems: MutableList<Genre> = mutableListOf()
    private var genresList: List<Genre> = emptyList()

    private val differ: AsyncListDiffer<Genre> = AsyncListDiffer(this, GenreDiffCallback())

    fun updateDataset(newGenresList: List<Genre>) {
        differ.submitList(newGenresList)
        genresList = newGenresList
    }

    inner class PreferencesViewHolder(val binding: CategoriesItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val adapterPosition = bindingAdapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val clickedItem = genresList[adapterPosition]
                    if (selectedItems.contains(clickedItem)) {
                        selectedItems.remove(clickedItem)
                    } else {
                        selectedItems.add(clickedItem)
                    }
                    updateBackgroundColor(selectedItems.contains(clickedItem))
                }
            }
        }
        fun bind(genre: Genre, isSelected: Boolean) {
            binding.genreName.text = genre.name
            updateBackgroundColor(isSelected)
            binding.executePendingBindings()
        }

        private fun updateBackgroundColor(isSelected: Boolean) {
            val backgroundColor = if (isSelected) {
                binding.root.context.getColor(R.color.blue_dark)
            } else {
                generateRandomPastelColor()
            }
            binding.genreCard.setCardBackgroundColor(backgroundColor)
        }
    }

    fun getSelectedList(): List<Genre> {
        return selectedItems
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreferencesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : CategoriesItemBinding = DataBindingUtil.inflate(layoutInflater,R.layout.categories_item, parent,false)
        return PreferencesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PreferencesViewHolder, position: Int) {
        val genre = genresList[position]
        val isSelected = selectedItems.contains(genre)
        holder.bind(genre, isSelected)
    }

    override fun getItemCount(): Int {
        return genresList.size
    }
    private fun generateRandomPastelColor(): Int {
        val random = Random()
        val red = random.nextInt(256) / 2 + 128
        val green = random.nextInt(256) / 2 + 128
        val blue = random.nextInt(256) / 2 + 128
        return Color.rgb(red, green, blue)
    }

    class GenreDiffCallback : DiffUtil.ItemCallback<Genre>() {

        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem.id == newItem.id && oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem == newItem
        }
    }
}