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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip

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
            GlowCricle(
                modifier = Modifier
                    .offset(x= (-7).dp),
                glowingColor = Purple40,
                glowingRadius = 10.dp,
                circleRadius = 128.dp
            )
        }

        // Second animated ring
        Box(modifier = Modifier.size(300.dp)
            .rotate(-rotationAngle2),
            contentAlignment = Alignment.Center) {
            GlowCricle(
                modifier = Modifier
                    .offset(x= (8).dp),
                glowingColor = PurpleGrey40,
                glowingRadius = 10.dp,
                circleRadius = 132.dp
            )
        }

        // Third animated rind
        Box(modifier = Modifier.size(300.dp)
            .rotate(rotationAngle3),
            contentAlignment = Alignment.Center) {
            GlowCricle(
                modifier = Modifier
                    .offset(x= (-5).dp),
                glowingColor = ButtonRed,
                glowingRadius = 10.dp,
                circleRadius = 130.dp
            )
        }

    }
}



@Composable
fun GlowCricle(
    modifier: Modifier = Modifier,
    glowingColor: Color,
    circleRadius: Dp = 0.dp,
    glowingRadius: Dp = 20.dp,
    xShifting: Dp = 0.dp,
    yShifting: Dp = 0.dp,
) {
    Box(
        modifier = modifier
            .drawBehind {
                val size = this.size
                drawContext.canvas.nativeCanvas.apply {
                    drawCircle(
                        size.width,
                        size.height,
                        circleRadius.toPx(),
                        Paint().apply {
                            style = android.graphics.Paint.Style.STROKE
                            strokeWidth = 5f
                            color = glowingColor.toArgb()
                            setShadowLayer(
                                glowingRadius.toPx(),
                                xShifting.toPx(), yShifting.toPx(),
                                glowingColor.toArgb()
                            )
                        }
                    )
                }
            }
    ) {
    }
}

@Preview
@Composable
fun GlowCirclePreview(){
    Box(Modifier.size(200.dp),
        contentAlignment = Alignment.Center)
    {
        GlowCricle(
            glowingColor = Color.Yellow,
            glowingRadius = 10.dp,
            circleRadius = 50.dp
        )
    }
}