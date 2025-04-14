package com.example.meugastocombustivel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.meugastocombustivel.model.Abastecimento
import com.example.meugastocombustivel.model.AbastecimentoViewModel
import com.example.meugastocombustivel.model.AbastecimentoViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: AbastecimentoViewModel by viewModels {
        AbastecimentoViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var telaAtual by remember { mutableStateOf("lista") }
            var itemEdicao by remember { mutableStateOf<Abastecimento?>(null) }

            MaterialTheme {
                when (telaAtual) {
                    "lista" -> TelaPrincipal(
                        viewModel,
                        abrirCadastro = {
                            itemEdicao = null
                            telaAtual = "cadastro"
                        },
                        abrirRelatorio = { telaAtual = "relatorio" },
                        onEditar = { abastecimento ->
                            itemEdicao = abastecimento
                            telaAtual = "cadastro"
                        },
                        onDeletar = { abastecimento ->
                            viewModel.deletar(abastecimento)
                        }
                    )
                    "cadastro" -> TelaCadastro(viewModel, itemEdicao) { telaAtual = "lista" }
                    "relatorio" -> TelaRelatorio(viewModel) { telaAtual = "lista" }
                }
            }
        }
    }
}

@Composable
fun TelaPrincipal(
    viewModel: AbastecimentoViewModel,
    abrirCadastro: () -> Unit,
    abrirRelatorio: () -> Unit,
    onEditar: (Abastecimento) -> Unit,
    onDeletar: (Abastecimento) -> Unit
) {
    val lista = viewModel.lista

    Scaffold(
        floatingActionButton = {
            Column {
                FloatingActionButton(onClick = abrirCadastro) {
                    Text("+")
                }
                Spacer(modifier = Modifier.height(16.dp))
                FloatingActionButton(onClick = abrirRelatorio) {
                    Text("Relatório")
                }
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(lista) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Data: ${item.data}")
                        Text("Posto: ${item.posto}")
                        Text("Litros: ${item.litros}")
                        Text("Valor: R$ ${item.valor}")

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(onClick = { onEditar(item) }) {
                                Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar")
                            }
                            IconButton(onClick = { onDeletar(item) }) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Deletar")
                            }
                        }
                    }
                }
            }
        }
    }
}
