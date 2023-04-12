package com.thk.findbook.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.thk.data.repository.SearchRepository
import com.thk.data.utils.logd
import com.thk.findbook.models.Book
import com.thk.findbook.models.toBook
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 검색 화면에서 사용할 ViewModel
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {
    /**
     * 책 검색 결과의 PagingData
     */
    private val _searchPaging: MutableStateFlow<PagingData<Book>> = MutableStateFlow(PagingData.empty())
    val searchPaging: StateFlow<PagingData<Book>>
        get() = _searchPaging.asStateFlow()

    /**
     * 주어진 검색 키워드로 책 검색
     */
    fun searchBook(keyword: String) {
        logd(">> searchBook -> $keyword")
        viewModelScope.launch {
            repository.searchBook(keyword)
                .mapLatest { pagingData ->
                    // UI에서 사용하는 타입으로 변환
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