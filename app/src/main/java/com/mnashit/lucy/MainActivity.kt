package com.mnashit.lucy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Navy = Color(0xFF071426)
private val Panel = Color(0xFF10243D)
private val Cyan = Color(0xFF42DDF5)
private val Blue = Color(0xFF1677D2)
private val Muted = Color(0xFF9BAEC4)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var screen by remember { mutableStateOf("home") }

            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Navy
                ) {
                    when (screen) {
                        "home" -> HomeScreen(
                            onChat = { screen = "chat" },
                            onSettings = { screen = "settings" },
                            onHistory = { screen = "history" }
                        )

                        "chat" -> SimpleScreen(
                            title = "LUCY CHAT",
                            message = "Chat screen",
                            onBack = { screen = "home" }
                        )

                        "settings" -> SimpleScreen(
                            title = "SETTINGS",
                            message = "LUCY Settings",
                            onBack = { screen = "home" }
                        )

                        "history" -> SimpleScreen(
                            title = "CONVERSATION HISTORY",
                            message = "Your conversations will appear here.",
                            onBack = { screen = "home" }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeScreen(
    onChat: () -> Unit,
    onSettings: () -> Unit,
    onHistory: () -> Unit
) {
    val infinite = rememberInfiniteTransition(label = "avatarGlow")

    val glowAlpha by infinite.animateFloat(
        initialValue = 0.35f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAlpha"
    )

    val ringSize by infinite.animateFloat(
        initialValue = 210f,
        targetValue = 224f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ringSize"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(28.dp))

        Text(
            "YOUR PERSONAL AI",
            color = Muted,
            fontSize = 12.sp
        )

        Spacer(Modifier.height(25.dp))

        Box(
            modifier = Modifier.size(230.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(ringSize.dp)
                    .border(
                        3.dp,
                        Cyan.copy(alpha = glowAlpha),
                        CircleShape
                    )
                    .background(
                        Brush.radialGradient(
                            listOf(
                                Color(0xFF1765A0).copy(alpha = 0.35f),
                                Panel,
                                Navy
                            )
                        ),
                        CircleShape
                    )
            )

            Image(
                painter = painterResource(
                    id = R.drawable.lucy_avatar
                ),
                contentDescription = "LUCY AI Avatar",
                modifier = Modifier
                    .size(190.dp)
                    .clip(CircleShape)
                    .border(
                        2.dp,
                        Blue.copy(alpha = glowAlpha),
                        CircleShape
                    ),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(Modifier.height(28.dp))

        Text(
            "HELLO BOSS!",
            color = Color.White,
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        Text(
            "Your intelligent assistant is waiting",
            color = Muted,
            fontSize = 13.sp
        )

        Spacer(Modifier.height(30.dp))

        Button(
            onClick = onChat,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Blue
            )
        ) {
            Text(
                "START CHATTING",
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick = onHistory,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "CONVERSATION HISTORY",
                color = Cyan
            )
        }

        TextButton(onClick = onSettings) {
            Text(
                "OPEN SETTINGS",
                color = Muted
            )
        }
    }
}

@Composable
private fun SimpleScreen(
    title: String,
    message: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Navy)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(40.dp))

        Text(
            title,
            color = Cyan,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(24.dp))

        Text(
            message,
            color = Color.White,
            fontSize = 16.sp
        )

        Spacer(Modifier.height(30.dp))

        Button(onClick = onBack) {
            Text("BACK TO HOME")
        }
    }
}
