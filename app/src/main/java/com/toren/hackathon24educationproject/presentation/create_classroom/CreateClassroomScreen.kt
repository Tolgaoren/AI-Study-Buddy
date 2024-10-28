package com.toren.hackathon24educationproject.presentation.create_classroom

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.toren.hackathon24educationproject.presentation.sign_up.SignUpContract
import com.toren.hackathon24educationproject.presentation.sign_up.SignUpForm
import kotlinx.coroutines.flow.Flow

@Composable
fun CreateClassroomScreen(
    uiState: CreateClassroomContract.UiState,
    uiEffect: Flow<CreateClassroomContract.UiEffect>,
    uiEvent: (CreateClassroomContract.UiEvent) -> Unit,
    onNavigateToSignIn: () -> Unit,
    onNavigateToClassroom: () -> Unit,
    onNavigateToSignUp: () -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(uiEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiEffect.collect { effect ->
                when (effect) {
                    is CreateClassroomContract.UiEffect.NavigateToClassroom -> onNavigateToClassroom()
                    is CreateClassroomContract.UiEffect.NavigateToSignIn -> onNavigateToSignIn()
                    is CreateClassroomContract.UiEffect.NavigateToSignUp -> onNavigateToSignUp()
                    is CreateClassroomContract.UiEffect.ShowToast -> Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Surface {
        Column {
            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxSize(),
            ) {

            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                SignUpForm(
                    modifier = Modifier.padding(8.dp),
                    firstField = uiState.classroomName,
                    secondField = uiState.teacherName,
                    thirdField = uiState.email,
                    fourthField = uiState.password,
                    isLoading = uiState.isLoading,
                    firstFieldLabel = "Classroom Name",
                    secondFieldLabel = "Full Name",
                    thirdFieldLabel = "Email",
                    fourthFieldLabel = "Password",
                    onFirstFieldChange = { uiEvent(CreateClassroomContract.UiEvent.OnClassroomNameChange(it)) },
                    onSecondFieldChange = { uiEvent(CreateClassroomContract.UiEvent.OnTeacherNameChange(it)) },
                    onThirdFieldChange = { uiEvent(CreateClassroomContract.UiEvent.OnEmailChange(it)) },
                    onFourthFieldChange = { uiEvent(CreateClassroomContract.UiEvent.OnPasswordChange(it)) },
                    primaryButtonText = "Create Classroom",
                    secondaryButtonText = "Sign In",
                    tertiaryButtonText = "Sign Up",
                    onPrimaryButtonClick = { uiEvent(CreateClassroomContract.UiEvent.OnCreateClassroomClick) },
                    onSecondaryButtonClick = { uiEvent(CreateClassroomContract.UiEvent.OnSignInClick) },
                    onTertiaryButtonClick = { uiEvent(CreateClassroomContract.UiEvent.OnSignUpClick) }
                )
            }
        }
    }
}