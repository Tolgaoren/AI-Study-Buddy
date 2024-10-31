package com.toren.hackathon24educationproject.presentation.level_panel

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toren.hackathon24educationproject.presentation.theme.Blue40
import com.toren.hackathon24educationproject.presentation.theme.Purple200
import com.toren.hackathon24educationproject.presentation.theme.PurpleGrey100
import com.toren.hackathon24educationproject.presentation.theme.PurpleGrey40
import com.toren.hackathon24educationproject.presentation.theme.PurpleGrey80
import com.toren.hackathon24educationproject.presentation.theme.PurpleGrey90


@Composable
fun LevelPanel(
    fullName: String,
    progress: Float,
    level: Int,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Blue40)
            .padding(start= 20.dp, top = 15.dp, bottom = 15.dp, end = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = fullName,
            style = TextStyle(
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
                )
            )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            LevelCircle(
                level = level
            )
            Spacer(
                modifier = Modifier
                    .padding(5.dp)
            )
            CustomProgressBar(
                progress = progress
            )
        }
    }
}

@Composable
fun LevelCircle(
    level: Int,
) {
    Box(
        modifier = Modifier
            .size(30.dp)
            .background(
                color = PurpleGrey40,
                shape = CircleShape)
            .border(
                width = 2.dp,
                color = PurpleGrey100,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = level.toString(),
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}


@Composable
fun CustomProgressBar(
    progress: Float,
) {
    val size by animateFloatAsState(
        targetValue = progress,
        tween(
            durationMillis = 1000,
            delayMillis = 200,
            easing = LinearOutSlowInEasing
        )
    )
    // Progress Bar
    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .height(17.dp)
    ) {
        // ProgressBar arka planı
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(9.dp))
                .background(PurpleGrey90)
        )
        // ProgressBar ilerleme kısmı
        Box(
            modifier = Modifier
                .fillMaxWidth(size)  // İlerleme animasyona göre değişir
                .fillMaxHeight()
                .clip(RoundedCornerShape(9.dp))
                .background(Purple200)
                .animateContentSize()
        )

    }
}
