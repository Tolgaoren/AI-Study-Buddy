package com.toren.hackathon24educationproject.presentation.choose_subject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.flow.Flow

@Composable
fun ChooseSubjectScreen(
    uiState: ChooseSubjectContract.UiState,
    uiEffect: Flow<ChooseSubjectContract.UiEffect>,
    uiEvent: (ChooseSubjectContract.UiEvent) -> Unit,
    onNavigateToPractice: (Any?) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = "Subjects",
            style = TextStyle(
                fontSize = 23.sp,
                fontWeight = FontWeight.SemiBold,
            )
        )
        LazyColumn {
            items(uiState.subjects) { item ->
                SubjectItem(
                    name = item,
                    onClick = { onNavigateToPractice(item) }
                )
            }
        }
    }
}

@Composable
fun SubjectItem(
    modifier: Modifier = Modifier,
    name: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier = modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                modifier = modifier
                    .padding(start = 10.dp)
            )
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Play Icon",
                modifier = modifier
                    .padding(end = 10.dp)
            )
        }

    }
}