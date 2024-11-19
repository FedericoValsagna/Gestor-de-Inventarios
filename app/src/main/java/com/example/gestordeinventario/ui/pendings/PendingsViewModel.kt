package com.example.gestordeinventario.ui.students_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gestordeinventario.model.Student

class PendingsViewModel {
    private val _studentsList = MutableLiveData< List<Student> >()
    val studentsList : LiveData< List<Student> > = _studentsList

    public fun addStudent(student: Student) {
        _studentsList.value = _studentsList.value?.plus(student) ?: mutableListOf(student)
    }
}