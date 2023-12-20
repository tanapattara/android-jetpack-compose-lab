package th.ac.kku.cis.lab03_state

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import th.ac.kku.cis.lab03_state.ui.theme.Lab03stateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab03stateTheme {
                // A surface container using the 'background' color from the theme
                ShoppingCartApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingCartApp() {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    var itemList = remember { mutableStateListOf<String>() }

    var isShowDialog = remember {
        mutableStateOf(false)
    }
    if(isShowDialog.value){
        InputDialog(
            onAddButton = {
                name ->
                itemList.add(name)
                isShowDialog.value = false
            },
            onCancle = {
                isShowDialog.value = false
            }
        )
    }
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text("My Shopping Cart")
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
                    FloatingActionButton(
                        onClick = { isShowDialog.value = true },
                        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(Icons.Filled.Add, "Localized description")
                    }
        }
    )
    {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(itemList){ item -> ShoppingCartItem(item) }
        }
    }
}
@Composable
fun ShoppingCartItem(
    itemname:String
){
    var amount: Int by remember { mutableStateOf(0) }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(start = 8.dp)
        ) {
            Text( text = itemname, fontSize = 20.sp, modifier = Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically){
                IconButton(
                    onClick = { /*TODO*/ },
                ) {
                    Icon(Icons.Filled.ArrowBack, "Localized description")
                }
                Text("$amount", fontSize = 20.sp)
                IconButton(onClick = { amount++ }) {
                    Icon(Icons.Filled.ArrowForward, "Localized description")
                }
            }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputDialog(
    onCancle:() -> Unit,
    onAddButton:(name:String) -> Unit
){
    Dialog(onDismissRequest = onCancle) {
        var inputName by remember {
            mutableStateOf("")
        }
        Card(modifier = Modifier.padding(10.dp)){
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                OutlinedTextField(
                    value = inputName,
                    onValueChange = { inputName = it },
                    label = { Text("Item name")})
                TextButton(
                    onClick = { onAddButton(inputName) },
                    colors =  ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                ) {
                    Text("Add")
                }
            }}
    }
}
@Preview(showBackground = true)
@Composable
fun AppPreview() {
    ShoppingCartApp()
}