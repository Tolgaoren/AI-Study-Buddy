package com.toren.hackathon24educationproject.presentation.choose_subject

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
import com.toren.hackathon24educationproject.presentation.components.LevelPanel
import com.toren.hackathon24educationproject.presentation.theme.Purple200
import com.toren.hackathon24educationproject.presentation.theme.PurpleGrey80
import com.toren.hackathon24educationproject.presentation.theme.TextBlack
import kotlinx.coroutines.flow.Flow

@Composable
fun ChooseSubjectScreen(
    uiState: ChooseSubjectContract.UiState,
    uiEffect: Flow<ChooseSubjectContract.UiEffect>,
    uiEvent: (ChooseSubjectContract.UiEvent) -> Unit,
    onNavigateToPractice: (Any?) -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(uiEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiEffect.collect { effect ->
                when (effect) {
                    is ChooseSubjectContract.UiEffect.NavigateToPractice -> {
                        onNavigateToPractice(effect.subject)
                    }
                    is ChooseSubjectContract.UiEffect.ShowToast -> {
                        Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        uiEvent(ChooseSubjectContract.UiEvent.Refresh)
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {

        LevelPanel(
            fullName = uiState.fullName,
            progress = uiState.progress,
            level = uiState.level
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.book
                ),
                contentDescription = "Book Icon",
                modifier = Modifier.size(40.dp)
            )
            Text(
                modifier = Modifier.padding(start = 15.dp),
                text = "Konular",
                style = TextStyle(
                    fontSize = 23.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            )
        }
        LazyColumn(
            modifier = Modifier
        ) {
            items(uiState.subjects) { item ->
                SubjectItem(
                    name = item,
                    onClick = {
                        uiEvent(ChooseSubjectContract.UiEvent.OnSubjectClick(item))
                    }
                )
            }
        }
    }
}

@Composable
fun SubjectItem(
    modifier: Modifier = Modifier,
    name: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        onClick = onClick,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Purple200,
                            PurpleGrey80,
                            PurpleGrey80,
                            PurpleGrey80,
                            Purple200
                        )
                    )
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                modifier = modifier
                    .padding(16.dp),
                style = TextStyle(
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextBlack
                )
            )
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Play Icon",
                modifier = modifier
                    .padding(16.dp),
                tint = TextBlack
            )
        }

    }
}