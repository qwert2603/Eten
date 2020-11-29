package com.qwert2603.eten

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.qwert2603.eten.presentation.screen.ScreenEditProduct
import com.qwert2603.eten.presentation.screen.ScreenMain
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
                ScreenMain(navigateToAddProduct = {
                    navController.navigate(Route.AddProduct.name)
                })
            }
            composable(Route.AddProduct.name) {
                ScreenEditProduct(
                    productUuid = null,
                    navigateUp = { navController.navigateUp() }
                )
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EtenApp()
}