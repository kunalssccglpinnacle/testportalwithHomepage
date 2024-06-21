package com.ssccgl.pinnacle.testportal.ui

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import com.ssccgl.pinnacle.testportal.R
import kotlinx.coroutines.launch

@Composable
fun TopAppBarWithDrawerButton(scaffoldState: ScaffoldState) {
    TopAppBar(
        title = { Text("Pinnacle") },
        navigationIcon = {
            val scope = rememberCoroutineScope()
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
               // navigationIcon = { Icon(Icons.Default.Menu, contentDescription = "Menu") }
            }
        }
    )
}
