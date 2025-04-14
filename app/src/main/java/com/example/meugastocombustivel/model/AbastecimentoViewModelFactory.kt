package com.example.meugastocombustivel.model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AbastecimentoViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AbastecimentoViewModel::class.java)) {
            return AbastecimentoViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
