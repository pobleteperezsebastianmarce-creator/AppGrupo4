package com.example.appgrupo4.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appgrupo4.data.model.Post
import com.example.appgrupo4.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class PostViewModel : ViewModel() {
    private val repository = PostRepository()

    protected val _postList = MutableStateFlow<List<Post>>(emptyList())
    open val postList: StateFlow<List<Post>> = _postList

    private val _mensajeEstado = MutableStateFlow("")
    val mensajeEstado: StateFlow<String> = _mensajeEstado

    init {
        fetchPosts()
    }

    open fun fetchPosts() {
        viewModelScope.launch {
            try {
                _postList.value = repository.getPosts()
            } catch (e: Exception) {
                _mensajeEstado.value = "Error al obtener datos: ${e.localizedMessage}"
            }
        }
    }

    fun agregarPost(titulo: String, cuerpo: String) {
        viewModelScope.launch {
            try {
                val nuevo = Post(userId = 1, title = titulo, body = cuerpo)
                val creado = repository.createPost(nuevo)
                _postList.value = listOf(creado) + _postList.value
                _mensajeEstado.value = "POST exitoso (ID: ${creado.id})"
            } catch (e: Exception) {
                _mensajeEstado.value = "Error POST: ${e.localizedMessage}"
            }
        }
    }

    fun actualizarPost(post: Post) {
        viewModelScope.launch {
            try {
                val editado = post.copy(title = "${post.title} (Editado)")
                repository.updatePost(post.id, editado)
                _postList.value = _postList.value.map { if (it.id == post.id) editado else it }
                _mensajeEstado.value = "PUT exitoso en ID: ${post.id}"
            } catch (e: Exception) {
                _mensajeEstado.value = "Error PUT: ${e.localizedMessage}"
            }
        }
    }

    fun eliminarPost(id: Int) {
        viewModelScope.launch {
            try {
                if (repository.deletePost(id)) {
                    _postList.value = _postList.value.filterNot { it.id == id }
                    _mensajeEstado.value = "DELETE exitoso en ID: $id"
                }
            } catch (e: Exception) {
                _mensajeEstado.value = "Error DELETE: ${e.localizedMessage}"
            }
        }
    }
}