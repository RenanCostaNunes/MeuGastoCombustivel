package com.example.meugastocombustivel

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.meugastocombustivel.model.AbastecimentoViewModel

@Composable
fun TelaRelatorio(viewModel: AbastecimentoViewModel, onVoltar: () -> Unit) {
    val lista = viewModel.lista

    val totalLitros = lista.sumOf { it.litros }
    val totalValor = lista.sumOf { it.valor }
    val abastecimentos = lista.size
    val precoMedio = if (totalLitros > 0) totalValor / totalLitros else 0.0

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Relatório", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Total de abastecimentos: $abastecimentos")
        Text("Total de litros abastecidos: $totalLitros")
        Text("Total gasto: R$ $totalValor")
        Text("Preço médio por litro: R$ %.2f".format(precoMedio))

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onVoltar, modifier = Modifier.fillMaxWidth()) {
            Text("Voltar")
        }
    }
}
