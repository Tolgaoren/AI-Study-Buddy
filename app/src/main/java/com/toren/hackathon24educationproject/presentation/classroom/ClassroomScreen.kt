package com.toren.hackathon24educationproject.presentation.classroom

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.toren.hackathon24educationproject.domain.model.Student
import com.toren.hackathon24educationproject.presentation.choose_subject.SubjectItem
import com.toren.hackathon24educationproject.presentation.components.LevelCircle
import com.toren.hackathon24educationproject.presentation.theme.BLue200
import com.toren.hackathon24educationproject.presentation.theme.Blue400
import com.toren.hackathon24educationproject.presentation.theme.Blue80
import com.toren.hackathon24educationproject.presentation.theme.TextBlack
import kotlinx.coroutines.flow.Flow

@Composable
fun ClassroomScreen(
    uiState: ClassroomContract.UiState,
    uiEffect: Flow<ClassroomContract.UiEffect>,
    uiEvent: (ClassroomContract.UiEvent) -> Unit,
    onNavigateToSubjectExplanation: (Any?) -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(uiEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiEffect.collect { effect ->
                when (effect) {
                    is ClassroomContract.UiEffect.NavigateToSubjectExplanation -> {
                        onNavigateToSubjectExplanation(effect.subject)
                    }
                    is ClassroomContract.UiEffect.ShowToast -> {
                        Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    ClassroomContent(
        students = uiState.students,
        subjects = uiState.subjects,
        onSubjectClick = {
            uiEvent(ClassroomContract.UiEvent.OnSubjectClick(it))
        },
        subjectTitle = "Konu Anlatımı"
    )
}

@Composable
fun ClassroomContent(
    students: List<Student>,
    subjects: List<String>,
    onSubjectClick: (String) -> Unit,
    subjectTitle: String
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        item { SectionHeader(icon = R.drawable.ranking_icon, title = "En yüksek seviyeler") }

        items(students) { student ->
            val rank = students.indexOf(student) + 1
            StudentItem(
                name = student.fullName,
                level = student.level,
                rank = rank,
                avatar = student.avatar
            )
        }

        item {
            SectionHeader(icon = R.drawable.book, title = subjectTitle)
        }
        
        items(subjects) { subject ->
            SubjectItem(
                name = subject,
                onClick = {
                    onSubjectClick(subject)
                }
            )
        }
    }
}

@Composable
fun SectionHeader(
    @DrawableRes icon: Int,
    title: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = "$title İkonu",
            modifier = Modifier.size(40.dp)
        )
        Text(
            modifier = Modifier.padding(start = 15.dp),
            text = title,
            style = TextStyle(
                fontSize = 23.sp,
                fontWeight = FontWeight.SemiBold,
            )
        )
    }
}

@Composable
fun StudentItem(
    name: String,
    level: Int,
    rank: Int,
    avatar: Int,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
        shape = RoundedCornerShape(25.dp)
    ) {
        Row(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.White,
                            Blue400,
                            Blue400,
                            Blue400,
                            Color.White
                        )
                    )
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = rank.toString(),
                modifier = Modifier
                    .padding(18.dp),
                style = TextStyle(
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextBlack
                )
            )
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(25.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    BLue200,
                                    Blue80,
                                    Blue80,
                                    Blue80,
                                    BLue200
                                )
                            )
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                    ) {
                        Row(
                            modifier = Modifier.padding(start = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(
                                    id = when (avatar) {
                                        1 -> R.drawable.avatar1
                                        2 -> R.drawable.avatar2
                                        3 -> R.drawable.avatar3
                                        4 -> R.drawable.avatar4
                                        else -> R.drawable.avatar1
                                    }
                                ),
                                contentDescription = "Avatar",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(30.dp)
                            )

                            Spacer(
                                modifier = Modifier.width(10.dp)
                            )

                            Text(
                                text = name,
                                style = TextStyle(
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextBlack
                                )
                            )
                        }
                    }
                    LevelCircle(
                        level = level,
                        modifier = Modifier
                            .padding(12.dp)
                    )
                }
            }

        }

    }
}