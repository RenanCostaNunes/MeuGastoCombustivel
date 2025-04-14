package com.example.meugastocombustivel.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.meugastocombustivel.AppDatabase

class AbastecimentoViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getDatabase(application).abastecimentoDao()

    val lista: SnapshotStateList<Abastecimento> = mutableStateListOf()

    init {
        carregarAbastecimentos()
    }

    private fun carregarAbastecimentos() {
        viewModelScope.launch {
            lista.clear()
            lista.addAll(dao.listarTodos())
        }
    }

    fun adicionar(abastecimento: Abastecimento) {
        viewModelScope.launch {
            if (abastecimento.id == 0) {
                dao.inserir(abastecimento)
            } else {
                dao.atualizar(abastecimento)
            }
            carregarAbastecimentos()
        }
    }

    fun deletar(abastecimento: Abastecimento) {
        viewModelScope.launch {
            dao.deletar(abastecimento)
            carregarAbastecimentos()
        }
    }
}
