package com.thk.findbook.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.thk.data.repository.SearchRepository
import com.thk.findbook.models.Book
import com.thk.findbook.models.toBook
import com.thk.findbook.utils.logd
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {
    init {
        logd(">> init~~~~")
    }

    private val _searchPaging: MutableStateFlow<PagingData<Book>> = MutableStateFlow(PagingData.empty())
    val searchPaging: StateFlow<PagingData<Book>>
        get() = _searchPaging.asStateFlow()

    fun searchBook(keyword: String) {
        logd(">> searchBook -> $keyword")
        viewModelScope.launch {
            repository.searchBook(keyword)
                .mapLatest { pagingData ->
                    pagingData.map { bookEntity ->
                        bookEntity.toBook()
                    }
                }.catch {
                    it.printStackTrace()
                }.collectLatest {
                    _searchPaging.value = it
                }
        }
    }
}