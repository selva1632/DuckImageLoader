package com.selva.onlinestore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.selva.onlinestore.presentation.MainViewModel
import com.selva.onlinestore.screen.Screen
import com.selva.onlinestore.ui.theme.OnlineStoreTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OnlineStoreTheme {
                val state by viewModel.duckUrlState.collectAsStateWithLifecycle()
                Screen(state = state, onEvent = viewModel::handleEvent)
            }
        }
    }
}