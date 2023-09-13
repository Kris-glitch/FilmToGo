package com.movieapp.filmtogo.ui.adapters

import com.movieapp.filmtogo.databinding.SubscriptionCardItemBinding
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.movieapp.filmtogo.modelRemote.Subscription
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.movieapp.filmtogo.R

class SubscriptionAdapter(private val subscriptions: List<Subscription>, private val onItemClick: (Subscription) -> Unit)
    : RecyclerView.Adapter<SubscriptionAdapter.SubscriptionViewHolder>() {

    private var selectedItem : Subscription? = null

    inner class SubscriptionViewHolder(val binding : SubscriptionCardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                val clickedItem = subscriptions[adapterPosition]
                selectedItem = clickedItem
                notifyDataSetChanged()
                onItemClick(clickedItem)
            }
        }

        fun bind(subscription: Subscription, isSelected: Boolean) {

            binding.apply {
                subscriptionType.text = subscription.sub_title
                fee.text = subscription.sub_price
                descOne.text = subscription.sub_desc_one
                descTwo.text = subscription.sub_desc_two
                descThree.text = subscription.sub_desc_three
            }


            val backgroundColor = when (subscription.sub_title) {
                "Basic" -> R.color.green
                "Standard" -> R.color.yellow
                "Premium" -> R.color.red
                else -> R.color.blue_light
            }
            binding.subCard.setCardBackgroundColor(binding.root.context.getColor(backgroundColor))

            if (isSelected) {
                binding.subCard.setCardBackgroundColor(binding.root.context.getColor(R.color.blue_dark))
            } else {
                binding.subCard.setCardBackgroundColor(binding.root.context.getColor(backgroundColor))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriptionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : SubscriptionCardItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.subscription_card_item, parent, false)
        return SubscriptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubscriptionViewHolder, position: Int) {
        val subscription = subscriptions[position]
        val isSelected = subscription == selectedItem
        holder.bind(subscription, isSelected)
    }

    override fun getItemCount(): Int {
        return subscriptions.size
    }
}