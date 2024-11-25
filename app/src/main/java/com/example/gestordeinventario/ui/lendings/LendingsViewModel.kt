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
import java.util.Calendar
import java.util.Date

class LendingsViewModel(padron: String): ViewModel() {
    private val _student: MutableLiveData<Student> = MutableLiveData<Student>()
    val student: LiveData<Student> = _student

    private var _elementsList = MutableLiveData< List<Element> >()
    val elementsList : LiveData< List<Element> > = _elementsList

    private var _pendingElements = MutableLiveData< ArrayList<PendingElement> >()
    val pendingElements : LiveData< ArrayList<PendingElement> > = _pendingElements

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
        val pendings = _pendingElements.value
        var i = 0

        if(pendings != null) {
            pendingIn.quantity++
            pendings.forEachIndexed { index, pending ->
                if (pending.element.name == pendingIn.element.name) {
                    pendings[index] = pending
                    i = index
                    println("Debug Trace index: ${index}")
                }
            }
            println("Debug Trace pendingIn.quantity: ${pendingIn.quantity}")
            println("Debug Trace pendings.get(i).quantity: ${pendings.get(i).quantity}")
            _pendingElements.value = pendings!!
            println("Debug Trace _pendingElements.value.get(i).quantity: ${_pendingElements.value?.get(i)?.quantity}")
        }
    }

    fun pendingElementQuantityDec(elementName: String){
        val pendings = pendingElements.value

        pendings?.forEach { pending ->
            if(pending.element.name == elementName){
                if(pending.quantity > 0){
                    pending.quantity--
                }
            }
        }
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