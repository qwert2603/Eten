package com.qwert2603.eten.presentation.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.Text
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.*
import com.qwert2603.eten.R
import com.qwert2603.eten.Route

private enum class BottomMenuItem(
    val route: Route,
    @StringRes val labelRes: Int,
    val icon: VectorAsset,
    val content: @Composable () -> Unit
) {
    Meals(
        route = Route.Meals,
        labelRes = R.string.bottom_menu_meals,
        icon = Icons.Default.List,
        content = { ScreenMealsList() }
    ),
    Dishes(
        route = Route.Dishes,
        labelRes = R.string.bottom_menu_dishes,
        icon = Icons.Default.Menu,
        content = { ScreenDishesList() }
    ),
    Products(
        route = Route.Products,
        labelRes = R.string.bottom_menu_products,
        icon = Icons.Default.Place,
        content = { ScreenProductsList() }
    ),
}

@Composable
fun ScreenMain(
    navigateToAddProduct: () -> Unit,
) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Eten") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToAddProduct() },
                icon = { Icon(Icons.Default.Add) }
            )
        },
        bottomBar = {
            BottomNavigation {
                stringResource(R.string.app_name)
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
                BottomMenuItem.values().forEach {
                    BottomNavigationItem(
                        icon = { Icon(it.icon) },
                        label = { Text(stringResource(it.labelRes)) },
                        selected = currentRoute == it.route.name,
                        onClick = {
                            navController.popBackStack(navController.graph.startDestination, false)
                            if (currentRoute != it.route.name) {
                                navController.navigate(it.route.name)
                            }
                        },
                    )
                }
            }
        },
        bodyContent = {
            NavHost(navController, startDestination = BottomMenuItem.values().first().route.name) {
                BottomMenuItem.values().forEach { bottomMenuItem ->
                    composable(bottomMenuItem.route.name) { bottomMenuItem.content() }
                }
            }
        },
    )
}