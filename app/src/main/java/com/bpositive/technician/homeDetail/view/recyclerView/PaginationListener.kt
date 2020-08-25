package com.bpositive.technician.homeDetail.view.recyclerView

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationListener(private val layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val totalItemCount = layoutManager.itemCount

        if (!isLoading && !isLastPage) {
            if (/*totalItemCount >= PAGE_SIZE
                && firstVisibleItemPosition >= 0
                &&*/ !recyclerView.canScrollVertically(1)
            ) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()
    abstract val isLastPage: Boolean
    abstract val isLoading: Boolean

    companion object {
        const val PAGE_START = 0
        const val PAGE_SIZE = 10
    }

}