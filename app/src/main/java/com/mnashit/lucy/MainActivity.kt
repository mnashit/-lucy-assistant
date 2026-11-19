package com.mnashit.lucy

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mnashit.lucy.ui.theme.LucyTheme

private val Navy = Color(0xFF080D1A)
private val Cyan = Color(0xFF00E5FF)
private val Blue = Color(0xFF168BFF)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LucyTheme {
                LucyHomeScreen()
            }
        }
    }
}

@Composable
fun LucyHomeScreen() {
    val context = LocalContext.current
    var activated by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Navy
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF101F3B),
                            Navy,
                            Color(0xFF050914)
                        )
                    )
                )
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "L U C Y",
                color = Cyan,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "YOUR PERSONAL AI ASSISTANT",
                color = Color.LightGray,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(55.dp))

            Box(
                modifier = Modifier
                    .size(210.dp)
                    .border(
                        2.dp,
                        Cyan.copy(alpha = 0.6f),
                        CircleShape
                    )
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF174C83),
                                Color(0xFF102542),
                                Navy
                            )
                        ),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(145.dp)
                        .border(
                            2.dp,
                            Blue,
                            CircleShape
                        )
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFF00E5FF),
                                    Color(0xFF1266B5),
                                    Color(0xFF101A35)
                                )
                            ),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "L",
                        color = Color.White,
                        fontSize = 75.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "HELLO BOSS!",
                color = Color.White,
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = if (activated)
                    "Lucy interface is activated"
                else
                    "Your intelligent assistant is waiting",
                color = Color.LightGray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(35.dp))

            Button(
                onClick = {
                    activated = true
                    Toast.makeText(
                        context,
                        "Lucy Interface Activated",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue
                )
            ) {
                Text(
                    text = if (activated)
                        "LUCY ACTIVATED"
                    else
                        "ACTIVATE LUCY",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "VOICE  •  INTELLIGENCE  •  ASSISTANCE",
                color = Cyan.copy(alpha = 0.8f),
                fontSize = 10.sp
            )
        }
    }
}
