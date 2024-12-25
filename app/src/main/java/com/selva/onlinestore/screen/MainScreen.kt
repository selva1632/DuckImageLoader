package com.selva.onlinestore.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Screen(
    state: UiState,
    onEvent: (UiEvent) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(state.error) {
        if (!state.error.isNullOrEmpty()) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {
                onEvent(UiEvent.GetDuckUrl)
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(state.url)
            .size(Size.ORIGINAL)
            .build()
    ).state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(200.dp)
                .border(1.dp, Color.Black, RectangleShape),
            contentAlignment = Alignment.Center
        ) {
            println(
                when (imageState) {
                    is AsyncImagePainter.State.Error -> {
                        state.error?.let { Text(text = it) }
                    }

                    is AsyncImagePainter.State.Loading -> {
                        CircularProgressIndicator()
                    }

                    is AsyncImagePainter.State.Success -> {
                        Image(
                            painter = imageState.painter,
                            contentDescription = null,
                            modifier = Modifier.matchParentSize()
                        )
                    }

                    else -> {}
                }
            )
        }

        Button(
            onClick = { onEvent(UiEvent.GetDuckUrl) }
        ) {
            Text(text = "Load New Image")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    Screen(state = UiState(true)) {}
}