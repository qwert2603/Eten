package com.qwert2603.eten.presentation.screen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Text
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.compose.*
import com.qwert2603.eten.R
import com.qwert2603.eten.Route

private enum class BottomMenuItem(
    val route: Route,
    @StringRes val labelRes: Int,
    @DrawableRes val iconRes: Int,
    val content: @Composable () -> Unit
) {
    Meals(
        route = Route.Meals,
        labelRes = R.string.bottom_menu_meals,
        iconRes = R.drawable.ic_meal,
        content = { ScreenMealsList() }
    ),
    Dishes(
        route = Route.Dishes,
        labelRes = R.string.bottom_menu_dishes,
        iconRes = R.drawable.ic_dish,
        content = { ScreenDishesList() }
    ),
    Products(
        route = Route.Products,
        labelRes = R.string.bottom_menu_products,
        iconRes = R.drawable.ic_product,
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
            TopAppBar(title = { Text(stringResource(R.string.app_name)) })
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
                        icon = { Icon(vectorResource(it.iconRes)) },
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