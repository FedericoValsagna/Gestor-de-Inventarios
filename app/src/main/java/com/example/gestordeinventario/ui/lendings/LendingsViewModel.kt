package com.example.gestordeinventario.ui.lendings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestordeinventario.model.Element
import com.example.gestordeinventario.model.PendingElement
import com.example.gestordeinventario.model.Student
import com.example.gestordeinventario.repository.ElementRepository
import com.example.gestordeinventario.repository.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Date

class LendingsViewModel(padron: String): ViewModel() {
    private val _student = MutableStateFlow(Student("", "", emptyList(), ""))
    val student: StateFlow<Student> = _student

    private val _elementsList = MutableStateFlow(List(0) { Element("", 0) })

    private val _pendingElements = MutableStateFlow(ArrayList<PendingElement>())
    val pendingElements: StateFlow<ArrayList<PendingElement>> = _pendingElements

    private val _actualDate = Date()

    init {
        getUser(padron)
        getElements()
    }

    private fun getUser(padron: String) {
        viewModelScope.launch {
            val result: Student = withContext(Dispatchers.IO) {
                StudentRepository().getById(padron) ?: Student("Error", "", emptyList(), "")
            }
            _student.value = result
        }
    }

    private fun getElements() {
        viewModelScope.launch {
            val result: List<Element> = withContext(Dispatchers.IO) {
                ElementRepository().getAll()
            }
            _elementsList.value = result
            createPendingList()
        }
    }

    private fun createPendingList() {
        val pendingList = ArrayList<PendingElement>()
        _elementsList.value.forEach { element ->
            pendingList.add(PendingElement(0, element, _actualDate))
        }
        _pendingElements.value = pendingList
    }

    //Sobrecarga de plus de date
    fun Date.plus(days: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = this
        calendar.add(Calendar.DAY_OF_MONTH, days)
        return calendar.time
    }

    fun pendingElementQuantityInc(pendingIn: PendingElement) {
        val pendings = ArrayList<PendingElement>()

        _pendingElements.value.forEach { pending ->
            if (pending.element.name == pendingIn.element.name) {
                val newPending =
                    PendingElement(pending.quantity + 1, pending.element, pending.devolutionDate)
                pendings.add(newPending)
            } else {
                pendings.add(pending)
            }
        }
        _pendingElements.value = pendings
    }

    fun pendingElementQuantityDec(pendingIn: PendingElement) {
        val pendings = ArrayList<PendingElement>()

        _pendingElements.value.forEach { pending ->
            if (pending.element.name == pendingIn.element.name && pending.quantity > 0) {
                val newPending =
                    PendingElement(pending.quantity - 1, pending.element, pending.devolutionDate)
                pendings.add(newPending)
            } else {
                pendings.add(pending)
            }
        }
        _pendingElements.value = pendings
    }

    fun pendingElementDaysInc(pendingIn: PendingElement) {
        val pendings = ArrayList<PendingElement>()

        _pendingElements.value.forEach { pending ->
            if (pending.element.name == pendingIn.element.name) {
                val newPending = PendingElement(
                    pending.quantity,
                    pending.element,
                    pending.devolutionDate.plus(1)
                )
                pendings.add(newPending)
            } else {
                pendings.add(pending)
            }
        }
        _pendingElements.value = pendings
    }

    fun pendingElementDaysDec(pendingIn: PendingElement) {
        val pendings = ArrayList<PendingElement>()

        _pendingElements.value.forEach { pending ->
            if (pending.element.name == pendingIn.element.name && pending.devolutionDate > _actualDate) {
                val newPending = PendingElement(
                    pending.quantity,
                    pending.element,
                    pending.devolutionDate.plus(-1)
                )
                pendings.add(newPending)
            } else {
                pendings.add(pending)
            }
        }
        _pendingElements.value = pendings
    }

    fun differenceInDays(date: Date): Long {
        val diffInMillis = date.time - _actualDate.time // Diferencia en milisegundos

        if(diffInMillis < 0) {
            return 0
        }
        else {
            return diffInMillis / (1000 * 60 * 60 * 24) // Convertir milisegundos a días
        }
    }
}