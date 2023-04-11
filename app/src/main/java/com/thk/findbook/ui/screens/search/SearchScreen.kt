@file:OptIn(ExperimentalGlideComposeApi::class)

package com.thk.findbook.ui.screens.search

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.thk.findbook.R
import com.thk.findbook.models.Book
import com.thk.findbook.ui.viewmodels.SearchViewModel
import com.thk.findbook.utils.logd

@Composable
fun SearchScreen(
    navigateToRecentSearches: () -> Unit,
    keyword: String?,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchResults: LazyPagingItems<Book> = viewModel.searchPaging.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        logd(">> LaunchedEffect")
        keyword?.also { viewModel.searchBook(keyword) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.search_book)) },
                actions = {
                    RecentSearchesAction {
                        navigateToRecentSearches()
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            var text by remember { mutableStateOf(keyword ?: "") }
            val context = LocalContext.current

            SearchBox(
                text = text,
                onTextChange = {
                    if (it.length <= 20) text = it
                },
                onSearchClick = {
                    if (text.isNotBlank()) {
                        viewModel.searchBook(text)
                    } else {
                        Toast.makeText(context, R.string.toast_blank_keyword, Toast.LENGTH_LONG).show()
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            SearchResultList(results = searchResults)
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
    results: LazyPagingItems<Book>
) {
    val uriHandler = LocalUriHandler.current

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        items(
            items = results,
            key = { item: Book ->
                item.title
            }
        ) { book: Book? ->
            SearchResultItem(
                imageUrl = book?.imageUrl ?: "",
                title = book?.title ?: "",
                author = book?.author ?: "",
                publisher = book?.publisher ?: "",
                discount = book?.discount ?: "",
                onClick = { book?.also { uriHandler.openUri(it.link) } }
            )

            Divider()
        }

        when (results.loadState.refresh) {
            is LoadState.Error -> {
                item {
                    StateText(text = stringResource(id = R.string.loading_failed))
                }
            }
            else -> {}
        }

        when (results.loadState.append) {
            is LoadState.Error -> {
                item {
                    StateText(text = stringResource(id = R.string.loading_failed))
                }
            }
            is LoadState.Loading -> {
                item {
                    StateText(text = stringResource(id = R.string.now_loading))
                }
            }
            else -> {}
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
        Text(text = stringResource(id = R.string.label_title).format(title), fontSize = 14.sp)

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = stringResource(id = R.string.label_author).format(author), fontSize = 12.sp)
        Text(text = stringResource(id = R.string.label_publisher).format(publisher), fontSize = 12.sp)

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = stringResource(id = R.string.label_discount).format(discount), fontSize = 12.sp)
    }
}

@Composable
private fun StateText(
    text: String
) = Box(
    modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp),
    contentAlignment = Alignment.Center
) {
    Text(text = text)
}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen(
        keyword = "",
        navigateToRecentSearches = {},
        viewModel = viewModel()
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