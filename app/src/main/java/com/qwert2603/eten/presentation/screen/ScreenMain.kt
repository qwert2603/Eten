package com.qwert2603.eten.presentation.screen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.*
import com.qwert2603.eten.E
import com.qwert2603.eten.R
import com.qwert2603.eten.Route
import com.qwert2603.eten.presentation.screen.lists.ScreenDishesList
import com.qwert2603.eten.presentation.screen.lists.ScreenMealsList
import com.qwert2603.eten.presentation.screen.lists.ScreenProductsList
import com.qwert2603.eten.util.noContentDescription
import com.qwert2603.eten.view.SnackbarHandler
import kotlinx.coroutines.launch

private enum class BottomMenuItem(
    val route: Route,
    @StringRes val labelRes: Int,
    @DrawableRes val iconRes: Int,
) {
    Meals(
        route = Route.Meals,
        labelRes = R.string.bottom_menu_meals,
        iconRes = R.drawable.ic_meal,
    ),
    Dishes(
        route = Route.Dishes,
        labelRes = R.string.bottom_menu_dishes,
        iconRes = R.drawable.ic_dish,
    ),
    Products(
        route = Route.Products,
        labelRes = R.string.bottom_menu_products,
        iconRes = R.drawable.ic_product,
    ),
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScreenMain(
    navigateToAddProduct: () -> Unit,
    navigateToEditProduct: (uuid: String) -> Unit,
    navigateToAddDish: () -> Unit,
    navigateToEditDish: (uuid: String) -> Unit,
    navigateToProductFromDish: (dishUuid: String) -> Unit,
    navigateToAddMeal: () -> Unit,
    navigateToEditMeal: (uuid: String) -> Unit,
    navigateToSettings: () -> Unit,
    navigateToDebug: () -> Unit,
) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val snackbarHostState = scaffoldState.snackbarHostState
    val scope = rememberCoroutineScope()

    // fixme: bodyContent is under bottomBar

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val snackbarHandler = remember {
        object : SnackbarHandler {
            override fun show(message: String, action: String?, onClick: (() -> Unit)?) {
                if (E.env.showUndoDeleteSnackbar) {
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        val snackbarResult = snackbarHostState.showSnackbar(message, action)
                        if (snackbarResult == SnackbarResult.ActionPerformed) {
                            onClick?.invoke()
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.app_name),
                        modifier = Modifier.clickable(onClick = navigateToDebug),
                    )
                },
                actions = {
                    IconButton(onClick = navigateToSettings) {
                        Icon(Icons.Default.Settings, noContentDescription)
                    }
                },
            )
        },
        floatingActionButton = {
            // todo: FAB as part of list screen.
            FloatingActionButton(
                onClick = {
                    when (currentRoute) {
                        BottomMenuItem.Meals.route.name -> navigateToAddMeal()
                        BottomMenuItem.Dishes.route.name -> navigateToAddDish()
                        BottomMenuItem.Products.route.name -> navigateToAddProduct()
                    }
                },
                content = { Icon(Icons.Default.Add, noContentDescription) }
            )
        },
        bottomBar = {
            BottomNavigation {
                BottomMenuItem.values().forEach {
                    BottomNavigationItem(
                        icon = { Icon(painterResource(it.iconRes), noContentDescription) },
                        label = { Text(stringResource(it.labelRes)) },
                        selected = currentRoute == it.route.name,
                        onClick = {
                            snackbarHostState.currentSnackbarData?.dismiss()

                            // todo: check how it works
                            navController.popBackStack(navController.graph.startDestinationId, false)
                            if (currentRoute != it.route.name) {
                                navController.navigate(it.route.name)
                            }
                        },
                    )
                }
            }
        },
        content = {
            NavHost(navController, startDestination = BottomMenuItem.values().first().route.name) {
                // todo: screens are recreated when switching bottom items
                composable(BottomMenuItem.Meals.route.name) {
                    ScreenMealsList(
                        navigateToEditMeal = navigateToEditMeal,
                        snackbarHandler = snackbarHandler,
                    )
                }
                composable(BottomMenuItem.Dishes.route.name) {
                    ScreenDishesList(
                        navigateToEditDish = navigateToEditDish,
                        navigateToProductFromDish = navigateToProductFromDish,
                        snackbarHandler = snackbarHandler,
                    )
                }
                composable(BottomMenuItem.Products.route.name) {
                    ScreenProductsList(
                        navigateToEditProduct = navigateToEditProduct,
                        snackbarHandler = snackbarHandler,
                    )
                }
            }
        },
    )
}