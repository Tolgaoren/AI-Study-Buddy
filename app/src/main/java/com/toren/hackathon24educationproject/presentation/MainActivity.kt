package com.toren.hackathon24educationproject.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.toren.hackathon24educationproject.presentation.navigation.BottomBarScreens
import com.toren.hackathon24educationproject.presentation.navigation.Navigation
import com.toren.hackathon24educationproject.presentation.theme.Hackathon24EducationProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Hackathon24EducationProjectTheme {
                 MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    val items = listOf(
        BottomBarScreens.Practice,
        BottomBarScreens.Classroom,
        BottomBarScreens.Profile
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    selectedItemIndex = items.indexOfFirst { it.route == currentRoute }

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (selectedItemIndex == index) {
                                    item.selectedIcon
                                } else {
                                    item.icon
                                },
                                contentDescription = item.title
                            )
                        },
                        label = {
                            Text(text = item.title)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Navigation(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Hackathon24EducationProjectTheme {
        MainScreen()
    }
}