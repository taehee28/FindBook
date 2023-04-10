package com.thk.findbook.ui.screens.recentsearches

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thk.findbook.R
import com.thk.findbook.models.RecentSearch

@Composable
fun RecentSearchesScreen(
    navigateToSearchScreen: (String) -> Unit
) {
    BackHandler {
        navigateToSearchScreen("")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.recent_searches))},
                actions = {
                    DeleteAllAction {
                        // TODO: 전체 삭제 동작
                    }
                }
            )
        }
    ) { paddingValues ->
        RecentSearchesList(
            keywords = emptyList(),     // TODO: 실제 데이터 넘기기
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
    keywords: List<RecentSearch>,
    onKeywordClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(
            items = keywords,
            key = { it.id }
        ) { recentSearch ->
            RecentSearchItem(
                keyword = recentSearch.keyword,
                onKeywordClick = onKeywordClick
            )
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
        .padding(horizontal = 16.dp, vertical = 8.dp),
    contentAlignment = Alignment.CenterStart
) {
    Text(
        text = keyword,
        modifier = Modifier.clickable { onKeywordClick(keyword) }
    )
}

@Preview
@Composable
private fun RecentSearchesScreenPreview() {
    RecentSearchesScreen(
        navigateToSearchScreen = {}
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