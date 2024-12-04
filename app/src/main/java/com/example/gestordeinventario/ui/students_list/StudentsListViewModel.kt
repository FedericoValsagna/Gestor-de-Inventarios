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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            _isLoading.value = true
            getUsers()
            _isLoading.value = false
        }
    }
    private suspend fun getUsers(){
            val result: List<Student> = withContext(Dispatchers.IO) {
                StudentRepository().getAll()
            }
            _studentsList.value = result
    }
}