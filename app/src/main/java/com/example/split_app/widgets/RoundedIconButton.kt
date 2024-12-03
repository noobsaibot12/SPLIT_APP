package com.example.split_app.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val IconbuttonSizeModiefier = Modifier.size( 40.dp )

@Composable
fun RoundIconButton (

    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit,
    tint: Color = Color.Black.copy( alpha = 0.8f ),
    backgroundColor: Color = Color.White,
    elevation: Dp = 4.dp

) {

    Card (

        modifier = modifier
            .padding(all = 4.dp)
            .clickable {

                onClick.invoke()

            }.then( IconbuttonSizeModiefier ),
        shape = CircleShape,
        elevation = CardDefaults.cardElevation( elevation ),
        colors = CardDefaults.cardColors( backgroundColor ),

    ) {

        Box (

            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center

        ) {

            Icon(

                imageVector = imageVector ,
                contentDescription = "Plus or Minus Icon!!" ,
                tint = tint

            )

        }

    }

}