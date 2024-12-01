package com.example.gestordeinventario.ui.pendings

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestordeinventario.model.PendingElement
import com.example.gestordeinventario.model.Student
import com.example.gestordeinventario.repository.StudentRepository
import com.example.gestordeinventario.ui.common.CheckboxInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PendingsViewModel(padron: String): ViewModel() {
    private val _student = MutableStateFlow(Student("", "", emptyList(), ""))
    val student: StateFlow<Student> = _student

    private val _pendingsCheckboxes = MutableStateFlow(SnapshotStateList<CheckboxInfo>())
    val pendingsCheckboxes: StateFlow<SnapshotStateList<CheckboxInfo>> = _pendingsCheckboxes

    init{
        viewModelScope.launch {
            getUser(padron)
            updatePendingCheckboxes()
        }
    }
    private suspend fun getUser(padron: String){
            val result: Student = withContext(Dispatchers.IO) {
                StudentRepository().getById(padron) ?: Student("Error", "", emptyList(), "")
            }
            _student.value = result
    }
    private fun updatePendingCheckboxes() {
        val pendingList = SnapshotStateList<CheckboxInfo>()
        _student.value.pendingDevolutions.forEachIndexed {index, _ ->
            pendingList.add(CheckboxInfo(false, index))
        }
        println("Debug trace: ${_student.value.pendingDevolutions.size}")
        _pendingsCheckboxes.value = pendingList
    }
}