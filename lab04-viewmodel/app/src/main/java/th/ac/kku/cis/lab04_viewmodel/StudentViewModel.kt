package th.ac.kku.cis.lab04_viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class StudentViewModel: ViewModel() {

    var data = mutableStateListOf<StudentModel>( )
    fun addStudent(name: String, studentId: String) {
        val newId = data.size + 1
        data.add( StudentModel(newId,name,studentId) )
        Log.d("StudentViewModel", "Total students: ${data.size}")

    }
}