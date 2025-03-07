package com.example.thisisawkward.components

import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import com.example.thisisawkward.R
import com.example.thisisawkward.ui.theme.*

@Preview
@Composable
fun OnDateAnimation() {
    // Animation state for rotating the rings
    val infiniteTransition = rememberInfiniteTransition()

    val rotationAngle1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 18000, easing = LinearEasing), // Slow rotation
            repeatMode = RepeatMode.Restart
        )
    )

    val rotationAngle2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 15000, easing = LinearEasing), // Slow rotation
            repeatMode = RepeatMode.Restart
        )
    )

    val rotationAngle3 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 17000, easing = LinearEasing), // Slow rotation
            repeatMode = RepeatMode.Restart
        )
    )

    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(300.dp)) {
        // Centered image
        Image(
            modifier = Modifier.size(250.dp),
            painter = painterResource(id = R.drawable.on_date_animation_graphic),
            contentDescription = "On Date Animation",
            contentScale = ContentScale.Fit,
        )

        // First animated ring
        Box(modifier = Modifier.size(300.dp)
            .rotate(rotationAngle1),
            contentAlignment = Alignment.Center) {
            Canvas(
                modifier = Modifier
                    .size(260.dp)
                    .offset(x= (-7).dp)
            ) {
                drawCircle(
                    color = Purple40,
                    style = Stroke(width = 3.dp.toPx())
                )
            }
        }

        // Second animated ring
        Box(modifier = Modifier.size(300.dp)
            .rotate(-rotationAngle2),
            contentAlignment = Alignment.Center) {
            Canvas(
                modifier = Modifier
                    .size(257.dp)
                    .offset(x= (5).dp)
            ) {
                drawCircle(
                    color = PurpleGrey40,
                    style = Stroke(width = 3.dp.toPx())
                )
            }
        }

        // Third animated rind
        Box(modifier = Modifier.size(300.dp)
            .rotate(rotationAngle3),
            contentAlignment = Alignment.Center) {
            Canvas(
                modifier = Modifier
                    .size(265.dp)
                    .offset(y = (2).dp)
            ) {
                drawCircle(
                    color = ButtonRed,
                    style = Stroke(width = 3.dp.toPx())
                )
            }
        }

    }
}