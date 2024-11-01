package com.toren.hackathon24educationproject.presentation.classroom

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.toren.hackathon24educationproject.R
import com.toren.hackathon24educationproject.presentation.choose_subject.SubjectItem
import com.toren.hackathon24educationproject.presentation.level_panel.LevelCircle
import com.toren.hackathon24educationproject.presentation.theme.BLue200
import com.toren.hackathon24educationproject.presentation.theme.Blue400
import com.toren.hackathon24educationproject.presentation.theme.Blue80
import com.toren.hackathon24educationproject.presentation.theme.Purple200
import com.toren.hackathon24educationproject.presentation.theme.PurpleGrey80
import kotlinx.coroutines.flow.Flow

@Composable
fun ClassroomScreen(
    uiState: ClassroomContract.UiState,
    uiEffect: Flow<ClassroomContract.UiEffect>,
    uiEvent: (ClassroomContract.UiEvent) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            modifier = Modifier.padding(15.dp),
            text = "Sıralama",
            style = TextStyle(
                fontSize = 23.sp,
                fontWeight = FontWeight.SemiBold,
            )
        )
        LazyColumn(
            modifier = Modifier.padding(10.dp),
        ) {
            items(uiState.students) { item ->
                val rank = uiState.students.indexOf(item) + 1
                StudentItem(
                    name = item.fullName,
                    level = item.level,
                    rank = rank
                )
            }
        }
    }
}

@Composable
fun StudentItem(
    name: String,
    level: Int,
    rank: Int,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(25.dp)
    ) {
        Row(modifier = Modifier
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
                    fontWeight = FontWeight.Bold
                )
            )
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(25.dp)
            ) {
                Row(modifier = Modifier
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
                        Row (
                            modifier = Modifier.padding(start = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Image(
                                painter = painterResource(
                                    id = R.drawable.avatar
                                ),
                                contentDescription = "Avatar",
                                modifier = Modifier.clip(CircleShape).size(30.dp)
                            )

                            Spacer(
                                modifier = Modifier.width(10.dp)
                            )

                            Text(
                                text = name,
                                style = TextStyle(
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.SemiBold
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