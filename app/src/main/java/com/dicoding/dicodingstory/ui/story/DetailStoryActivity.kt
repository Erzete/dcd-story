package com.dicoding.dicodingstory.ui.story

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.dicodingstory.data.remote.response.StoryItem
import com.dicoding.dicodingstory.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding
    private var story: StoryItem? = null

    companion object {
        private const val ID_STORY = "id_story"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        story = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(ID_STORY, StoryItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<StoryItem>(ID_STORY)
        }

        if (story != null) {
            Glide.with(this)
                .load(story?.photoUrl) // URL Gambar
                .into(binding.ivDetailPhoto) // imageView mana yang akan diterapkan
            binding.tvDetailName.text = story?.name
            binding.tvDetailDescription.text = story?.description
        }
    }
}