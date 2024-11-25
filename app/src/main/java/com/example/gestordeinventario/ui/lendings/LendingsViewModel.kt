package com.example.gestordeinventario.ui.students_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
        _pendingElements.value = ArrayList()

        _elementsList.value?.forEach { element ->
            _pendingElements.value?.add(PendingElement(0, element, Date()))
            if(_pendingElements.value?.isEmpty()?:true){
                    println("Debug trace: NO OK!")
                }
        }
    }

    //Sobrecarga de plus de date
    fun Date.plus(days: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = this
        calendar.add(Calendar.DAY_OF_MONTH, days)
        return calendar.time
    }

    public fun pendingElementQuantityInc(elementName: String){
        val pendings = pendingElements.value

        pendings?.forEach { pending ->
            if(pending.element.name == elementName){
                pending.quantity++
            }
        }
    }

    public fun pendingElementQuantityDec(elementName: String){
        val pendings = pendingElements.value

        pendings?.forEach { pending ->
            if(pending.element.name == elementName){
                if(pending.quantity > 0)
                pending.quantity--
            }
        }
    }

    public fun pendingElementDaysInc(elementName: String){
        val pendings = pendingElements.value

        pendings?.forEach { pending ->
            if(pending.element.name == elementName){
                pending.devolutionDate.time.plus(1)
            }
        }
    }

    public fun pendingElementDaysDec(elementName: String){
        val pendings = pendingElements.value

        pendings?.forEach { pending ->
            if(pending.element.name == elementName){
                pending.devolutionDate.time.plus(-1)
            }
        }
    }
}