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
import com.thk.findbook.utils.logd

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

fun NavController.navigateToSearchScreen(keyword: String) {
    val route = "search".run {
        if (keyword.isNotEmpty()) {
            plus("?$ARG_KEY_KEYWORD=$keyword")
        } else {
            this
        }
    }
    logd(">> rout = $route")

    navigate(route) {
        popUpTo(Screen.SEARCH.route)
    }
}

fun NavController.navigateToRecentSearches() {
    navigate(Screen.RECENT_SEARCHES.route)
}

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


        // TODO: viewModel을 거쳐서 처리?
        val keyword: String? = navBackStackEntry.arguments?.getString(ARG_KEY_KEYWORD)
        logd(">> keyword = ${keyword==null}")

        SearchScreen(
            keyword = keyword,
            navigateToRecentSearches = navigateToRecentSearches
        )
    }
}

fun NavGraphBuilder.recentSearchesScreenComposable(
    navigateToSearchScreen: (String) -> Unit
) {
    composable(
        route = Screen.RECENT_SEARCHES.route
    ) {
        RecentSearchesScreen(navigateToSearchScreen = navigateToSearchScreen)
    }
}