package com.example.gestordeinventario.ui.devolutions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestordeinventario.model.Element
import com.example.gestordeinventario.model.PendingElement
import com.example.gestordeinventario.model.Student
import com.example.gestordeinventario.repository.PendingElementRepository
import com.example.gestordeinventario.repository.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class DevolutionsViewModel: ViewModel() {

    private val _student = MutableStateFlow(Student("", "", ArrayList(), ""))
    val student: StateFlow<Student> = _student

    private val _devolutionsList = MutableStateFlow(List(0){PendingElement(0, Element("", 0), Date())})
    val devolutionsList : StateFlow<List<PendingElement> > = _devolutionsList

    private val _students = MutableStateFlow(List(0){Student("", "", ArrayList(), "",)})
    val students = _students

    private val _isLoading = MutableStateFlow(false)
    val isLoading : StateFlow<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            _isLoading.value = true
            getStudents()
            getDevolutions()
            _isLoading.value = false
        }
    }

    private suspend fun getStudents() {
        val result: List<Student> = withContext(Dispatchers.IO) {
            StudentRepository().getAll()
        }
        _students.value = result
    }

    private fun getDevolutions(){
        viewModelScope.launch{
            val result: List<PendingElement> = withContext(Dispatchers.IO) {
                PendingElementRepository().getAll().filter { !it.isSolved() }
            }
            _devolutionsList.value = result
            println("Debug trace: DEVOLUTIONS SIZE: ${_devolutionsList.value.size}")
        }
    }
    fun getDevolutionsFromStudent(student: Student): List<PendingElement>{
        return student.pendingDevolutions.filter { !it.isSolved() }
    }
}