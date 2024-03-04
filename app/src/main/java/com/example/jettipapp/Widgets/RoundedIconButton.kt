package com.example.jettipapp.Widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


val IconButtonSizeModifier = Modifier.size(40.dp)
@Composable
fun RoundedIconCircle(
    modifier: Modifier = Modifier,
    imageVector: ImageVector = Icons.Filled.Add,
    tint: Color = Color.Black.copy(alpha = 0.88f),
    shape: Shape = CircleShape,
    backgroundColor: CardColors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    onClick:() ->Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .size(45.dp)
            .clickable { }
            .then(IconButtonSizeModifier),
        shape = shape,
        colors = backgroundColor,
        elevation = elevation
    ) {
        IconButton(onClick = { onClick.invoke() }) {
            Icon(imageVector = imageVector, contentDescription = null,tint = tint)
        }

    }
}




