package com.toren.hackathon24educationproject.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.toren.hackathon24educationproject.R
import com.toren.hackathon24educationproject.presentation.theme.PurpleGrey100
import com.toren.hackathon24educationproject.presentation.theme.PurpleGrey40
import com.toren.hackathon24educationproject.presentation.theme.PurpleGrey80

@Composable
fun SelectAvatar(selectedAvatarIndex: Int, onAvatarSelected: (Int) -> Unit) {
    val avatarIds = listOf(
        R.drawable.avatar1,
        R.drawable.avatar2,
        R.drawable.avatar3,
        R.drawable.avatar4
    )
    AvatarGrid(
        avatarIds = avatarIds,
        onAvatarSelected = { onAvatarSelected(it) },
        selectedAvatarIndex = selectedAvatarIndex
    )
}

@Composable
fun AvatarGrid(
    avatarIds: List<Int>,
    modifier: Modifier = Modifier,
    selectedAvatarIndex: Int,
    onAvatarSelected: (Int) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        avatarIds.chunked(2).forEachIndexed { rowIndex, rowItems ->
            Row(
                modifier = Modifier.weight(1f).padding(start = 20.dp, end = 20.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                rowItems.forEachIndexed { columnIndex, avatarId ->
                    val avatarIndex = rowIndex * 2 + columnIndex
                    AvatarImage(
                        modifier = Modifier.weight(1f),
                        avatarId = avatarId,
                        isSelect = avatarIndex + 1 == selectedAvatarIndex,
                        onAvatarSelected = { onAvatarSelected(avatarIndex + 1) }
                    )
                }
            }
        }
    }
}

@Composable
fun AvatarImage(
    modifier: Modifier = Modifier,
    avatarId: Int,
    isSelect: Boolean,
    onAvatarSelected: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .padding(5.dp)
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
                width = if (isSelect) 5.dp else 0.dp,
                color = Color.LightGray,
                shape = CircleShape,
            )
            .clickable { onAvatarSelected() },
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = avatarId),
            contentDescription = "Avatar",
            modifier = modifier
                .clip(CircleShape)
        )
        if (isSelect) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = Color.White,
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.Center),
            )
        }
    }
}
