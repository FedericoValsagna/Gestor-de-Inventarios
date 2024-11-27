package com.example.gestordeinventario.ui.pendings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestordeinventario.model.Student
import com.example.gestordeinventario.repository.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PendingsViewModel(padron: String): ViewModel() {
    private val _studentsList = MutableLiveData< List<Student> >()
    private val _student: MutableLiveData<Student> = MutableLiveData<Student>()
    val student: LiveData<Student> = _student
    init{
        getUser(padron)
    }
    private fun getUser(padron: String){
        viewModelScope.launch{
            val result: Student = withContext(Dispatchers.IO) {
                StudentRepository().getById(padron) ?: Student("Error", "", emptyList(), "")
            }
            _student.value = result
        }
    }

}