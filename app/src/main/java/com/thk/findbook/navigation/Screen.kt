package com.thk.findbook.navigation

enum class Screen(val route: String) {
    SEARCH("search?$ARG_KEY_KEYWORD={$ARG_KEY_KEYWORD}"),
    RECENT_SEARCHES("recent_searches")
}

const val ARG_KEY_KEYWORD = "keyword"