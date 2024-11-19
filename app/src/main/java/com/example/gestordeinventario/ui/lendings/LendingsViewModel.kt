package com.example.gestordeinventario.ui.students_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestordeinventario.model.Student
import com.example.gestordeinventario.repository.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LendingsViewModel(padron: String): ViewModel() {
    private val _studentsList = MutableLiveData< List<Student> >()
    val studentsList : LiveData< List<Student> > = _studentsList
    private val _student: MutableLiveData<Student> = MutableLiveData<Student>()

    init{
        getUser(padron)
    }
    val student: LiveData<Student> = _student

    public fun addStudent(student: Student) {
        _studentsList.value = _studentsList.value?.plus(student) ?: mutableListOf(student)
    }

    private fun getUser(padron: String){
        viewModelScope.launch{
            val result: Student = withContext(Dispatchers.IO) {
                StudentRepository().getById(padron) ?: Student("Error", "", emptyList())
            }
            _student.value = result
        }
    }

}