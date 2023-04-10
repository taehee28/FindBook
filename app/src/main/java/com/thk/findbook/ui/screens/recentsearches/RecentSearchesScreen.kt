package com.thk.findbook.ui.screens.recentsearches

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thk.findbook.R
import com.thk.findbook.models.RecentSearch
import com.thk.findbook.ui.viewmodels.RecentSearchesViewModel

@Composable
fun RecentSearchesScreen(
    navigateToSearchScreen: (String) -> Unit,
    viewModel: RecentSearchesViewModel = hiltViewModel()
) {
    val searches by viewModel.recentSearches.collectAsState()

    BackHandler {
        navigateToSearchScreen("")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.recent_searches))},
                actions = {
                    DeleteAllAction(onClick = viewModel::deleteAll)
                }
            )
        }
    ) { paddingValues ->
        RecentSearchesList(
            searchList = searches,
            onKeywordClick = { navigateToSearchScreen(it) },
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun DeleteAllAction(
    onClick: () -> Unit
) = TextButton(onClick = onClick) {
    Text(
        text = stringResource(id = R.string.delete_all),
        color = MaterialTheme.colors.onPrimary
    )
}

@Composable
private fun RecentSearchesList(
    searchList: List<RecentSearch>,
    onKeywordClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 8.dp)
    ) {
        itemsIndexed(
            items = searchList,
            key = { index: Int, item: RecentSearch -> item.id }
        ) { index: Int, item: RecentSearch ->
            RecentSearchItem(
                keyword = item.keyword,
                onKeywordClick = onKeywordClick
            )

            if (index < searchList.lastIndex) {
                Divider()
            }
        }
    }
}

@Composable
private fun RecentSearchItem(
    keyword: String,
    onKeywordClick: (String) -> Unit
) = Box(
    modifier = Modifier
        .fillMaxWidth()
        .clickable { onKeywordClick(keyword) }
        .padding(horizontal = 16.dp, vertical = 8.dp),
    contentAlignment = Alignment.CenterStart
) {
    Text(text = keyword)
}

@Preview
@Composable
private fun RecentSearchesScreenPreview() {
    RecentSearchesScreen(
        navigateToSearchScreen = {},
        viewModel = viewModel()
    )
}

@Preview
@Composable
private fun RecentSearchItemPreview() {
    RecentSearchItem(
        keyword = "검색어",
        onKeywordClick = {}
    )
}