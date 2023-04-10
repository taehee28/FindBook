@file:OptIn(ExperimentalGlideComposeApi::class)

package com.thk.findbook.ui.screens.search

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.thk.findbook.R
import com.thk.findbook.models.Book
import com.thk.findbook.ui.viewmodels.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navigateToRecentSearches: () -> Unit
) {
    val searchResults by viewModel.searchResult.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.search_book)) },
                actions = {
                    RecentSearchesAction {
                        // TODO: 최근 검색어 화면으로 이동
                        navigateToRecentSearches()
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            var text by remember { mutableStateOf("") }
            SearchBox(
                text = text,
                onTextChange = { text = it },
                onSearchClick = {
                    // TODO: 빈칸 검색 불가 안내 토스트
                    if (text.isNotBlank()) viewModel.searchBook(text)
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            SearchResultList(results = searchResults ?: emptyList())
        }
    }
}

@Composable
private fun RecentSearchesAction(
    onClick: () -> Unit
) = TextButton(onClick = onClick) {
    Text(
        text = stringResource(id = R.string.recent_searches),
        color = MaterialTheme.colors.onPrimary
    )
}

@Composable
private fun SearchBox(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClick: () -> Unit
) = Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
) {
    TextField(
        value = text,
        onValueChange = onTextChange,
        modifier = Modifier.weight(1f),
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
        singleLine = true
    )

    Button(
        onClick = onSearchClick
    ) {
        Text(text = stringResource(id = R.string.search))
    }
}

@Composable
private fun SearchResultList(
    results: List<Book>
) {
    Log.d("TAG", "SearchResultList: size = ${results.size}")
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            items = results,
            key = { it.title }
        ) { result ->
            SearchResultItem(
                imageUrl = result.imageUrl,
                title = result.title,
                author = result.author,
                publisher = result.publisher,
                discount = result.discount,
                onClick = { /*TODO: 웹페이지로 이동*/ }
            )
        }
    }
}

@Composable
private fun SearchResultItem(
    imageUrl: String,
    title: String,
    author: String,
    publisher: String,
    discount: String,
    onClick: () -> Unit
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 4.dp, vertical = 2.dp)
        .clickable(onClick = onClick)
) {
    GlideImage(
        model = imageUrl,
        contentDescription = title,
        modifier = Modifier.width(100.dp)
    )

    Spacer(modifier = Modifier.width(4.dp))

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = "제목: $title", fontSize = 14.sp)

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "저자: $author", fontSize = 12.sp)
        Text(text = "출판사: $publisher", fontSize = 12.sp)

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "가격: $discount", fontSize = 12.sp)
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen(
        navigateToRecentSearches = {}
    )
}

@Preview
@Composable
private fun SearchResultItemPreview(){
    SearchResultItem(
        imageUrl = "https://picsum.photos/300/500",
        title = "title",
        author = "author",
        publisher = "publisher",
        discount = "price",
        onClick = {}
    )
}