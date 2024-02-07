package th.ac.kku.cis.lab6_firebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import th.ac.kku.cis.lab6_firebase.ui.theme.Lab6firebaseTheme
import th.ac.kku.cis.lab6_firebase.view.NewTaskView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirebaseApp(navController: NavHostController = rememberNavController()){

    val backStackEntry by navController.currentBackStackEntryAsState()
    var currentScreen = backStackEntry?.destination?.route ?: "HOME"
    if(currentScreen.contains("/"))
        currentScreen = currentScreen.split("/")[0]

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Task")
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        IconButton(onClick = {navController.navigateUp()}) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back Navigation"
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(route = "NEWTASK") },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Add, "Add new Task")
            }
        }
    ){
            paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "HOME",
            modifier = Modifier.padding(paddingValues)
        ){
            composable(route = "HOME"){
                Text("Home")
            }
            composable(route = "NEWTASK"){
                NewTaskView { navController.navigateUp() }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    Lab6firebaseTheme {
        FirebaseApp()
    }
}