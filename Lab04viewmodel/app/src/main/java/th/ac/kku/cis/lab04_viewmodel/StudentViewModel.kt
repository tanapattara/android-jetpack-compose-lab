package th.ac.kku.cis.lab04_viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class StudentViewModel: ViewModel() {

    private val _count by  mutableStateOf(0)
    val count: Int = _count

    private val _listStudent = mutableStateListOf<String>()
    val listStudent: List<String> = _listStudent
    fun addStudent(item: String) {
        _listStudent.add(item)
    }
}