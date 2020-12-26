package com.qwert2603.eten

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.qwert2603.eten.presentation.screen.ScreenMain
import com.qwert2603.eten.presentation.screen.edit_dish.ScreenEditDish
import com.qwert2603.eten.presentation.screen.edit_meal.ScreenEditMeal
import com.qwert2603.eten.presentation.screen.edit_product.EditProductParam
import com.qwert2603.eten.presentation.screen.edit_product.ScreenEditProduct
import com.qwert2603.eten.presentation.screen.settings.ScreenSettings
import com.qwert2603.eten.ui.EtenTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EtenApp()
        }
    }
}

@Composable
fun EtenApp() {
    EtenTheme {
        val navController = rememberNavController()
        NavHost(navController, startDestination = Route.Main.name) {
            composable(Route.Main.name) {
                ScreenMain(
                    navigateToAddProduct = { navController.navigate("edit_product") },
                    navigateToEditProduct = { uuid -> navController.navigate("edit_product?uuid=$uuid") },
                    navigateToAddDish = { navController.navigate("edit_dish") },
                    navigateToEditDish = { uuid -> navController.navigate("edit_dish?uuid=$uuid") },
                    navigateToProductFromDish = { dishUuid ->
                        navController.navigate("edit_product?from_dish_uuid=$dishUuid")
                    },
                    navigateToAddMeal = { navController.navigate("edit_meal") },
                    navigateToEditMeal = { uuid -> navController.navigate("edit_meal?uuid=$uuid") },
                    navigateToSettings = { navController.navigate("settings") },
                )
            }
            composable(
                route = "edit_product?uuid={uuid}&from_dish_uuid={from_dish_uuid}",
                arguments = listOf(
                    navArgument("uuid") {
                        type = NavType.StringType
                        nullable = true
                    },
                    navArgument("from_dish_uuid") {
                        type = NavType.StringType
                        nullable = true
                    },
                )
            ) {
                val productUuid = it.arguments?.getString("uuid")
                val dishUuid = it.arguments?.getString("from_dish_uuid")
                ScreenEditProduct(
                    editProductParam = when {
                        productUuid != null -> EditProductParam.EditProduct(productUuid)
                        dishUuid != null -> EditProductParam.FromDish(dishUuid)
                        else -> EditProductParam.NewProduct
                    },
                    navigateUp = { navController.navigateUp() }
                )
            }
            composable(
                route = "edit_dish?uuid={uuid}",
                arguments = listOf(navArgument("uuid") {
                    type = NavType.StringType
                    nullable = true
                })
            ) {
                ScreenEditDish(
                    dishUuid = it.arguments?.getString("uuid"),
                    navigateUp = { navController.navigateUp() }
                )
            }
            composable(
                route = "edit_meal?uuid={uuid}",
                arguments = listOf(navArgument("uuid") {
                    type = NavType.StringType
                    nullable = true
                })
            ) {
                ScreenEditMeal(
                    mealUuid = it.arguments?.getString("uuid"),
                    navigateUp = { navController.navigateUp() }
                )
            }
            composable(
                route = "settings",
            ) {
                ScreenSettings(
                    navigateUp = { navController.navigateUp() }
                )
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
//    EtenApp()

    val list = listOf("een", "twee", "drie", "vier", "vijf", "zes")
}