package com.example.gestordeinventario.ui.devolutions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestordeinventario.model.Element
import com.example.gestordeinventario.model.PendingElement
import com.example.gestordeinventario.model.Student
import com.example.gestordeinventario.repository.PendingElementRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class DevolutionsViewModel: ViewModel() {

    private val _student = MutableStateFlow(Student("", "", emptyList(), ""))
    val student: StateFlow<Student> = _student

    private val _devolutionsList = MutableStateFlow(List(0){PendingElement(0, Element("", 0), Date())})
    val devolutionsList : StateFlow<List<PendingElement> > = _devolutionsList

    init {
        getDevolutions()
    }

    private fun getDevolutions(){
        viewModelScope.launch{
            val result: List<PendingElement> = withContext(Dispatchers.IO) {
                PendingElementRepository().getAll()
            }
            _devolutionsList.value = result
        }
    }
}