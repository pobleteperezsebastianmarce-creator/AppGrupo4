package com.example.appgrupo4.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appgrupo4.viewmodel.PostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(viewModel: PostViewModel) {
    val posts by viewModel.postList.collectAsState()
    val mensaje by viewModel.mensajeEstado.collectAsState()
    var tituloInput by remember { mutableStateOf("") }
    var cuerpoInput by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Listado de Posts") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = tituloInput,
                onValueChange = { tituloInput = it },
                label = { Text("Título nuevo post") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = cuerpoInput,
                onValueChange = { cuerpoInput = it },
                label = { Text("Contenido") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    if (tituloInput.isNotBlank() && cuerpoInput.isNotBlank()) {
                        viewModel.agregarPost(tituloInput, cuerpoInput)
                        tituloInput = ""
                        cuerpoInput = ""
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Crear Post (POST)")
            }

            if (mensaje.isNotEmpty()) {
                Text(text = mensaje, color = MaterialTheme.colorScheme.primary)
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(posts) { post ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Título: ${post.title}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = post.body,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                TextButton(onClick = { viewModel.actualizarPost(post) }) {
                                    Text("Editar (PUT)")
                                }
                                TextButton(onClick = { viewModel.eliminarPost(post.id) }) {
                                    Text("Borrar (DELETE)", color = MaterialTheme.colorScheme.error)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}