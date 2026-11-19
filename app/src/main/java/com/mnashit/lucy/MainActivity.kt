package com.mnashit.lucy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mnashit.lucy.ui.theme.LucyTheme

private val Navy = Color(0xFF080D1A)
private val Panel = Color(0xFF111D32)
private val Cyan = Color(0xFF00E5FF)
private val Blue = Color(0xFF168BFF)
private val Muted = Color(0xFF9EACC2)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LucyTheme {
                LucyApp()
            }
        }
    }
}

private data class ChatMessage(
    val text: String,
    val fromLucy: Boolean
)

@Composable
fun LucyApp() {
    var screen by remember { mutableStateOf("Home") }
    var messages by remember {
        mutableStateOf(
            listOf(ChatMessage("Hello! Main LUCY hoon. Aapki assistant.", true))
        )
    }
    var listening by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Navy
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF142747), Navy, Color(0xFF050914))
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "L U C Y",
                    color = Cyan,
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "AI ASSISTANT",
                    color = Muted,
                    fontSize = 11.sp
                )
            }

            when (screen) {
                "Home" -> HomeScreen(
                    onChat = { screen = "Chat" },
                    onSettings = { screen = "Settings" },
                    onHistory = { screen = "History" }
                )
                "Chat" -> ChatScreen(
                    messages = messages,
                    onSend = { message ->
                        messages = messages + ChatMessage(message, false)
                    },
                    listening = listening,
                    onMic = { listening = !listening }
                )
                "Settings" -> SettingsScreen()
                "History" -> HistoryScreen(messages)
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Panel)
                    .padding(vertical = 15.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                NavItem("⌂", "Home", screen) { screen = "Home" }
                NavItem("◉", "Chat", screen) { screen = "Chat" }
                NavItem("◷", "History", screen) { screen = "History" }
                NavItem("⚙", "Settings", screen) { screen = "Settings" }
            }
        }
    }
}

@Composable
private fun NavItem(
    symbol: String,
    label: String,
    current: String,
    onClick: () -> Unit
) {
    TextButton(onClick = onClick) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                symbol,
                color = if (current == label) Cyan else Muted,
                fontSize = 22.sp
            )
            Text(
                label,
                color = if (current == label) Cyan else Muted,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
private fun HomeScreen(
    onChat: () -> Unit,
    onSettings: () -> Unit,
    onHistory: () -> Unit
) {
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
            modifier = Modifier
                .size(210.dp)
                .border(2.dp, Cyan.copy(alpha = 0.7f), CircleShape)
                .background(
                    Brush.radialGradient(
                        listOf(Color(0xFF1765A0), Panel, Navy)
                    ),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(145.dp)
                    .border(2.dp, Blue, CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(Cyan, Color(0xFF1266B5), Navy)
                        ),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "L",
                    color = Color.White,
                    fontSize = 76.sp,
                    fontWeight = FontWeight.Bold
                )
            }
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
            Text("START CHATTING", fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick = onHistory,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("CONVERSATION HISTORY", color = Cyan)
        }

        TextButton(onClick = onSettings) {
            Text("OPEN SETTINGS", color = Muted)
        }
    }
}

@Composable
private fun ChatScreen(
    messages: List<ChatMessage>,
    onSend: (String) -> Unit,
    listening: Boolean,
    onMic: () -> Unit
) {
    var input by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.88f)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            "CHAT WITH LUCY",
            color = Cyan,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(messages) { message ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (message.fromLucy)
                        Arrangement.Start else Arrangement.End
                ) {
                    Box(
                        modifier = Modifier
                            .widthIn(max = 290.dp)
                            .background(
                                if (message.fromLucy) Panel else Color(0xFF123C65),
                                RoundedCornerShape(16.dp)
                            )
                            .padding(14.dp)
                    ) {
                        Text(
                            message.text,
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        if (listening) {
            ListeningIndicator()
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Panel, RoundedCornerShape(25.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.weight(1f),
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp
                ),
                decorationBox = { inner ->
                    Box {
                        if (input.isEmpty()) {
                            Text("Type a message...", color = Muted)
                        }
                        inner()
                    }
                }
            )

            TextButton(onClick = onMic) {
                Text("🎙", fontSize = 21.sp)
            }

            TextButton(
                onClick = {
                    if (input.isNotBlank()) {
                        onSend(input.trim())
                        input = ""
                    }
                }
            ) {
                Text("SEND", color = Cyan, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun ListeningIndicator() {
    val infinite = rememberInfiniteTransition(label = "listening")
    val alpha by infinite.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(700),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Text(
        "●  LISTENING...",
        color = Cyan.copy(alpha = alpha),
        modifier = Modifier.padding(12.dp),
        fontSize = 13.sp
    )
}

@Composable
private fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(22.dp)
    ) {
        Text(
            "SETTINGS",
            color = Cyan,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(22.dp))

        SettingRow("Assistant Name", "LUCY")
        SettingRow("Interface Theme", "Dark Navy")
        SettingRow("Accent Lighting", "Blue / Cyan")
        SettingRow("Voice System", "Not configured")
        SettingRow("AI Brain", "Not connected")
        SettingRow("App Version", "1.0")
    }
}

@Composable
private fun SettingRow(title: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .background(Panel, RoundedCornerShape(12.dp))
            .padding(15.dp)
    ) {
        Text(title, color = Color.White, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(5.dp))
        Text(value, color = Muted, fontSize = 12.sp)
    }
}

@Composable
private fun HistoryScreen(messages: List<ChatMessage>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(
            "CONVERSATION HISTORY",
            color = Cyan,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(15.dp))

        if (messages.isEmpty()) {
            Text("No conversations yet.", color = Muted)
        } else {
            messages.forEach { message ->
                Text(
                    (if (message.fromLucy) "LUCY: " else "YOU: ") + message.text,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 7.dp),
                    fontSize = 13.sp
                )
            }
        }
    }
}
