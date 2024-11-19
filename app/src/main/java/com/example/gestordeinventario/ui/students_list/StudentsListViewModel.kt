package com.example.gestordeinventario.ui.students_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestordeinventario.model.Student
import com.example.gestordeinventario.repository.StudentRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class StudentsListViewModel: ViewModel() {
    private var _studentsList = MutableLiveData< List<Student> >()
    val studentsList : LiveData< List<Student> > = _studentsList

    init {
        getUsers()
    }
    private fun getUsers(){
        viewModelScope.launch{
            val result: List<Student> = withContext(Dispatchers.IO) {
                StudentRepository().getAll()
            }
            _studentsList.value = result
        }
    }
}