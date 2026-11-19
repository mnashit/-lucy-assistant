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
                painter = painterResource(id = R.drawable.lucy_avatar),
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
