package com.toren.hackathon24educationproject.presentation.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.toren.hackathon24educationproject.R
import com.toren.hackathon24educationproject.presentation.classroom.SectionHeader
import com.toren.hackathon24educationproject.presentation.components.CustomProgressBar
import com.toren.hackathon24educationproject.presentation.components.LevelCircle
import com.toren.hackathon24educationproject.presentation.practice.GradientButton
import com.toren.hackathon24educationproject.presentation.theme.Purple80
import com.toren.hackathon24educationproject.presentation.theme.PurpleGrey80
import com.toren.hackathon24educationproject.presentation.theme.TextBlack
import kotlinx.coroutines.flow.Flow

@Composable
fun ProfileScreen(
    uiState: ProfileContract.UiState,
    uiEffect: Flow<ProfileContract.UiEffect>,
    uiEvent: (ProfileContract.UiEvent) -> Unit,
    onNavigateToSignIn: () -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(uiEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiEffect.collect { effect ->
                when (effect) {
                    is ProfileContract.UiEffect.GoToLoginScreen -> onNavigateToSignIn()
                    is ProfileContract.UiEffect.ShowToast -> Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        uiEvent(ProfileContract.UiEvent.Refresh)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            ProfilePanel(
                name = uiState.fullName,
                avatar = uiState.avatar,
                level = uiState.level,
                progress = uiState.progress
            )
        }
        item {
            SectionHeader(icon = R.drawable.badges, title = "Rozetlerim")
        }
        item {
            BadgeGrid(badges = uiState.badges)
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                GradientButton(
                    text = "Çıkış yap",
                    textColor = TextBlack,
                    gradient = Brush.radialGradient(
                        listOf(
                            Purple80,
                            PurpleGrey80
                        )
                    ),
                    onClick = {
                        uiEvent(ProfileContract.UiEvent.SignOutClick)
                    }
                )
            }
        }

    }

}

@Composable
fun BadgeGrid(
    badges: List<Int>,
) {
    val badgeList = (1..6).chunked(2)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        badgeList.forEach { pair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                pair.forEach { badgeId ->
                    BadgeCard(
                        modifier = Modifier.weight(1f),
                        badgeId = badgeId,
                        badges = badges
                    )
                }
            }
        }
    }
}

@Composable
fun BadgeCard(modifier: Modifier, badgeId: Int, badges: List<Int>) {
    Card(
        modifier = modifier
            .height(150.dp),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(
                    id = if (badges.contains(badgeId)) {
                        when (badgeId) {
                            1 -> R.drawable.badge1
                            2 -> R.drawable.badge2
                            3 -> R.drawable.badge3
                            4 -> R.drawable.badge4
                            5 -> R.drawable.badge5
                            6 -> R.drawable.badge6
                            else -> R.drawable.badge1
                        }
                    } else {
                        when (badgeId) {
                            1 -> R.drawable.badge1bw
                            2 -> R.drawable.badge2bw
                            3 -> R.drawable.badge3bw
                            4 -> R.drawable.badge4bw
                            5 -> R.drawable.badge5bw
                            6 -> R.drawable.badge6bw
                            else -> R.drawable.badge1bw
                        }
                    }
                ),
                contentDescription = "Badge $badgeId",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun ProfilePanel(
    name: String,
    avatar: Int,
    level: Int,
    progress: Float = 0.0f,
) {
    Column {
        Row(
            modifier = Modifier.padding(top = 20.dp),
        ) {
            Box(modifier = Modifier.weight(0.4f)) {
                Image(
                    painter = painterResource(
                        id = when (avatar) {
                            1 -> R.drawable.avatar1
                            2 -> R.drawable.avatar2
                            3 -> R.drawable.avatar3
                            4 -> R.drawable.avatar4
                            else -> R.drawable.avatar1
                        }
                    ),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(100.dp)
                        .align(Alignment.Center)
                )
            }
            Box(
                modifier = Modifier.weight(0.5f)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = name,
                        style = TextStyle(
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isSystemInDarkTheme()) Color.White else TextBlack
                        )
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LevelCircle(level = level)
                        CustomProgressBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, end = 20.dp),
                            progress = progress
                        )
                    }
                }
            }
        }
    }
}