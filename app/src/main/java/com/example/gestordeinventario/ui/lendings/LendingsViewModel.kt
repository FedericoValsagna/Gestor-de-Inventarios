package com.example.gestordeinventario.ui.students_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestordeinventario.model.Element
import com.example.gestordeinventario.model.PendingElement
import com.example.gestordeinventario.model.Student
import com.example.gestordeinventario.repository.ElementRepository
import com.example.gestordeinventario.repository.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LendingsViewModel(padron: String): ViewModel() {
    private val _student: MutableLiveData<Student> = MutableLiveData<Student>()
    val student: LiveData<Student> = _student

    private var _elementsList = MutableLiveData< List<Element> >()
    val elementsList : LiveData< List<Element> > = _elementsList

    init {
        getUser(padron)
        getElements()
    }

    private fun getUser(padron: String){
        viewModelScope.launch{
            val result: Student = withContext(Dispatchers.IO) {
                StudentRepository().getById(padron) ?: Student("Error", "", emptyList(), "")
            }
            _student.value = result
        }
    }

    private fun getElements(){
        viewModelScope.launch{
            val result: List<Element> = withContext(Dispatchers.IO) {
                ElementRepository().getAll()
            }
            _elementsList.value = result
        }
    }

}