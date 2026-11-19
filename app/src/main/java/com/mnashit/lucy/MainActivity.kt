package com.mnashit.lucy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.json.JSONArray

private val Navy = Color(0xFF071426)
private val Panel = Color(0xFF10243D)
private val Cyan = Color(0xFF38DDF8)
private val Blue = Color(0xFF247BFF)
private val Muted = Color(0xFF9FB2C8)
private val White = Color(0xFFFFFFFF)
private val Danger = Color(0xFFFF6B6B)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Navy.toArgb()),
            navigationBarStyle = SystemBarStyle.dark(Navy.toArgb())
        )
        super.onCreate(savedInstanceState)

        val preferences = getSharedPreferences(
            "lucy_preferences",
            MODE_PRIVATE
        )

        setContent {
            var currentScreen by rememberSaveable {
                mutableStateOf("home")
            }

            val messages = remember {
                mutableStateListOf<String>().apply {
                    val saved = preferences.getString(
                        "chat_history",
                        null
                    )

                    if (saved != null) {
                        try {
                            val array = JSONArray(saved)
                            for (i in 0 until array.length()) {
                                add(array.getString(i))
                            }
                        } catch (_: Exception) {
                            clear()
                        }
                    }
                }
            }

            var notifications by rememberSaveable {
                mutableStateOf(
                    preferences.getBoolean("notifications", true)
                )
            }

            var onlineSearch by rememberSaveable {
                mutableStateOf(
                    preferences.getBoolean("online_search", false)
                )
            }

            fun saveMessages() {
                val array = JSONArray()
                messages.forEach { array.put(it) }

                preferences.edit()
                    .putString("chat_history", array.toString())
                    .apply()
            }

            fun sendMessage(message: String) {
                val cleaned = message.trim()

                if (cleaned.isNotEmpty()) {
                    messages.add(cleaned)
                    saveMessages()
                }
            }

            fun deleteMessage(index: Int) {
                if (index in messages.indices) {
                    messages.removeAt(index)
                    saveMessages()
                }
            }

            fun clearMessages() {
                messages.clear()
                saveMessages()
            }

            BackHandler(enabled = currentScreen != "home") {
                currentScreen = "home"
            }

            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Navy
                ) {
                    when (currentScreen) {
                        "home" -> HomeScreen(
                            onChat = { currentScreen = "chat" },
                            onSettings = { currentScreen = "settings" },
                            onHistory = { currentScreen = "history" }
                        )

                        "chat" -> ChatScreen(
                            messages = messages,
                            onSendMessage = { sendMessage(it) },
                            onClearHistory = { clearMessages() },
                            onBack = { currentScreen = "home" }
                        )

                        "settings" -> SettingsScreen(
                            notifications = notifications,
                            onlineSearch = onlineSearch,
                            messageCount = messages.size,
                            onNotificationsChange = {
                                notifications = it
                                preferences.edit()
                                    .putBoolean("notifications", it)
                                    .apply()
                            },
                            onOnlineSearchChange = {
                                onlineSearch = it
                                preferences.edit()
                                    .putBoolean("online_search", it)
                                    .apply()
                            },
                            onClearHistory = { clearMessages() },
                            onBack = { currentScreen = "home" }
                        )

                        "history" -> HistoryScreen(
                            messages = messages,
                            onDeleteMessage = { deleteMessage(it) },
                            onClearHistory = { clearMessages() },
                            onOpenChat = { currentScreen = "chat" },
                            onBack = { currentScreen = "home" }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    onChat: () -> Unit,
    onSettings: () -> Unit,
    onHistory: () -> Unit
) {
    val transition = rememberInfiniteTransition(label = "lucyRing")

    val pulse by transition.animateFloat(
        initialValue = 0.94f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Navy)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "LUCY",
                color = Cyan,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "YOUR PERSONAL AI ASSISTANT",
                color = Muted,
                fontSize = 12.sp,
                letterSpacing = 2.sp
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(230.dp)
                    .scale(pulse)
                    .border(3.dp, Cyan, CircleShape)
                    .padding(12.dp)
                    .border(2.dp, Blue, CircleShape)
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(
                        id = R.drawable.lucy_avatar
                    ),
                    contentDescription = "LUCY Avatar",
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Panel, CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Hello! I'm LUCY",
                color = White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Your assistant is ready",
                color = Muted,
                fontSize = 14.sp
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onChat,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Open Chat", fontSize = 16.sp)
            }

            OutlinedButton(
                onClick = onHistory,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Cyan
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Chat History", fontSize = 16.sp)
            }

            OutlinedButton(
                onClick = onSettings,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Cyan
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Settings", fontSize = 16.sp)
            }
        }

        Text(
            text = "LUCY • Your Personal Assistant",
            color = Muted,
            fontSize = 11.sp
        )
    }
}

