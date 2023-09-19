package com.movieapp.filmtogo.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.movieapp.filmtogo.R
import com.movieapp.filmtogo.databinding.CategoriesItemBinding
import com.movieapp.filmtogo.modelRemote.Genre

class GenresAdapter(private val onItemClick: (Genre) -> Unit) : RecyclerView.Adapter<GenresAdapter.GenresViewHolder>() {

    private var selectedItem : Genre? = null
    private var genresList : List<Genre> = emptyList()
    private val differ : AsyncListDiffer<Genre> = AsyncListDiffer(this, PreferencesAdapter.GenreDiffCallback())

    fun updateDataset(newGenresList : List<Genre>) {
        differ.submitList(newGenresList)
        genresList = newGenresList
    }

    inner class GenresViewHolder(val binding : CategoriesItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val clickedItem = genresList[adapterPosition]
                selectedItem = clickedItem
                notifyDataSetChanged()
                onItemClick(clickedItem)
            }
        }

        fun bind(genre: Genre, isSelected: Boolean) {
            binding.genreName.text = genre.name
            binding.executePendingBindings()

            if (isSelected) {
                binding.genreCard.setCardBackgroundColor(binding.root.context.getColor(R.color.blue_dark))
            } else {
                binding.genreCard.setCardBackgroundColor(binding.root.context.getColor(R.color.red_orange))
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : CategoriesItemBinding = DataBindingUtil.inflate(layoutInflater,R.layout.categories_item, parent,false)
        return GenresViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return genresList.size
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        val genre = genresList[position]
        val isSelected = genre == selectedItem
        holder.bind(genre, isSelected)
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