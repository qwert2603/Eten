package com.qwert2603.eten

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import androidx.ui.tooling.preview.Preview
import com.qwert2603.eten.domain.model.Meal
import com.qwert2603.eten.domain.model.Product
import com.qwert2603.eten.stub.Stub
import com.qwert2603.eten.ui.EtenTheme
import com.qwert2603.eten.util.format
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { EtenApp() }
    }
}

@Composable
fun EtenApp() {
    EtenTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Routes.main,
            builder = {
                composable(Routes.main) { MainScreen(navController) }
                composable(Routes.appProduct) { AddProduct(navController) }
            }
        )
    }
}

@Composable
fun MainScreen(navController: NavController) {
    val selectedBottomIndex = mutableStateOf(0)
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Eten") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Routes.appProduct) },
                icon = { Icon(Icons.Default.Add) }
            )
        },
        bottomBar = {
            BottomAppBar(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.primary,
            ) {
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.List) },
                    label = { Text("Products") },
                    selected = selectedBottomIndex.value == 0,
                    onClick = { selectedBottomIndex.value = 0 },
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Menu) },
                    label = { Text("Meals") },
                    selected = selectedBottomIndex.value == 1,
                    onClick = { selectedBottomIndex.value = 1 },
                )
            }
        },
        bodyContent = {
            when (selectedBottomIndex.value) {
                0 -> ProductsList(products = Stub.products)
                1 -> MealsList(meals = Stub.meals)
            }
        },
    )
}

@Composable
fun ProductsList(products: List<Product>) {
    LazyColumnFor(items = products) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
        ) {
            Text(it.name, fontWeight = FontWeight.Bold)
            Text("${it.caloriePer100g.format()} cal/100g")
        }
        Divider()
    }
}

@Composable
fun MealsList(meals: List<Meal>) {
    LazyColumnFor(
        items = meals,
    ) { meal ->
        Column(
            modifier = Modifier.padding(12.dp),
        ) {
            Text("Meal at ${SimpleDateFormat.getDateTimeInstance().format(meal.time)}")
            meal.products.forEachIndexed { index, product ->
                Text(
                    "${index + 1}. ${product.product.name}: ${product.weight.format()}g = ${product.calories.format()} cal",
                    modifier = Modifier.padding(start = 4.dp),
                )
            }
            Text(
                "Total: ${meal.totalCalories.format()} cal",
                fontWeight = FontWeight.Bold,
            )
        }
        Divider()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EtenApp()
}