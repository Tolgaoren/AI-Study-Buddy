package com.toren.hackathon24educationproject.presentation.create_classroom

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.toren.hackathon24educationproject.R
import com.toren.hackathon24educationproject.presentation.sign_up.SignUpForm
import kotlinx.coroutines.flow.Flow

@Composable
fun CreateClassroomScreen(
    uiState: CreateClassroomContract.UiState,
    uiEffect: Flow<CreateClassroomContract.UiEffect>,
    uiEvent: (CreateClassroomContract.UiEvent) -> Unit,
    onNavigateToTeacher: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    onNavigateToSignUp: () -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(uiEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiEffect.collect { effect ->
                when (effect) {
                    is CreateClassroomContract.UiEffect.NavigateToTeacher -> onNavigateToTeacher()
                    is CreateClassroomContract.UiEffect.NavigateToSignIn -> onNavigateToSignIn()
                    is CreateClassroomContract.UiEffect.NavigateToSignUp -> onNavigateToSignUp()
                    is CreateClassroomContract.UiEffect.ShowToast -> Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(200.dp),
                    alignment = Alignment.Center
                )

            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp, end = 40.dp)
                    ) {
                        Button(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
                            Text("Seçilen sınıf: ${uiState.grade}")
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            (1..8).forEach { grade ->
                                DropdownMenuItem(
                                    text = { Text("$grade.sınıf") },
                                    onClick = {
                                        uiEvent(CreateClassroomContract.UiEvent.OnGradeChange(grade))
                                        expanded = false
                                    },
                                    enabled = !uiState.isLoading
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    SignUpForm(
                        modifier = Modifier.padding(8.dp),
                        firstField = uiState.classroomName,
                        secondField = uiState.teacherName,
                        thirdField = uiState.email,
                        fourthField = uiState.password,
                        isLoading = uiState.isLoading,
                        firstFieldLabel = "Sınıf adı",
                        secondFieldLabel = "Öğretmen adı",
                        thirdFieldLabel = "Email",
                        fourthFieldLabel = "Parola",
                        onFirstFieldChange = { uiEvent(CreateClassroomContract.UiEvent.OnClassroomNameChange(it)) },
                        onSecondFieldChange = { uiEvent(CreateClassroomContract.UiEvent.OnTeacherNameChange(it)) },
                        onThirdFieldChange = { uiEvent(CreateClassroomContract.UiEvent.OnEmailChange(it)) },
                        onFourthFieldChange = { uiEvent(CreateClassroomContract.UiEvent.OnPasswordChange(it)) },
                        primaryButtonText = "Sınıfı oluştur",
                        secondaryButtonText = "Giriş yap",
                        tertiaryButtonText = "Kayıt ol",
                        onPrimaryButtonClick = { uiEvent(CreateClassroomContract.UiEvent.OnCreateClassroomClick) },
                        onSecondaryButtonClick = { uiEvent(CreateClassroomContract.UiEvent.OnSignInClick) },
                        onTertiaryButtonClick = { uiEvent(CreateClassroomContract.UiEvent.OnSignUpClick) },
                        lastItemVisibility = uiState.lastItemVisibility,
                        lastItemVisibilityButtonClick = { uiEvent(CreateClassroomContract.UiEvent.OnLastItemVisibilityChange) }
                    )
                }
            }
        }
    }
}