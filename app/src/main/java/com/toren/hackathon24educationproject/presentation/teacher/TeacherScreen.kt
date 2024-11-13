package com.toren.hackathon24educationproject.presentation.teacher

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.toren.hackathon24educationproject.presentation.classroom.ClassroomContent
import kotlinx.coroutines.flow.Flow

@Composable
fun TeacherScreen(
    uiState: TeacherContract.UiState,
    uiEffect: Flow<TeacherContract.UiEffect>,
    uiEvent: (TeacherContract.UiEvent) -> Unit,
    onNavigateToSignIn: () -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(uiEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiEffect.collect { effect ->
                when (effect) {
                    is TeacherContract.UiEffect.ShowToast -> {
                        Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                    }

                    is TeacherContract.UiEffect.NavigateToLoginScreen -> onNavigateToSignIn()

                }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ClassroomContent(
            students = uiState.students,
            subjects = uiState.subjects,
            onSubjectClick = {},
            subjectTitle = "Konular",
            isBottomButtonVisible = true,
            onBottomButtonClick = {
                uiEvent(TeacherContract.UiEvent.SignOutClick)
            }
        )
    }
}
