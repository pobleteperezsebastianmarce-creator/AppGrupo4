package com.example.appgrupo4.repository
import com.example.appgrupo4.data.model.Post
import com.example.appgrupo4.data.remote.RetrofitInstance

class PostRepository {
    suspend fun getPosts(): List<Post> = RetrofitInstance.api.getPosts()
    suspend fun createPost(post: Post): Post = RetrofitInstance.api.createPost(post)
    suspend fun updatePost(id: Int, post: Post): Post = RetrofitInstance.api.updatePost(id, post)
    suspend fun deletePost(id: Int): Boolean = RetrofitInstance.api.deletePost(id).isSuccessful
}