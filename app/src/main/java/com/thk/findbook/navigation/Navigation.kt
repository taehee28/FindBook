package com.thk.findbook.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.thk.findbook.ui.screens.recentsearches.RecentSearchesScreen
import com.thk.findbook.ui.screens.search.SearchScreen

@Composable
fun SetupNavigation() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = Screen.SEARCH.route
    ) {
        searchScreenComposable { navController.navigateToRecentSearches() }
        recentSearchesScreenComposable { navController.navigateToSearchScreen(keyword = it) }
    }
}

/**
 * [SearchScreen]으로 이동하는 [NavController]의 확장 함수
 */
fun NavController.navigateToSearchScreen(keyword: String) {
    // 파라미터로 넘어온 검색 키워드가 있는 경우에만
    // route 뒤에 쿼리문을 붙여줌
    val route = "search".run {
        if (keyword.isNotEmpty()) {
            plus("?$ARG_KEY_KEYWORD=$keyword")
        } else {
            this
        }
    }

    navigate(route) {
        popUpTo(Screen.SEARCH.route) {
            inclusive = true
        }
    }
}

/**
 * [RecentSearchesScreen]으로 이동하는 [NavController]의 확장 함수
 */
fun NavController.navigateToRecentSearches() {
    navigate(Screen.RECENT_SEARCHES.route)
}

/**
 * [SearchScreen]을 호출하는 [NavGraphBuilder]의 확장 함수
 */
fun NavGraphBuilder.searchScreenComposable(
    navigateToRecentSearches: () -> Unit,
) {
    composable(
        route = Screen.SEARCH.route,
        arguments = listOf(
            navArgument(ARG_KEY_KEYWORD) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )
    ) { navBackStackEntry ->
        val keyword: String? = navBackStackEntry.arguments?.getString(ARG_KEY_KEYWORD)

        SearchScreen(
            keyword = keyword,
            navigateToRecentSearches = navigateToRecentSearches
        )
    }
}

/**
 * [RecentSearchesScreen]을 호출하는 [NavGraphBuilder]의 확장 함수
 */
fun NavGraphBuilder.recentSearchesScreenComposable(
    navigateToSearchScreen: (String) -> Unit
) {
    composable(
        route = Screen.RECENT_SEARCHES.route
    ) {
        RecentSearchesScreen(navigateToSearchScreen = navigateToSearchScreen)
    }
}