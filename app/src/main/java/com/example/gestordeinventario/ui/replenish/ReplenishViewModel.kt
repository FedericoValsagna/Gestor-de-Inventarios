package com.example.gestordeinventario.ui.replenish

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestordeinventario.model.Element
import com.example.gestordeinventario.model.Provider
import com.example.gestordeinventario.repository.ElementRepository
import com.example.gestordeinventario.repository.ProviderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.ArrayList

class ReplenishViewModel(providerName: String): ViewModel() {
    private val _provider = MutableLiveData<Provider>()
    val provider = _provider

    private val _elements = MutableLiveData<ArrayList<Element>>()
    val elements = _elements
    init{
        fetchProvider(providerName)
    }
    private fun fetchProvider(providerName: String){
        viewModelScope.launch {
            val result: Provider = withContext(Dispatchers.IO){
                ProviderRepository().getByName(providerName)?: Provider("Error", ArrayList<Element>())
            }
            _provider.value = result
            _elements.value = result.elements
        }
    }
    fun pendingElementQuantityInc(element: Element) {
        val newElements = ArrayList<Element>()
        elements.value?.forEach {
            if (it.name == element.name) {
                val newElement = Element(element.name, element.totalQuantity + 1)
                newElements.add(newElement)
            } else {
                newElements.add(it)
            }
        }
        elements.value = newElements
    }
    fun pendingElementQuantityDec(element: Element) {
        val newElements = ArrayList<Element>()
        elements.value?.forEach {
            if (it.name == element.name) {
                val newElement = Element(element.name, element.totalQuantity - 1)
                newElements.add(newElement)
            } else {
                newElements.add(it)
            }
        }
        elements.value = newElements
        provider.value?.elements = newElements
    }
    fun submitReplenish() {
        viewModelScope.launch{
            elements.value?.forEach{ element ->
                ElementRepository().save(element)
            }
        }
    }

}