package com.dicoding.dicodingstory.ui.story

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingstory.data.remote.response.StoryItem
import com.dicoding.dicodingstory.databinding.ActivityStoryBinding
import com.dicoding.dicodingstory.ui.StoryViewModelFactory

class StoryActivity : AppCompatActivity() {
    private val viewModel by viewModels<StoryViewModel> {
        StoryViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvStory.addItemDecoration(itemDecoration)

        viewModel.listStory.observe(this) { stories ->
            setStoriesData(stories)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setStoriesData(stories: List<StoryItem>) {
        val adapter = StoryAdapter()
        adapter.submitList(stories)
        binding.rvStory.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}