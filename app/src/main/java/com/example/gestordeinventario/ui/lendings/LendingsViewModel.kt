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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Date

class LendingsViewModel(padron: String): ViewModel() {
    private val _student: MutableLiveData<Student> = MutableLiveData<Student>()
    val student: LiveData<Student> = _student

    private val _elementsList = MutableLiveData< List<Element> >()
    val elementsList : LiveData< List<Element> > = _elementsList

    private val _pendingElements = MutableStateFlow(ArrayList<PendingElement>())
    val pendingElements : StateFlow< ArrayList<PendingElement> > = _pendingElements

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
            createPendingList()
        }
    }

    private fun createPendingList(){
        val pendingList = ArrayList<PendingElement>()
        _elementsList.value?.forEach { element ->
            pendingList.add(PendingElement(0,element, Date()))
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

    fun pendingElementQuantityInc(pendingIn: PendingElement){
        val pendings = ArrayList<PendingElement>()

        _pendingElements.value.forEach{ pending ->
            if (pending.element.name == pendingIn.element.name) {
                val newPending = PendingElement(pending.quantity + 1, pending.element, pending.devolutionDate)
                pendings.add(newPending)
            }
            else{
                pendings.add(pending)
            }
        }
        _pendingElements.value = pendings
    }

    fun pendingElementQuantityDec(pendingIn: PendingElement){
        val pendings = ArrayList<PendingElement>()

        _pendingElements.value.forEach{ pending ->
            if (pending.element.name == pendingIn.element.name && pending.quantity > 0) {
                val newPending = PendingElement(pending.quantity - 1, pending.element, pending.devolutionDate)
                pendings.add(newPending)
            }
            else{
                pendings.add(pending)
            }
        }
        _pendingElements.value = pendings
    }

    fun pendingElementDaysInc(elementName: String){
        val pendings = pendingElements.value

        pendings?.forEach { pending ->
            if(pending.element.name == elementName){
                pending.devolutionDate.time.plus(1)
            }
        }
    }

    fun pendingElementDaysDec(elementName: String){
        val pendings = pendingElements.value

        pendings?.forEach { pending ->
            if(pending.element.name == elementName){
                pending.devolutionDate.time.plus(-1)
            }
        }
    }
}