package com.ese12.gilgitapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ese12.gilgitapp.domain.models.ProductModel
import com.ese12.gilgitapp.repo.MainRepository
import com.ese12.gilgitapp.repo.MainRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    var mainRepository: MainRepository = MainRepositoryImpl()
    private val _allProducts = MutableStateFlow<List<ProductModel>>(emptyList())
    val allProducts: StateFlow<List<ProductModel>> = _allProducts

    init {
        // Load all products into _allProducts
        viewModelScope.launch {
            val products = mainRepository.collectAllProductsList()
            products.collect {
                _allProducts.value = it
            }
        }
    }

    suspend fun searchProduct(query: String): List<ProductModel> {
        return withContext(Dispatchers.Default) {
            _allProducts.value.filter { it.title.contains(query, true) }
        }
    }
}
