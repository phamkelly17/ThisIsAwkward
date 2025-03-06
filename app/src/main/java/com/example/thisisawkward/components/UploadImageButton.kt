package com.example.thisisawkward.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thisisawkward.R

@Preview
@Composable
fun UploadImageButton() {
    Column (modifier = Modifier.padding(vertical = 4.dp)){
        Text(
            text = "Upload Photo",
            color = Color.DarkGray,
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 4.dp)
        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(4.dp)
            .drawBehind {
                drawRoundRect(
                    color = Color.DarkGray,
                    style = Stroke(
                        width = 4f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    ),
                    cornerRadius = CornerRadius((45 / 2).dp.toPx())
                )
            }
            .drawBehind {
                drawRoundRect(
                    color = Color.White,
                    cornerRadius = CornerRadius((45 / 2).dp.toPx())
                )
            }
            .clip(shape = RoundedCornerShape((45/2).dp)),
            contentAlignment = Alignment.Center
        ){
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                Image(
                    painter = painterResource(id = R.drawable.add_date_button),
                    contentDescription = "upload photo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(35.dp)
                )
            }
        }
    }
}