package com.example.meugastocombustivel.model

import androidx.room.*

@Dao
interface AbastecimentoDao {

    @Insert
    suspend fun inserir(abastecimento: Abastecimento)

    @Query("SELECT * FROM abastecimentos ORDER BY id DESC")
    suspend fun listarTodos(): List<Abastecimento>

    @Delete
    suspend fun deletar(abastecimento: Abastecimento)
}
