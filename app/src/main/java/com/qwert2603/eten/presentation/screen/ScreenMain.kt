package com.qwert2603.eten.presentation.screen

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
import androidx.navigation.compose.*
import com.qwert2603.eten.Route

private enum class BottomMenuItem(
    val route: Route,
    val label: String,
    val icon: VectorAsset,
    val content: @Composable () -> Unit
) {
    Meals(
        route = Route.Meals,
        label = "Meals",
        icon = Icons.Default.List,
        content = { ScreenMealsList() }
    ),
    Dishes(
        route = Route.Dishes,
        label = "Dishes",
        icon = Icons.Default.Menu,
        content = { ScreenDishesList() }
    ),
    Products(
        route = Route.Products,
        label = "Products",
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
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
                BottomMenuItem.values().forEach {
                    BottomNavigationItem(
                        icon = { Icon(it.icon) },
                        label = { Text(it.label) },
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