package com.example.internet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.internet.data.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _status = MutableLiveData<ProductApiStatus>()
    val status: LiveData<ProductApiStatus> get() = _status

    private val _products = MutableLiveData<List<ProductResponse>>()
    val products: LiveData<List<ProductResponse>> get() = _products

    init {
        getNetworkProducts()
    }

    fun retry() {
        getNetworkProducts()
    }

    private fun getNetworkProducts() {
        viewModelScope.launch {
            _status.value = ProductApiStatus.LOADING
            try {
                _products.value = productRepository.getProducts()
                _status.value = ProductApiStatus.DONE
            } catch (e: Exception) {
                _products.value = emptyList()
                _status.value = ProductApiStatus.ERROR
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ProductApplication)
                val productRepository = application.container.productRepository
                ProductViewModel(productRepository = productRepository)
            }
        }
    }
}
