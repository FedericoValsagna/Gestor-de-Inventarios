package com.example.gestordeinventario.ui.student_elements

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestordeinventario.model.Element
import com.example.gestordeinventario.repository.ElementRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentElementsViewModel: ViewModel() {
    private var _elementsList = MutableLiveData< List<Element> >()
    val elementsList : LiveData<List<Element>> = _elementsList

    init {
        getElements()
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