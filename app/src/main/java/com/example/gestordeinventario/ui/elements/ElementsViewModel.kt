package com.example.gestordeinventario.ui.elements

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestordeinventario.model.Element
import com.example.gestordeinventario.repository.ElementRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ElementsViewModel: ViewModel() {
    private var _elementsList = MutableLiveData< List<Element> >()
    val elementsList : LiveData< List<Element> > = _elementsList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            _isLoading.value = true
            getElements()
            _isLoading.value = false
        }
    }

    private suspend fun getElements() {
        val result: List<Element> = withContext(Dispatchers.IO) {
            ElementRepository().getAll()
        }
        _elementsList.value = result
    }
}