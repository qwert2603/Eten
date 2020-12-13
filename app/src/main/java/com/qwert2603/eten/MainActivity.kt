package com.qwert2603.eten

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.ui.tooling.preview.Preview
import com.qwert2603.eten.presentation.screen.ScreenEditProduct
import com.qwert2603.eten.presentation.screen.ScreenMain
import com.qwert2603.eten.presentation.screen.edit_dish.ScreenEditDish
import com.qwert2603.eten.ui.EtenTheme
import com.qwert2603.eten.view.SelectDropDown

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
                    navigateToEditDish = { uuid -> navController.navigate("edit_dish?uuid=$uuid") }
                )
            }
            composable(
                route = "edit_product?uuid={uuid}",
                arguments = listOf(navArgument("uuid") {
                    type = NavType.StringType
                    nullable = true
                })
            ) {
                ScreenEditProduct(
                    productUuid = it.arguments?.getString("uuid"),
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
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
//    EtenApp()

    val list = listOf("een", "twee", "drie", "vier", "vijf", "zes")
    var selected by remember { mutableStateOf<String?>(null) }
    SelectDropDown(
        items = list,
        selectedItem = selected,
        renderItem = { Text(it) },
        onItemSelected = { selected = it },
    )
}