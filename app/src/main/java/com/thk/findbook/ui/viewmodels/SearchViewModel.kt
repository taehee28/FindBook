package com.thk.findbook.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _searchResult: MutableStateFlow<List<Book>> = MutableStateFlow(emptyList())
    val searchResult: StateFlow<List<Book>>
        get() = _searchResult.asStateFlow()

    fun searchBook(keyword: String) = viewModelScope.launch {
        repository.searchBook(keyword)
            .map {
                it.map { it.toBook() }
            }
            .collectLatest {
                logd(">> collect = $it")
                _searchResult.value = it
            }
    }
}