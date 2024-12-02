package com.example.gestordeinventario.ui.pendings

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestordeinventario.model.PendingElement
import com.example.gestordeinventario.model.Student
import com.example.gestordeinventario.repository.ElementRepository
import com.example.gestordeinventario.repository.PendingElementRepository
import com.example.gestordeinventario.repository.StudentRepository
import com.example.gestordeinventario.ui.common.CheckboxInfo
import com.google.protobuf.Internal.BooleanList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PendingsViewModel(padron: String): ViewModel() {
    private val _student = MutableStateFlow(Student("", "", ArrayList(), ""))
    val student: StateFlow<Student> = _student

    private val _checkboxHashMap = MutableStateFlow(HashMap<String, Pair<PendingElement, Boolean>>())
    val checkboxHashMap: StateFlow<HashMap<String, Pair<PendingElement,Boolean>>> = _checkboxHashMap

    init{
        viewModelScope.launch {
            getUser(padron)
        }
    }
    private suspend fun getUser(padron: String){
        val result: Student = withContext(Dispatchers.IO) {
            StudentRepository().getById(padron) ?: Student("Error", "", ArrayList(), "")
        }
        _student.value = result
        result.pendingDevolutions.forEach{pendingElement ->
            _checkboxHashMap.value[pendingElement.reference.toString()] = Pair(pendingElement, false)
        }
    }
    fun updateCheckbox(boxId: String, value: Boolean) {
        val pair = checkboxHashMap.value[boxId]
        if (pair != null) {
            val (item, _) = pair
            checkboxHashMap.value[boxId] = Pair(item, value)
        }
    }
    fun sumbitDevolutions(){
        viewModelScope.launch {
            val map = checkboxHashMap.value
            map.forEach { _, (pendingElement, isChecked) ->
                if (isChecked) {
                    viewModelScope.launch {
                        pendingElement.resolve()
                        PendingElementRepository().save(pendingElement, _student.value)
                        ElementRepository().save(pendingElement.element)
                        StudentRepository().save(student.value)
                    }
                }
            }
        }
    }
}