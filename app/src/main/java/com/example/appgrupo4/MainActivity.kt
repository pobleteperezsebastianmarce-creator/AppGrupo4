package com.example.appgrupo4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appgrupo4.ui.screens.PostScreen
import com.example.appgrupo4.ui.theme.AppGrupo4Theme
import com.example.appgrupo4.viewmodel.PostViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppGrupo4Theme {
                val postViewModel: PostViewModel = viewModel()
                PostScreen(viewModel = postViewModel)
            }
        }
    }
}