package com.example.gestordeinventario.ui.student_pendings

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestordeinventario.model.Student
import com.example.gestordeinventario.repository.StudentRepository
import com.example.gestordeinventario.ui.common.CheckboxInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentPendingsViewModel(padron: String): ViewModel() {
    private val _student = MutableStateFlow(Student("", "", ArrayList(), ""))
    val student: StateFlow<Student> = _student

    private val _isLoading = MutableStateFlow(false)
    val isLoading : StateFlow<Boolean> = _isLoading

    init{
        viewModelScope.launch {
            _isLoading.value = true
            getUser(padron)
            _isLoading.value = false
        }
    }
    private suspend fun getUser(padron: String){
        val result: Student = withContext(Dispatchers.IO) {
            StudentRepository().getById(padron) ?: Student("Error", "", ArrayList(), "")
        }
        _student.value = result
    }
}