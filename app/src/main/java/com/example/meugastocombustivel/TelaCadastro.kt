package com.example.meugastocombustivel

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.meugastocombustivel.model.Abastecimento
import com.example.meugastocombustivel.model.AbastecimentoViewModel
import java.text.NumberFormat
import java.util.*

@Composable
fun TelaCadastro(
    viewModel: AbastecimentoViewModel,
    abastecimentoEdicao: Abastecimento?,
    onVoltar: () -> Unit
) {
    var data by remember { mutableStateOf(TextFieldValue(abastecimentoEdicao?.data ?: "")) }
    var posto by remember { mutableStateOf(abastecimentoEdicao?.posto ?: "") }
    var litros by remember { mutableStateOf(abastecimentoEdicao?.litros?.toString() ?: "") }
    var valor by remember { mutableStateOf(TextFieldValue(abastecimentoEdicao?.valor?.toString() ?: "")) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = if (abastecimentoEdicao == null) "Novo Abastecimento" else "Editar Abastecimento",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = data,
            onValueChange = { data = formatarDataDinamica(it) },
            label = { Text("Data (dd/MM/yyyy)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = posto,
            onValueChange = { posto = it },
            label = { Text("Posto") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = litros,
            onValueChange = { litros = it },
            label = { Text("Litros") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = valor,
            onValueChange = { valor = formatarValorDinamico(it) },
            label = { Text("Valor (R$)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (data.text.isNotBlank() && posto.isNotBlank() && litros.isNotBlank() && valor.text.isNotBlank()) {
                    val valorDouble = valor.text.replace(Regex("[^\\d]"), "").toDoubleOrNull()?.div(100) ?: 0.0
                    val novoAbastecimento = Abastecimento(
                        id = abastecimentoEdicao?.id ?: 0,
                        data = data.text,
                        posto = posto,
                        litros = litros.toDoubleOrNull() ?: 0.0,
                        valor = valorDouble
                    )
                    viewModel.adicionar(novoAbastecimento)
                    onVoltar()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salvar")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onVoltar,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text("Voltar")
        }
    }
}

fun formatarDataDinamica(textFieldValue: TextFieldValue): TextFieldValue {
    val digits = textFieldValue.text.filter { it.isDigit() }
    val builder = StringBuilder()

    for (i in digits.indices) {
        builder.append(digits[i])
        if (i == 1 || i == 3) builder.append('/')
    }

    val formatted = builder.toString()

    return TextFieldValue(
        text = formatted,
        selection = TextRange(formatted.length)
    )
}

fun formatarValorDinamico(textFieldValue: TextFieldValue): TextFieldValue {
    val digits = textFieldValue.text.filter { it.isDigit() }

    val valor = if (digits.isNotEmpty()) {
        val parsed = digits.toDouble() / 100
        NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(parsed)
    } else {
        ""
    }

    return TextFieldValue(
        text = valor,
        selection = TextRange(valor.length)
    )
}
