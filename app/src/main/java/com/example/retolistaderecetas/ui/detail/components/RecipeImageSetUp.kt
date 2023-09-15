package com.example.retolistaderecetas.ui.detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.retolistaderecetas.R

@Composable
fun RecipeImage(image: String?) {
    ImageContainer {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .crossfade(true)
                    .error(R.drawable.empty_plate)
                    .placeholder(R.drawable.empty_plate)
                    .data(image)
                    .build()
            )
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.FillWidth, // Cambiado a FillWidth
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun ImageContainer(
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        content()
    }
}
