package com.example.mypocapp.presentation.ui.features.user.navigation

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.mypocapp.R
import com.example.mypocapp.util.Constants

data class NavigationItem(
    val title: String = "",
    val icon: ImageVector = Icons.Filled.Home,
    val screenRoute: String = ""
)

fun getNavigationItems(context: Context): List<NavigationItem> {
    return listOf(
        NavigationItem(
            title = context.getString(R.string.home),
            icon = Icons.Filled.Home,
            screenRoute = NavigationScreens.Home.screenRoute
        )
    )
}

sealed class NavigationScreens(var screenRoute: String) {
    data object Home : NavigationScreens(Constants.HOME_ROUTES)
}