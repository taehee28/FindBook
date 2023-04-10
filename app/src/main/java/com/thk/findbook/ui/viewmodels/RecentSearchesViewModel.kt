package com.thk.findbook.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thk.data.repository.RecentSearchesRepository
import com.thk.findbook.models.RecentSearch
import com.thk.findbook.models.toRecentSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentSearchesViewModel @Inject constructor(
    private val repository: RecentSearchesRepository
) : ViewModel() {
    val recentSearches: StateFlow<List<RecentSearch>> = repository.getRecentSearches()
        .mapLatest { list ->
            list.map { it.toRecentSearch() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }
}