package com.salihakbas.ecommercecompose.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.salihakbas.ecommercecompose.R


@Composable
fun BottomNavigation(
    navController: NavController
) {
    val bottomNavItems = listOf(
        BottomNavItem(
            title = R.string.home,
            icon = R.drawable.ic_home_selected,
            route = Screen.Home
        ),
        BottomNavItem(
            title = R.string.cart,
            icon = R.drawable.ic_cart_selected,
            route = Screen.Cart
        ),
        BottomNavItem(
            title = R.string.favorites,
            icon = R.drawable.ic_favorite_selected,
            route = Screen.Favorites
        ),
        BottomNavItem(
            title = R.string.profile,
            icon = R.drawable.ic_profile_selected,
            route = Screen.Profile
        )
    )
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route

    NavigationBar {
        bottomNavItems.forEach { item ->
            val selected = currentDestination == Screen.getRoute(item.route)
            NavigationBarItem(
                selected = currentDestination == item.route.toString(),
                onClick = {
                    navController.navigate(Screen.getRoute(item.route)) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = stringResource(id = item.title)
                    )
                },
                label = {
                    Text(text = stringResource(id = item.title))
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    BottomNavigation(navController = rememberNavController())
}