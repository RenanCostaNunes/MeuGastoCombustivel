package com.example.meugastocombustivel.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "abastecimentos")
data class Abastecimento(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val data: String,
    val posto: String,
    val litros: Double,
    val valor: Double
)
