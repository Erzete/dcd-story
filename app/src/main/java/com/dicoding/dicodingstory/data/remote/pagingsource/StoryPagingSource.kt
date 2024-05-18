package com.dicoding.dicodingstory.data.remote.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.dicodingstory.data.remote.response.StoryItem
import com.dicoding.dicodingstory.data.remote.retrofit.ApiService

// Not used because I've changed it to RemoteMediator 
class StoryPagingSource(private val apiService: ApiService) : PagingSource<Int, StoryItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getAllStory(position, params.loadSize)
            LoadResult.Page(
                data = responseData.listStory,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.listStory.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, StoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}