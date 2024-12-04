package com.example.gestordeinventario.ui.providers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestordeinventario.model.Provider
import com.example.gestordeinventario.repository.ProviderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProvidersViewModel: ViewModel() {
    private var _providersList = MutableStateFlow(ArrayList<Provider>())
    val providersList : StateFlow<ArrayList<Provider>> = _providersList

    private val _isLoading = MutableStateFlow(false)
    val isLoading : StateFlow<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            _isLoading.value = true
            getProviders()
            _isLoading.value = false
        }
    }

    private suspend fun getProviders() {
        _providersList.value = ArrayList(ProviderRepository().getAll())
    }
}