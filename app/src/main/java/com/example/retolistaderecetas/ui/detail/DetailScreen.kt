package com.example.retolistaderecetas.ui.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dining
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.retolistaderecetas.R.*
import com.example.retolistaderecetas.domain.model.Recipe
import com.example.retolistaderecetas.ui.Screen
import com.example.retolistaderecetas.ui.detail.components.RecipeImage
import com.example.retolistaderecetas.ui.detail.components.DetailProperty
import com.example.retolistaderecetas.ui.detail.components.mirroringBackIcon

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    upPress: () -> Unit,
    navController: NavController
) {
    val state = viewModel.state
    DetailContent(
        character = state.recipe,
        upPress = upPress,
        navController = navController
    )
}

@Composable
private fun DetailContent(
    modifier: Modifier = Modifier,
    character: Recipe?,
    upPress: () -> Unit,
    navController: NavController
) {
    Box(modifier.fillMaxSize()) {
        Column {
            Header(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                character = character
            )
            Body(character = character, navController = navController)
        }
        Up(upPress)
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier,
    character: Recipe?,
) {
    Column(
        modifier = modifier.background(Color(0xffffe0b2)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RecipeImage(image = character?.image)
    }
}

@SuppressLint("QueryPermissionsNeeded")
@Composable
private fun Body(character: Recipe?, navController: NavController = rememberNavController()) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            textAlign = TextAlign.Center,
            text = character?.name ?: "",
            fontFamily = FontFamily.Serif,
            fontStyle = FontStyle.Italic,
            style = MaterialTheme.typography.h5,
            color = Color.White
        )
        DetailProperty(label = stringResource(string.name), value = character?.name, imageVector = Icons.Filled.Dining)
        DetailProperty(label = stringResource(string.description), value = character?.description, imageVector = Icons.Outlined.AutoStories)
        DetailProperty(label = stringResource(string.location), value = character?.location?.latitude, imageVector = Icons.Outlined.LocationOn)
        Spacer(modifier = Modifier.height(8.dp))
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(15.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            TextButton(
                onClick = {
                    val latitude = character?.location?.latitude ?: "0.0"
                    val longitude = character?.location?.longitude ?: "0.0"
                    navController.navigate(Screen.Map.createRoute(latitude = latitude.toDouble(),longitude = longitude.toDouble()))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.textButtonColors(backgroundColor = Color(0xffFF5722))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = "Icono de ubicación",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Ver origen de la receta",
                        color = Color.White
                    )
                }
            }
        }

    }
}

@Composable
private fun Up(upPress: () -> Unit) {
    IconButton(
        onClick = upPress,
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 10.dp)
            .size(36.dp)
    ) {
        Icon(
            imageVector = mirroringBackIcon(),
            tint = Color(0xffffffff),
            contentDescription = null
        )
    }
}