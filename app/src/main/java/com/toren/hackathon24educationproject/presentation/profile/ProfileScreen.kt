package com.toren.hackathon24educationproject.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.toren.hackathon24educationproject.R
import com.toren.hackathon24educationproject.domain.model.Student
import com.toren.hackathon24educationproject.presentation.level_panel.CustomProgressBar
import com.toren.hackathon24educationproject.presentation.level_panel.LevelCircle
import com.toren.hackathon24educationproject.presentation.theme.TextBlack
import kotlinx.coroutines.flow.Flow

@Composable
fun ProfileScreen(
    uiState: ProfileContract.UiState,
    uiEffect: Flow<ProfileContract.UiEffect>,
    uiEvent: (ProfileContract.UiEvent) -> Unit,
)
{
    LaunchedEffect(Unit) {
        uiEvent(ProfileContract.UiEvent.Refresh)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ProfilePanel(
            name = uiState.fullName,
            avatar = uiState.avatar,
            level = uiState.level,
            progress = uiState.progress
        )
    }

}

@Composable
fun ProfilePanel(
    name: String,
    avatar: Int,
    level: Int,
    progress: Float = 0.0f
) {
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
            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ){
                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    text = name,
                    style = TextStyle(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextBlack
                    )
                )
                Row (
                    modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    LevelCircle(level = level)
                    CustomProgressBar(
                        modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 20.dp),
                        progress = progress)
                }
            }
        }
    }
}