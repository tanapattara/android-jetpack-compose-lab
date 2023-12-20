package th.ac.kku.cis.lab04_viewmodel

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import th.ac.kku.cis.lab04_viewmodel.ui.theme.Lab04viewmodelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab04viewmodelTheme {
                StudentApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentApp(studentViewModel: StudentViewModel = viewModel()) {
    var isShowDialog = remember {
        mutableStateOf(false)
    }
    if(isShowDialog.value){
        InputDialog(
            onAddButton = { name, studentId ->
                Log.d("onAddButton", "Name: ${name}, ID: ${studentId}")
                studentViewModel.addStudent(name, studentId)
                isShowDialog.value = false
            },
            onCancle = {
                isShowDialog.value = false
            }
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("My Student")
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
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
    ){
        LazyColumn(modifier = Modifier.padding(it)) {
            items(studentViewModel.data){
                    student->StudentListItem(student.name, student.studentId)
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputDialog(
    onCancle:() -> Unit,
    onAddButton: (name:String, id:String) -> Unit
){
    Dialog(onDismissRequest = onCancle) {
        var inputName by remember {
            mutableStateOf("")
        }
        var inputId by remember {
            mutableStateOf("")
        }
        Card(modifier = Modifier.padding(10.dp)){
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                OutlinedTextField(
                    value = inputId,
                    onValueChange = { inputId = it },
                    label = { Text("Student id")})
                OutlinedTextField(
                    value = inputName,
                    onValueChange = { inputName = it },
                    label = { Text("Student name")})
                TextButton(
                    onClick = { onAddButton(inputName, inputId) },
                    colors =  ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = BottomAppBarDefaults.bottomAppBarFabColor
                        ),
                    border = BorderStroke( width = 1.dp, color = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    Text("Add")
                }
            }}
    }
}

@Composable
fun StudentListItem(name:String, studentId:String){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 2.dp)
    ) {
        Text(text = name, fontSize = 30.sp)
        Text(studentId)
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    StudentApp()
}