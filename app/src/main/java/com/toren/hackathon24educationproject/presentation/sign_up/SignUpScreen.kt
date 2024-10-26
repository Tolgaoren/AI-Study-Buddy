package com.toren.hackathon24educationproject.presentation.sign_up

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
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
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(uiEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiEffect.collect { effect ->
                when (effect) {
                    is SignUpContract.UiEffect.ShowToast -> {
                        Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                    }

                    is SignUpContract.UiEffect.NavigateToSignIn -> {
                        onNavigateToSignIn()
                    }

                    is SignUpContract.UiEffect.NavigateToClassroom -> {
                        onNavigateToClassroom()
                    }
                }
            }
        }
    }
    Surface {
        Column {
            Box (
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
            ){

            }
            Box (
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                ){
                    OutlinedTextField(
                        value = uiState.fullName,
                        onValueChange = { uiEvent(SignUpContract.UiEvent.OnFullNameChange(it)) },
                        label = { Text("Full Name") },
                        singleLine = true,
                        enabled = uiState.isLoading.not(),
                        modifier = Modifier
                            .alpha(if (uiState.isLoading) 0.8f else 1f)
                    )
                    EmailPasswordForm(
                        email = uiState.email,
                        password = uiState.password,
                        isLoading = uiState.isLoading,
                        onEmailChange = { uiEvent(SignUpContract.UiEvent.OnEmailChange(it)) },
                        onPasswordChange = { uiEvent(SignUpContract.UiEvent.OnPasswordChange(it)) },
                        primaryButtonText = "Sign Up",
                        secondaryButtonText = "Sign In",
                        onPrimaryButtonClick = { uiEvent(SignUpContract.UiEvent.OnSignUpClick) },
                        onSecondaryButtonClick = { uiEvent(SignUpContract.UiEvent.OnSignInClick) }
                    )
                }
            }

        }
    }
}