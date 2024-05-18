package com.dicoding.dicodingstory

import com.dicoding.dicodingstory.data.remote.response.StoryItem

object DataDummy {

    fun generateDummyStoryResponse(): List<StoryItem> {
        val items: MutableList<StoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = StoryItem(
                "https://story-api.dicoding.dev/images/stories/photos-1715998808579_002a7323e260fde85756.jpg",
                "createdAt $i",
                "name $i",
                "description $i",
                -7.739967,
                "id $i",
                110.664566
            )
            items.add(story)
        }
        return items
    }
}