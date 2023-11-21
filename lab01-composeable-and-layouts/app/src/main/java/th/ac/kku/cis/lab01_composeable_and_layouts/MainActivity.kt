package th.ac.kku.cis.lab01_composeable_and_layouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import th.ac.kku.cis.lab01_composeable_and_layouts.ui.theme.Lab01composeableandlayoutsTheme

data class Person (val name:String, val imageId:Int, val studentId:String)

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
            setContent {
            Lab01composeableandlayoutsTheme {
                PersonApp()
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(
    title: String,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
){
    TopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back Navigation"
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonApp(
    navController: NavHostController = rememberNavController()
){
    val backStackEntry by navController.currentBackStackEntryAsState()
    var currentScreen = backStackEntry?.destination?.route ?: "List"
    if(currentScreen.contains("/"))
        currentScreen = currentScreen.split("/")[0]

    Scaffold(
        topBar ={
            MyAppBar(
                title= currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = "List",
            modifier = Modifier.padding(paddingValues)
        ){
            composable(route = "List"){
                PersonsList(onItemClick = { userId ->
                    navController.navigate(route = "Detail/$userId")
                })
            }
            composable(route = "Detail/{userId}"){
                    backStackEntry -> PersonDetail(navController, userID = backStackEntry.arguments?.getString("userId"))
            }
        }
    }
}
@Composable
fun PersonsList(onItemClick: (String) -> Unit,){
    val persons: List<Person> = listOf<Person>(
        Person("Mickey Mouse", R.drawable.avatar, "001"),
        Person("Minnie Mouse", R.drawable.avatar, "001"),
        Person("Okoye", R.drawable.okoye_avatar, "001"),
    )
    val context = LocalContext.current

    LazyColumn(
              modifier = Modifier.fillMaxSize()
          ){
                items(persons){
                    persons -> PersonListItem(data = persons, onClick = onItemClick)
                }
          }
}
@Composable
fun PersonListItem(data:Person, onClick: (msg: String) -> Unit){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                horizontal = 10.dp,
                vertical = 10.dp
            )
            .fillMaxWidth()
            .clickable { onClick(data.studentId) }){
        Image(
            painter = painterResource(id = data.imageId),
            contentDescription = "Avatar image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape))
        Spacer(modifier = Modifier.width(16.dp))
        Column  {
            Text(data.name)
            Text(data.studentId)
        }}
}
@Composable
fun PersonDetail(navController: NavHostController = rememberNavController(), userID: String?){
    var displayText: String = "Hello world"
    if(userID != null)
        displayText = userID
    Text(text = displayText)
}