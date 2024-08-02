package com.ssccgl.pinnacle.testportal.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ssccgl.pinnacle.testportal.R
import kotlinx.coroutines.launch

@Composable
fun AppDrawer(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    fullName: String,
    emailId: String,
    mobileNumber: String
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header section
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.ic_user_avatar),
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )
            Text(
                text = fullName,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = mobileNumber,
                fontSize = 14.sp
            )
            Text(
                text = emailId,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // Drawer items
        val icons = listOf(
            Icons.Default.Home to "Home",
            Icons.Default.Assessment to "test_pass",
            Icons.Default.Dashboard to "Dashboard",
            Icons.Default.ShoppingCart to "Product",
            Icons.Default.School to "My Courses",
            Icons.Default.Book to "Books",
            Icons.Default.CheckCircle to "Attempted Tests",
            Icons.Default.TrackChanges to "Tracking",
            Icons.Default.LibraryBooks to "eBooks",
            Icons.Default.Save to "Saved",
            Icons.Default.OfflinePin to "Offline eBooks",
            Icons.Default.QrCodeScanner to "QR Scanner",
            Icons.Default.Keyboard to "Typing Software",
            Icons.Default.VideoLibrary to "Offline Videos"
        )

        icons.forEach { (icon, label) ->
            DrawerItem(
                icon = icon,
                label = label
            ) {
                coroutineScope.launch {
                    navController.navigate(label.lowercase().replace(" ", "_")) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    scaffoldState.drawerState.close()
                }
            }
        }
    }
}

@Composable
fun DrawerItem(icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
    }
}
