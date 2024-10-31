package com.toren.hackathon24educationproject.presentation.sign_up

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.toren.hackathon24educationproject.presentation.sign_in.EmailPasswordForm
import kotlinx.coroutines.flow.Flow

@Composable
fun SignUpScreen(
    uiState: SignUpContract.UiState,
    uiEffect: Flow<SignUpContract.UiEffect>,
    uiEvent: (SignUpContract.UiEvent) -> Unit,
    onNavigateToSignIn: () -> Unit,
    onNavigateToClassroom: () -> Unit,
    onNavigateToCreateClassroom: () -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(uiEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiEffect.collect { effect ->
                when (effect) {
                    is SignUpContract.UiEffect.ShowToast -> Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()

                    is SignUpContract.UiEffect.NavigateToSignIn -> onNavigateToSignIn()

                    is SignUpContract.UiEffect.NavigateToClassroom -> onNavigateToClassroom()

                    is SignUpContract.UiEffect.NavigateToCreateClassroom -> onNavigateToCreateClassroom()
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
                    firstField = uiState.classroomCode,
                    secondField = uiState.fullName,
                    thirdField = uiState.email,
                    fourthField = uiState.password,
                    isLoading = uiState.isLoading,
                    firstFieldLabel = "Classroom Code",
                    secondFieldLabel = "Full Name",
                    thirdFieldLabel = "Email",
                    fourthFieldLabel = "Password",
                    onFirstFieldChange = { uiEvent(SignUpContract.UiEvent.OnClassroomCodeChange(it)) },
                    onSecondFieldChange = { uiEvent(SignUpContract.UiEvent.OnFullNameChange(it)) },
                    onThirdFieldChange = { uiEvent(SignUpContract.UiEvent.OnEmailChange(it)) },
                    onFourthFieldChange = { uiEvent(SignUpContract.UiEvent.OnPasswordChange(it)) },
                    primaryButtonText = "Sign Up",
                    secondaryButtonText = "Sign In",
                    tertiaryButtonText = "Create Classroom",
                    onPrimaryButtonClick = { uiEvent(SignUpContract.UiEvent.OnSignUpClick) },
                    onSecondaryButtonClick = { uiEvent(SignUpContract.UiEvent.OnSignInClick) },
                    onTertiaryButtonClick = { uiEvent(SignUpContract.UiEvent.OnCreateClassroomClick) }
                )
            }
        }
    }
}

@Composable
fun SignUpForm(
    modifier: Modifier = Modifier,
    firstField: String,
    secondField: String,
    thirdField: String,
    fourthField: String,
    isLoading: Boolean,
    firstFieldLabel: String,
    secondFieldLabel: String,
    thirdFieldLabel: String,
    fourthFieldLabel: String,
    onFirstFieldChange: (String) -> Unit,
    onSecondFieldChange: (String) -> Unit,
    onThirdFieldChange: (String) -> Unit,
    onFourthFieldChange: (String) -> Unit,
    primaryButtonText: String,
    secondaryButtonText: String,
    tertiaryButtonText: String,
    onPrimaryButtonClick: () -> Unit,
    onSecondaryButtonClick: () -> Unit,
    onTertiaryButtonClick: () -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = firstField,
            onValueChange = onFirstFieldChange,
            label = {
                Text( firstFieldLabel )
                    },
            singleLine = true,
            enabled = !isLoading,
            modifier = Modifier
                .alpha(if (isLoading) 0.8f else 1f)
        )

        Spacer(modifier = modifier)

        OutlinedTextField(
            value = secondField,
            onValueChange = onSecondFieldChange,
            label = {
                Text( secondFieldLabel )
                    },
            singleLine = true,
            enabled = !isLoading,
            modifier = Modifier
                .alpha(if (isLoading) 0.8f else 1f)
        )

        Spacer(modifier = modifier)

        EmailPasswordForm(
            modifier = modifier,
            email = thirdField,
            password = fourthField,
            isLoading = isLoading,
            firstFieldLabel = thirdFieldLabel,
            secondFieldLabel = fourthFieldLabel,
            onEmailChange = onThirdFieldChange,
            onPasswordChange = onFourthFieldChange,
            primaryButtonText = primaryButtonText,
            secondaryButtonText = secondaryButtonText,
            tertiaryButtonText = tertiaryButtonText,
            onPrimaryButtonClick = onPrimaryButtonClick,
            onSecondaryButtonClick = onSecondaryButtonClick,
            onTertiaryButtonClick = onTertiaryButtonClick
        )
    }
}
