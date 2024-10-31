package com.toren.hackathon24educationproject.presentation.practice

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import app.rive.runtime.kotlin.RiveAnimationView
import app.rive.runtime.kotlin.core.ExperimentalAssetLoader
import com.toren.hackathon24educationproject.R
import com.toren.hackathon24educationproject.common.Constants
import com.toren.hackathon24educationproject.common.Constants.STATE_MACHINE_NAME
import com.toren.hackathon24educationproject.presentation.level_panel.LevelPanel
import com.toren.hackathon24educationproject.presentation.theme.Pink80
import com.toren.hackathon24educationproject.presentation.theme.Purple200
import com.toren.hackathon24educationproject.presentation.theme.Purple80
import com.toren.hackathon24educationproject.presentation.theme.PurpleGrey40
import com.toren.hackathon24educationproject.presentation.theme.PurpleGrey80
import kotlinx.coroutines.flow.Flow


@OptIn(ExperimentalAssetLoader::class)
@Composable
fun PracticeScreen(
    uiState: PracticeContract.UiState,
    uiEffect: Flow<PracticeContract.UiEffect>,
    uiEvent: (PracticeContract.UiEvent) -> Unit,
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val scope = rememberCoroutineScope()
    var riveView: RiveAnimationView? by remember { mutableStateOf(null) }

    LaunchedEffect(uiState.isAnswerFocused) {
        riveView?.setBooleanState(STATE_MACHINE_NAME, "Check", uiState.isAnswerFocused.not())
    }

    LaunchedEffect(uiState.answer) {
        riveView?.setNumberState(STATE_MACHINE_NAME, "Look", 2 * uiState.answer.length.toFloat())
    }

    LaunchedEffect(uiState.isAnswerCorrect) {
        uiState.isAnswerCorrect?.let {
            if (it) {
                riveView?.fireState(STATE_MACHINE_NAME, "success")
            } else {
                riveView?.fireState(STATE_MACHINE_NAME, "fail")
            }
        }
    }

    LaunchedEffect(uiEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiEffect.collect { effect ->
                when (effect) {
                    is PracticeContract.UiEffect.GoToBackScreen -> {}
                    is PracticeContract.UiEffect.ShowToast -> Toast.makeText(
                        context,
                        effect.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
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
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
                .padding(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = Purple80
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = uiState.question,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 17.sp
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .weight(0.3f)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            AndroidView(
                modifier = Modifier,
                factory = { context ->
                    RiveAnimationView(context).apply {
                        setRiveResource(
                            resId = R.raw.animated_character,
                            stateMachineName = STATE_MACHINE_NAME,
                            alignment = app.rive.runtime.kotlin.core.Alignment.BOTTOM_CENTER
                        )
                        riveView = this
                    }
                }
            )

            Column(
                modifier = Modifier
                    .padding(top = 50.dp, start = 4.dp, end = 4.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GradientButton(
                    text = "Yardım",
                    textColor = TextStyle.Default.color,
                    gradient = Brush.radialGradient(
                        listOf(
                            Purple80,
                            PurpleGrey80
                        )
                    ),
                    onClick = {
                        uiEvent(PracticeContract.UiEvent.OnExplainClick)
                    }
                )
                GradientButton(
                    text = "Cevapla",
                    textColor = TextStyle.Default.color,
                    gradient = Brush.radialGradient(
                        listOf(
                            Purple80,
                            PurpleGrey80
                        )
                    ),
                    onClick = {
                        uiEvent(PracticeContract.UiEvent.OnAnswerClick)
                    }
                )
            }
        }
        OutlinedTextField(
            value = uiState.answer,
            onValueChange = { uiEvent(PracticeContract.UiEvent.OnAnswerChange(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
                .padding(4.dp)
                .onFocusChanged { uiEvent(PracticeContract.UiEvent.OnAnswerFocused) },
        )
    }
}

@Composable
fun GradientButton(
    text: String,
    textColor: Color,
    gradient: Brush,
    onClick: () -> Unit,

    ) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Purple200,
        ),
        contentPadding = PaddingValues()
    ) {
        Button(onClick = onClick,
            modifier = Modifier.padding(horizontal = 5.dp),
            contentPadding = PaddingValues(),) {
            Box(
                modifier = Modifier
                    .background(gradient)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    style = TextStyle(
                        color = textColor,
                        fontSize = 17.sp
                    )
                )
            }

        }

    }
}

@Preview
@Composable
private fun Asdd() {
    GradientButton(
        text = "Cevapla",
        textColor = TextStyle.Default.color,
        gradient = Brush.radialGradient(
            listOf(
                Purple80,
                PurpleGrey80
            )
        ),
        onClick = {}
    )
}