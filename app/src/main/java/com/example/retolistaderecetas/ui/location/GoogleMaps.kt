package com.example.retolistaderecetas.ui.location

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay

@Composable
fun GoogleMaps(latitud: String, longitud: String){
    val latitude = latitud.toDoubleOrNull() ?: 0.0
    val longitude = longitud.toDoubleOrNull() ?: 0.0
    Box(contentAlignment = Alignment.Center) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(latitude, longitude), 1f)
        }

        LaunchedEffect(key1 = "Animation") {
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(latitude, longitude),
                    16.0f
                ),
                2000
            )
            delay(4000L)
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = LatLng(latitude, longitude)),
                title = "Mi receta 🧆🌮",
                snippet = "Origen!"
            )
        }
    }
}
