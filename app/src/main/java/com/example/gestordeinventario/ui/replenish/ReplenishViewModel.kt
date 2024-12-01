package com.example.gestordeinventario.ui.replenish

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestordeinventario.model.Element
import com.example.gestordeinventario.model.Provider
import com.example.gestordeinventario.repository.ProviderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.ArrayList

class ReplenishViewModel(providerName: String): ViewModel() {
    private val _provider = MutableLiveData<Provider>()
    val provider = _provider
//    private val _elementsList = MutableLiveData<List<Element>>()
//    val elementsList = _elementsList
    init{
        getElements(providerName)
    }
    private fun getElements(providerName: String){
        viewModelScope.launch {
            val result: Provider = withContext(Dispatchers.IO){
                ProviderRepository().getByName(providerName)?: Provider("Error", ArrayList<Element>())
            }
            _provider.value = result
            // _elementsList.value = result.elements
        }
    }
}