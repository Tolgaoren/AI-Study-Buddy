package com.toren.hackathon24educationproject.presentation.sign_in

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun SignInScreen(
    uiState: SignInContract.UiState,
    uiEffect: Flow<SignInContract.UiEffect>,
    uiEvent: (SignInContract.UiEvent) -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToClassroom: () -> Unit,
    onNavigateToCreateClassroom: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(uiEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiEffect.collect { effect ->
                when (effect) {
                    is SignInContract.UiEffect.ShowToast -> {
                        Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                    }

                    is SignInContract.UiEffect.NavigateToSignUp -> {
                        onNavigateToSignUp()
                    }

                    is SignInContract.UiEffect.NavigateToClassroom -> {
                        onNavigateToClassroom()
                    }

                    is SignInContract.UiEffect.NavigateToCreateClassroom -> {
                        onNavigateToCreateClassroom()
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
                EmailPasswordForm(
                    modifier = Modifier.padding(8.dp),
                    email = uiState.email,
                    password = uiState.password,
                    isLoading = uiState.isLoading,
                    onEmailChange = { uiEvent(SignInContract.UiEvent.OnEmailChange(it)) },
                    onPasswordChange = { uiEvent(SignInContract.UiEvent.OnPasswordChange(it)) },
                    primaryButtonText = "Sign In",
                    secondaryButtonText = "Sign Up",
                    tertiaryButtonText = "Create Classroom",
                    onPrimaryButtonClick = { uiEvent(SignInContract.UiEvent.OnSignInClick) },
                    onSecondaryButtonClick = { uiEvent(SignInContract.UiEvent.OnSignUpClick) },
                    onTertiaryButtonClick = { uiEvent(SignInContract.UiEvent.OnCreateClassroomClick) },
                )
            }

        }
    }
}


@Composable
fun EmailPasswordForm(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    isLoading: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    primaryButtonText: String,
    secondaryButtonText: String,
    tertiaryButtonText: String,
    onPrimaryButtonClick: () -> Unit,
    onSecondaryButtonClick: () -> Unit,
    onTertiaryButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Email") },
            singleLine = true,
            enabled = !isLoading,
            modifier = Modifier
                .alpha(if (isLoading) 0.8f else 1f)
        )

        Spacer(modifier = modifier)

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            singleLine = true,
            enabled = !isLoading,
            modifier = Modifier
                .alpha(if (isLoading) 0.8f else 1f)
        )

        Spacer(modifier = modifier)

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
        } else {
            Button(onClick = onPrimaryButtonClick) {
                Text(primaryButtonText)
            }

            Spacer(modifier = modifier)

            Row (
                modifier = Modifier.align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                TextButton(onClick = onSecondaryButtonClick) {
                    Text(secondaryButtonText)
                }
                TextButton(onClick = onTertiaryButtonClick) {
                    Text(tertiaryButtonText)
                }
            }
        }
    }
}

@Preview
@Composable
private fun SignInPreview() {
    val viewModel: SignInViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiEffect = viewModel.uiEffect
    SignInScreen(
        uiState = uiState,
        uiEffect = uiEffect,
        uiEvent = viewModel::onEvent,
        onNavigateToSignUp = { /*TODO*/ },
        onNavigateToClassroom = { /*TODO*/ },
        onNavigateToCreateClassroom = { /*TODO*/ }
    )
}