@Composable
fun ChatScreen(
    messages: List<String>,
    onSendMessage: (String) -> Unit,
    onClearHistory: () -> Unit,
    onBack: () -> Unit
) {
    var input by rememberSaveable {
        mutableStateOf("")
    }

    var showClearDialog by remember {
        mutableStateOf(false)
    }

    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    fun sendCurrentMessage() {
        if (input.isNotBlank()) {
            onSendMessage(input)
            input = ""
        }
    }

    if (showClearDialog) {
        ConfirmClearDialog(
            onConfirm = {
                onClearHistory()
                showClearDialog = false
            },
            onDismiss = { showClearDialog = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Navy)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onBack) {
                Text("← Back", color = Cyan)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "LUCY Chat",
                color = White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            if (messages.isNotEmpty()) {
                TextButton(onClick = { showClearDialog = true }) {
                    Text("Clear", color = Danger)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (messages.isEmpty()) {
                item {
                    Text(
                        text = "Your conversation will appear here.",
                        color = Muted,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            itemsIndexed(messages) { _, message ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Panel,
                            RoundedCornerShape(14.dp)
                        )
                        .padding(14.dp)
                ) {
                    Text(
                        text = message,
                        color = White,
                        fontSize = 15.sp
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text("Type a message...", color = Muted)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = White,
                    unfocusedTextColor = White,
                    focusedBorderColor = Cyan,
                    unfocusedBorderColor = Muted,
                    cursorColor = Cyan
                ),
                shape = RoundedCornerShape(14.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = { sendCurrentMessage() }
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { sendCurrentMessage() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Send")
            }
        }
    }
}

@Composable
fun HistoryScreen(
    messages: List<String>,
    onDeleteMessage: (Int) -> Unit,
    onClearHistory: () -> Unit,
    onOpenChat: () -> Unit,
    onBack: () -> Unit
) {
    var showClearDialog by remember {
        mutableStateOf(false)
    }

    if (showClearDialog) {
        ConfirmClearDialog(
            onConfirm = {
                onClearHistory()
                showClearDialog = false
            },
            onDismiss = { showClearDialog = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Navy)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(20.dp)
    ) {
        TextButton(onClick = onBack) {
            Text("← Back", color = Cyan)
        }

        Text(
            text = "Chat History",
            color = White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = if (messages.size == 1) {
                "1 saved message"
            } else {
                "${messages.size} saved messages"
            },
            color = Muted,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = onOpenChat,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Open Chat")
            }

            OutlinedButton(
                onClick = { showClearDialog = true },
                enabled = messages.isNotEmpty(),
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Danger
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Clear All")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (messages.isEmpty()) {
            Text(
                text = "No chat messages yet.",
                color = Muted
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                itemsIndexed(messages) { index, message ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Panel,
                                RoundedCornerShape(12.dp)
                            )
                            .padding(14.dp)
                    ) {
                        Text(
                            text = message,
                            color = White
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(
                                onClick = { onDeleteMessage(index) }
                            ) {
                                Text(
                                    "Delete",
                                    color = Danger,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(
    notifications: Boolean,
    onlineSearch: Boolean,
    messageCount: Int,
    onNotificationsChange: (Boolean) -> Unit,
    onOnlineSearchChange: (Boolean) -> Unit,
    onClearHistory: () -> Unit,
    onBack: () -> Unit
) {
    var showClearDialog by remember {
        mutableStateOf(false)
    }

    if (showClearDialog) {
        ConfirmClearDialog(
            onConfirm = {
                onClearHistory()
                showClearDialog = false
            },
            onDismiss = { showClearDialog = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Navy)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(20.dp)
    ) {
        TextButton(onClick = onBack) {
            Text("← Back", color = Cyan)
        }

        Text(
            text = "Settings",
            color = White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        SettingItem(
            title = "Notifications",
            description = "Notification preference",
            checked = notifications,
            onCheckedChange = onNotificationsChange
        )

        Spacer(modifier = Modifier.height(12.dp))

        SettingItem(
            title = "Online Search",
            description = "Online search preference",
            checked = onlineSearch,
            onCheckedChange = onOnlineSearchChange
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = { showClearDialog = true },
