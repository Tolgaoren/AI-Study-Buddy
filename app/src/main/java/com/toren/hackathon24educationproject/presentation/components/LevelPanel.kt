package com.toren.hackathon24educationproject.presentation.components

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toren.hackathon24educationproject.R
import com.toren.hackathon24educationproject.presentation.theme.Blue40
import com.toren.hackathon24educationproject.presentation.theme.Blue400
import com.toren.hackathon24educationproject.presentation.theme.Purple200
import com.toren.hackathon24educationproject.presentation.theme.PurpleGrey100
import com.toren.hackathon24educationproject.presentation.theme.PurpleGrey40
import com.toren.hackathon24educationproject.presentation.theme.PurpleGrey80
import com.toren.hackathon24educationproject.presentation.theme.PurpleGrey90
import com.toren.hackathon24educationproject.presentation.theme.TextBlack


@Composable
fun LevelPanel(
    fullName: String,
    progress: Float,
    level: Int,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isSystemInDarkTheme()) Blue40 else Blue400
            )
            .padding(start = 20.dp, top = 15.dp, bottom = 15.dp, end = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(
                        if (isSystemInDarkTheme()) {
                            Brush.linearGradient(
                                listOf(
                                    PurpleGrey40,
                                    PurpleGrey100
                                )
                            )
                        } else {
                            Brush.linearGradient(
                                listOf(
                                    PurpleGrey80,
                                    Color.White
                                )
                            )
                        },
                        shape = CircleShape
                    )
                    .border(
                        width = 1.dp,
                        color = if (isSystemInDarkTheme()) PurpleGrey100 else Purple200,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(
                        id = R.drawable.avatar1
                    ),
                    contentDescription = "Avatar",
                    modifier = Modifier.clip(CircleShape))
            }

            Spacer(
                modifier = Modifier
                    .padding(5.dp)
            )

            Text(
                text = fullName,
                style = TextStyle(
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isSystemInDarkTheme()) Color.White else TextBlack
                )
            )
        }
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
                modifier = Modifier
                .fillMaxWidth(0.5f),
                progress = progress
            )
        }
    }
}

@Composable
fun LevelCircle(
    modifier: Modifier = Modifier,
    level: Int,
) {
    Box(
        modifier = modifier
            .size(30.dp)
            .background(
                if (isSystemInDarkTheme()) {
                    Brush.linearGradient(
                        listOf(
                            PurpleGrey40,
                            PurpleGrey100
                        )
                    )
                } else {
                    Brush.linearGradient(
                        listOf(
                            PurpleGrey80,
                            Color.White
                        )
                    )
                },
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = if (isSystemInDarkTheme()) PurpleGrey100 else Purple200,
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


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CustomProgressBar(
    modifier: Modifier,
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
        modifier = modifier
            .height(15.dp)
    ) {
        // ProgressBar arka planı
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(9.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(
                            PurpleGrey100,
                            PurpleGrey90
                        )
                    )
                )
        )
        // ProgressBar ilerleme kısmı
        Box(
            modifier = Modifier
                .fillMaxWidth(size)  // İlerleme animasyona göre değişir
                .fillMaxHeight()
                .clip(RoundedCornerShape(9.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(
                            PurpleGrey80,
                            Color.White
                        )
                    )
                )
                .animateContentSize()
        )

    }
}
