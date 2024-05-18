package com.dicoding.dicodingstory.ui.story

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.dicodingstory.data.remote.response.StoryItem
import com.dicoding.dicodingstory.databinding.ItemStoryBinding

class StoryAdapter : PagingDataAdapter<StoryItem, StoryAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val aStory = getItem(position)
        if (aStory != null) {
            holder.bind(aStory)
        }
    }

    class MyViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(aStory: StoryItem){
            Glide.with(binding.root.context)
                .load(aStory.photoUrl) // URL Gambar
                .into(binding.ivItemPhoto) // imageView mana yang akan diterapkan
            binding.tvItemName.text = aStory.name
            binding.tvItemDescription.text = aStory.description

            binding.root.setOnClickListener {
                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.ivItemPhoto, "photo"),
                        Pair(binding.tvItemName, "name"),
                        Pair(binding.tvItemDescription, "description")
                    )
                val intentDetail = Intent(binding.root.context, DetailStoryActivity::class.java)
                intentDetail.putExtra("id_story", aStory)
                binding.root.context.startActivity(intentDetail, optionsCompat.toBundle())
            }
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryItem>() {
            override fun areItemsTheSame(oldItem: StoryItem, newItem: StoryItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: StoryItem, newItem: StoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}