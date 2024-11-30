package com.example.gestordeinventario.ui.pendings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestordeinventario.model.PendingElement
import com.example.gestordeinventario.model.Student
import com.example.gestordeinventario.repository.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class PendingCheckedInfo(
    val isChecked: Boolean,
    val pendingElementIndex: Int
)

class PendingsViewModel(padron: String): ViewModel() {
    private val _student = MutableStateFlow(Student("", "", emptyList(), ""))
    val student: StateFlow<Student> = _student

    private val _pendingsCheckboxes = MutableStateFlow(ArrayList<PendingCheckedInfo>())
    val pendingsCheckboxes: StateFlow<ArrayList<PendingCheckedInfo>> = _pendingsCheckboxes

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
        val pendingList = ArrayList<PendingCheckedInfo>()
        _student.value.pendingDevolutions.forEachIndexed {index, _ ->
            pendingList.add(PendingCheckedInfo(false, index))
        }
        _pendingsCheckboxes.value = pendingList
    }

}