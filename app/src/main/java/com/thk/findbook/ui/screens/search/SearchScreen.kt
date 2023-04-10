@file:OptIn(ExperimentalGlideComposeApi::class)

package com.thk.findbook.ui.screens.search

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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.thk.findbook.R
import com.thk.findbook.models.Book

@Composable
fun SearchScreen(
    navigateToRecentSearches: () -> Unit
) {
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
                onSearchClick = { /*TODO: 검색 버튼 클릭*/ }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // TODO: 실제 데이터 넘기기
            SearchResultList(results = emptyList())
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
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)
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
                publisher = result.author,
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
        .clickable(onClick = onClick)
) {
    GlideImage(
        model = imageUrl,
        contentDescription = title,
        modifier = Modifier
            .width(30.dp)
            .height(50.dp)
    )

    Column {
        Text(text = "제목: $title")
        Text(text = "저자: $author")
        Text(text = "출판사: $publisher")
        Text(text = "가격: $discount")
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