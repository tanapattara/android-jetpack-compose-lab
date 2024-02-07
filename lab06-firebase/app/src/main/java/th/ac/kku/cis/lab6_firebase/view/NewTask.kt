package th.ac.kku.cis.lab6_firebase.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import th.ac.kku.cis.lab6_firebase.ui.theme.Lab6firebaseTheme
import th.ac.kku.cis.lab6_firebase.viewmodel.NewTaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskView(
    newTaskViewModel: NewTaskViewModel = viewModel(),
    onCompleate: () -> Boolean
){
    var inputTitle:String by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = inputTitle,
            onValueChange = { inputTitle = it },
            placeholder = { "Title" }
        )
        Button(
            onClick = { newTaskViewModel.onSave(context = context, title = inputTitle, onCompleate = onCompleate) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "SAVE")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun AppPreview() {
    var navController: NavHostController = rememberNavController()
    Lab6firebaseTheme {
        NewTaskView( onCompleate = { navController.navigateUp() })
    }
}