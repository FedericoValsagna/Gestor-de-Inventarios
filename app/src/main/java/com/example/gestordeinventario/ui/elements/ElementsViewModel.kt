package com.example.gestordeinventario.ui.elements

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestordeinventario.model.Element
import com.example.gestordeinventario.model.Student
import com.example.gestordeinventario.repository.ElementRepository
import com.example.gestordeinventario.repository.StudentRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ElementsViewModel: ViewModel() {
    private var _elementsList = MutableLiveData< List<Element> >()
    val elementsList : LiveData< List<Element> > = _elementsList

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