
package com.ssccgl.pinnacle.testportal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

val icons = listOf(
    Icons.Default.Home to "Home",
    Icons.Default.Assessment to "Test Portal",
    Icons.Default.ShoppingCart to "Product",
    Icons.Default.School to "My Courses"
)

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    Box {
        BottomAppBar(
            cutoutShape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
            backgroundColor = MaterialTheme.colors.background,
            contentColor = Color.Gray,
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
        ) {
            icons.subList(0, 2).forEach { (icon, label) ->
                val selected = navController.currentDestination?.route == label.toLowerCase().replace(" ", "_")
                BottomNavigationItem(
                    icon = { Icon(imageVector = icon, contentDescription = label) },
                    label = { Text(label) },
                    selected = selected,
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = Color.Gray,
                    onClick = { navigateTo(navController, label.toLowerCase().replace(" ", "_")) }
                )
            }
            Spacer(Modifier.weight(1f, true))
            icons.subList(2, 4).forEach { (icon, label) ->
                val selected = navController.currentDestination?.route == label.toLowerCase().replace(" ", "_")
                BottomNavigationItem(
                    icon = { Icon(imageVector = icon, contentDescription = label) },
                    label = { Text(label) },
                    selected = selected,
                    selectedContentColor = MaterialTheme.colors.secondary,
                    unselectedContentColor = Color.Gray,
                    onClick = { navigateTo(navController, label.toLowerCase().replace(" ", "_")) }
                )
            }
        }

        FloatingActionButton(
            onClick = { navigateTo(navController, "dashboard") },
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = -28.dp) // Adjust this value as needed to fit the design
        ) {
            Icon(imageVector = Icons.Default.Dashboard, contentDescription = "Dashboard")
        }
    }
}

fun navigateTo(navController: NavHostController, route: String) {
    navController.navigate(route) {
        popUpTo(navController.graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
